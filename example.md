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

val authenticate: JwtClaim => IO[Option[AuthUser]] =
  claim => AuthUser(123L, "joe").some.pure[IO]
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
//        import org.http4s._
//                          ^
// <synthetic>:16: warning: Unused import
// 
// (To diagnose errors in synthetic code, try adding `// show` to the end of your input.)
//          claim => AuthUser(123L, "joe").some.pure[IO]
//          ^
// On line 3: warning: parameter value claim in anonymous function is never used
// authenticate: pdi.jwt.JwtClaim => cats.effect.IO[Option[AuthUser]] = $$Lambda$6282/681092789@7ebfb7da

val jwtAuth    = JwtAuth(JwtSecretKey("53cr3t"), JwtAlgorithm.HS256)
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
// jwtAuth: dev.profunktor.auth.jwt.JwtAuth = JwtAuth(JwtSecretKey(53cr3t),HS256)

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
// middleware: org.http4s.server.AuthMiddleware[cats.effect.IO,AuthUser] = org.http4s.server.package$AuthMiddleware$$$Lambda$6294/1191670308@e3dff6e

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
// securedRoutes: org.http4s.HttpRoutes[cats.effect.IO] = Kleisli(org.http4s.server.package$AuthMiddleware$$$Lambda$6296/1575448323@69ee2689)
```
