```scala
import cats.effect.IO
//        import cats.effect.IO
//                           ^
// On line 2: warning: Unused import
// import cats.effect.IO

import cats.implicits._
// 
//        import cats.effect.IO
//                           ^
// <synthetic>:2: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
//        import cats.implicits._
//                              ^
// On line 2: warning: Unused import
// import cats.implicits._

import dev.profunktor.auth._
// 
//        import cats.effect.IO
//                           ^
// <synthetic>:2: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import cats.implicits._
//                              ^
// <synthetic>:4: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
//        import dev.profunktor.auth._
//                                   ^
// On line 2: warning: Unused import
// import dev.profunktor.auth._

import dev.profunktor.auth.jwt._
// 
//        import cats.effect.IO
//                           ^
// <synthetic>:2: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import cats.implicits._
//                              ^
// <synthetic>:4: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import dev.profunktor.auth._
//                                   ^
// <synthetic>:7: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
//        import dev.profunktor.auth.jwt._
//                                       ^
// On line 2: warning: Unused import
// import dev.profunktor.auth.jwt._

import pdi.jwt._
// 
//        import cats.effect.IO
//                           ^
// <synthetic>:2: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import cats.implicits._
//                              ^
// <synthetic>:4: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import dev.profunktor.auth._
//                                   ^
// <synthetic>:7: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import dev.profunktor.auth.jwt._
//                                       ^
// <synthetic>:10: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
//        import pdi.jwt._
//                       ^
// On line 2: warning: Unused import
// import pdi.jwt._

import org.http4s._
// 
//        import cats.effect.IO
//                           ^
// <synthetic>:2: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import cats.implicits._
//                              ^
// <synthetic>:4: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import dev.profunktor.auth._
//                                   ^
// <synthetic>:7: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import dev.profunktor.auth.jwt._
//                                       ^
// <synthetic>:10: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import pdi.jwt._
//                       ^
// <synthetic>:13: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
//        import org.http4s._
//                          ^
// On line 2: warning: Unused import
// import org.http4s._

case class AuthUser(id: Long, name: String)
// 
//        import cats.effect.IO
//                           ^
// <synthetic>:2: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import cats.implicits._
//                              ^
// <synthetic>:4: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import dev.profunktor.auth._
//                                   ^
// <synthetic>:7: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import dev.profunktor.auth.jwt._
//                                       ^
// <synthetic>:10: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import pdi.jwt._
//                       ^
// <synthetic>:13: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import org.http4s._
//                          ^
// <synthetic>:16: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// defined class AuthUser

val authenticate: JwtToken => JwtClaim => IO[Option[AuthUser]] =
  token => claim => AuthUser(123L, "joe").some.pure[IO]
// 
//        import dev.profunktor.auth._
//                                   ^
// <synthetic>:7: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import org.http4s._
//                          ^
// <synthetic>:16: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
//          token => claim => AuthUser(123L, "joe").some.pure[IO]
//          ^
// On line 3: warning: parameter value token in anonymous function is never used
//          token => claim => AuthUser(123L, "joe").some.pure[IO]
//                   ^
// On line 3: warning: parameter value claim in anonymous function is never used
// authenticate: dev.profunktor.auth.jwt.JwtToken => (pdi.jwt.JwtClaim => cats.effect.IO[Option[AuthUser]]) = $$Lambda$11176/401993450@727b359e

val jwtAuth    = JwtAuth.hmac("53cr3t", JwtAlgorithm.HS256)
// 
//        import cats.effect.IO
//                           ^
// <synthetic>:2: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import cats.implicits._
//                              ^
// <synthetic>:4: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import dev.profunktor.auth._
//                                   ^
// <synthetic>:7: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import org.http4s._
//                          ^
// <synthetic>:16: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// jwtAuth: dev.profunktor.auth.jwt.JwtSymmetricAuth = JwtSymmetricAuth(JwtSecretKey(53cr3t),List(HS256))

val middleware = JwtAuthMiddleware[IO, AuthUser](jwtAuth, authenticate)
// 
//        import cats.implicits._
//                              ^
// <synthetic>:4: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import dev.profunktor.auth.jwt._
//                                       ^
// <synthetic>:10: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import pdi.jwt._
//                       ^
// <synthetic>:13: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import org.http4s._
//                          ^
// <synthetic>:16: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// middleware: org.http4s.server.AuthMiddleware[cats.effect.IO,AuthUser] = org.http4s.server.package$AuthMiddleware$$$Lambda$11188/1630776015@2efb11b0

val routes: AuthedRoutes[AuthUser, IO] = null
// 
//        import cats.implicits._
//                              ^
// <synthetic>:4: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import dev.profunktor.auth._
//                                   ^
// <synthetic>:7: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import dev.profunktor.auth.jwt._
//                                       ^
// <synthetic>:10: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import pdi.jwt._
//                       ^
// <synthetic>:13: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// routes: org.http4s.AuthedRoutes[AuthUser,cats.effect.IO] = null

val securedRoutes: HttpRoutes[IO] = middleware(routes)
// 
//        import cats.implicits._
//                              ^
// <synthetic>:4: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import dev.profunktor.auth._
//                                   ^
// <synthetic>:7: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import dev.profunktor.auth.jwt._
//                                       ^
// <synthetic>:10: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// 
//        import pdi.jwt._
//                       ^
// <synthetic>:13: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
// securedRoutes: org.http4s.HttpRoutes[cats.effect.IO] = Kleisli(org.http4s.server.package$AuthMiddleware$$$Lambda$11190/452633870@3ad80a4b)
```
