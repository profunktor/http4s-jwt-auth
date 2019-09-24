http4s-jwt-auth
===============

Opinionated [JWT](https://tools.ietf.org/html/rfc7519) authentication library for [http4s](https://http4s.org/).

### Additional Dependencies

- [jwt-scala](https://github.com/pauldijou/jwt-scala)
- [newtype](https://github.com/estatico/scala-newtype)

### Usage

```scala
import cats.effect.IO
import dev.profunktor.auth.jwt._
import io.estatico.newtype.macros.newtype
import pdi.jwt._

@newtype case class AuthUser(value: String)

val jwtAuth    = JwtAuth("53cr3t".coerce[JwtSecretKey], JwtAlgorithm.HS256)
val middleware = JwtAuthMiddleware[IO, AuthUser](jwtAuth)

val routes: HttpRoutes[IO] = ???
val securedRoutes: HttpRoutes[IO] = middleware(routes)
```

### Notes

This library is quite opinionated, use with caution. Examples and docs coming soon!
