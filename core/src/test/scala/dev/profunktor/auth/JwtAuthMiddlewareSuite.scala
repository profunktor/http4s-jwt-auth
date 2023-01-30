package dev.profunktor.auth

import scala.util.Try
import cats.data.OptionT
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import cats.syntax.all.*
import munit.FunSuite
import org.http4s.*

import scala.util.control.NoStackTrace

class JwtAuthMiddlewareSpec extends FunSuite with JwtFixture {

  private def assertResp(response: OptionT[IO, Response[IO]], expected: Status) =
    response.value
      .map {
        case Some(resp) => assertEquals(resp.status, expected)
        case None       => fail("No response")
      }
      .unsafeToFuture()

  test("Open route works when combined with secured route") {
    val routes = openRoute <+> middleware(adminRoute)
    assertResp(routes.run(rootReq), Status.Ok)
  }

  test("Admin route gives 200 when there's a valid token") {
    assertResp(middleware(adminRoute).run(goodAdminReq), Status.Ok)
  }

  test("Admin route gives 403 when there's no token") {
    assertResp(middleware(adminRoute).run(adminReqNoToken), Status.Forbidden)
  }

  test("Admin route gives 403 when there's an invalid token") {
    assertResp(middleware(adminRoute).run(badAdminReq), Status.Forbidden)
  }

  test("Admin route gives 403 when the token is valid but the user cannot be found") {
    assertResp(middleware(adminRoute).run(noUserAdminReq), Status.Forbidden)
  }

  test("Admin Route gives 200 when there's a valid token and secret is fetched via F[_]") {
    val middleware = JwtAuthMiddleware[IO, AuthUser](IO.pure(jwtAuth), authenticate)
    assertResp(middleware(adminRoute).run(goodAdminReq), Status.Ok)
  }

  test("Admin Route fails when secret fetching via F[_] fails") {
    val exception = new Exception("key fetching failed") with NoStackTrace
    val middleware = JwtAuthMiddleware[IO, AuthUser](
      IO.raiseError(exception),
      authenticate
    )
    middleware(adminRoute)
      .run(goodAdminReq)
      .value
      .attempt
      .map(response => assertEquals(response, Left(exception)))
      .unsafeToFuture()
  }

}

object TestUsers {}

trait JwtFixture {
  import dev.profunktor.auth.jwt.*
  import org.http4s.dsl.io.*
  import pdi.jwt.*

  case class AuthUser(id: Long, name: String)

  def extractId(content: String): Long =
    Try(content.drop(1).dropRight(1).toLong).toOption.getOrElse(0L)

  val authenticate: JwtToken => JwtClaim => IO[Option[AuthUser]] = _ =>
    claim =>
      if (extractId(claim.content) == 123L) AuthUser(123L, "joe").some.pure[IO]
      else none[AuthUser].pure[IO]

  val jwtAuth    = JwtAuth.hmac("53cr3t", JwtAlgorithm.HS256)
  val middleware = JwtAuthMiddleware[IO, AuthUser](jwtAuth, authenticate)

  val adminToken  = Jwt.encode(JwtClaim("{123}"), jwtAuth.secretKey.value, jwtAuth.jwtAlgorithms.head)
  val noUserToken = Jwt.encode(JwtClaim("{666}"), jwtAuth.secretKey.value, jwtAuth.jwtAlgorithms.head)
  val randomToken = Jwt.encode(JwtClaim("{000}"), "secret", jwtAuth.jwtAlgorithms.head)

  val rootReq         = Request[IO](Method.GET, Uri.unsafeFromString("/"))
  val adminReqNoToken = Request[IO](Method.GET, Uri.unsafeFromString("/admin"))
  val badAdminReq  = adminReqNoToken.withHeaders(Header.ToRaw.keyValuesToRaw("Authorization" -> s"Bearer $randomToken"))
  val goodAdminReq = adminReqNoToken.withHeaders(Header.ToRaw.keyValuesToRaw("Authorization" -> s"Bearer $adminToken"))
  val noUserAdminReq =
    adminReqNoToken.withHeaders(Header.ToRaw.keyValuesToRaw("Authorization" -> s"Bearer $noUserToken"))

  val openRoute: HttpRoutes[IO] = HttpRoutes.of { case GET -> Root =>
    Ok()
  }

  val adminRoute: AuthedRoutes[AuthUser, IO] = AuthedRoutes.of { case GET -> Root / "admin" as _ =>
    Ok()
  }
}
