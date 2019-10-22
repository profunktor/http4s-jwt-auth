```tut:book
import cats.effect.IO
import cats.implicits._
import dev.profunktor.auth._
import dev.profunktor.auth.jwt._
import pdi.jwt._
import org.http4s._

case class AuthUser(id: Long, name: String)

val authenticate: JwtToken => JwtClaim => IO[Option[AuthUser]] =
  token => claim => AuthUser(123L, "joe").some.pure[IO]

val jwtAuth    = JwtAuth.hmac("53cr3t", JwtAlgorithm.HS256)
val middleware = JwtAuthMiddleware[IO, AuthUser](jwtAuth, authenticate)

val routes: AuthedRoutes[AuthUser, IO] = null
val securedRoutes: HttpRoutes[IO] = middleware(routes)
```
