package dev.profunktor.auth

import cats.data.OptionT
import cats.effect.IO
import cats.implicits._
import io.estatico.newtype.macros.newtype
import io.estatico.newtype.ops._
import org.http4s._
import org.scalatest.AsyncFunSuite
import org.scalatest.compatible.Assertion
import scala.concurrent.Future

class JwtAuthMiddlewareSpec extends AsyncFunSuite with JwtFixture {

  private def assertResp(response: OptionT[IO, Response[IO]], expected: Status): Future[Assertion] =
    response.value
      .map {
        case Some(resp) => assert(resp.status == expected)
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

}

object TestUsers {
  @newtype case class AuthUser(value: String)
}

trait JwtFixture {
  import dev.profunktor.auth.jwt._
  import org.http4s.dsl.io._
  import pdi.jwt._
  import TestUsers._

  val jwtAuth    = JwtAuth("53cr3t".coerce[JwtSecretKey], JwtAlgorithm.HS256)
  val middleware = JwtAuthMiddleware[IO, AuthUser](jwtAuth)

  val adminToken  = Jwt.encode(JwtClaim("cl41m"), jwtAuth.secretKey.value, JwtAlgorithm.HS256)
  val randomToken = Jwt.encode(JwtClaim("random"), "secret", JwtAlgorithm.HS256)

  val rootReq         = Request[IO](Method.GET, Uri.unsafeFromString("/"))
  val adminReqNoToken = Request[IO](Method.GET, Uri.unsafeFromString("/admin"))
  val badAdminReq     = adminReqNoToken.withHeaders(Header("Authorization", s"Bearer $randomToken"))
  val goodAdminReq    = adminReqNoToken.withHeaders(Header("Authorization", s"Bearer $adminToken"))

  val openRoute: HttpRoutes[IO] = HttpRoutes.of {
    case GET -> Root => Ok()
  }

  val adminRoute: AuthedRoutes[AuthUser, IO] = AuthedRoutes.of {
    case GET -> Root / "admin" as _ => Ok()
  }
}
