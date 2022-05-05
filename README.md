http4s-jwt-auth
===============

[![CI Status](https://github.com/profunktor/http4s-jwt-auth/workflows/Build/badge.svg)](https://github.com/profunktor/http4s-jwt-auth/actions)
[![Gitter Chat](https://badges.gitter.im/profunktor-dev/http4s-jwt-auth.svg)](https://gitter.im/profunktor-dev/http4s-jwt-auth)
[![Maven Central](https://img.shields.io/maven-central/v/dev.profunktor/http4s-jwt-auth_2.12.svg)](http://search.maven.org/#search%7Cga%7C1%7Chttp4s-jwt-auth) <a href="https://typelevel.org/cats/"><img src="https://raw.githubusercontent.com/typelevel/cats/c23130d2c2e4a320ba4cde9a7c7895c6f217d305/docs/src/main/resources/microsite/img/cats-badge.svg" height="40px" align="right" alt="Cats friendly" /></a>
[![MergifyStatus](https://img.shields.io/endpoint.svg?url=https://gh.mergify.io/badges/profunktor/http4s-jwt-auth&style=flat)](https://mergify.io)
[![Scala Steward badge](https://img.shields.io/badge/Scala_Steward-helping-brightgreen.svg?style=flat&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAQCAMAAAARSr4IAAAAVFBMVEUAAACHjojlOy5NWlrKzcYRKjGFjIbp293YycuLa3pYY2LSqql4f3pCUFTgSjNodYRmcXUsPD/NTTbjRS+2jomhgnzNc223cGvZS0HaSD0XLjbaSjElhIr+AAAAAXRSTlMAQObYZgAAAHlJREFUCNdNyosOwyAIhWHAQS1Vt7a77/3fcxxdmv0xwmckutAR1nkm4ggbyEcg/wWmlGLDAA3oL50xi6fk5ffZ3E2E3QfZDCcCN2YtbEWZt+Drc6u6rlqv7Uk0LdKqqr5rk2UCRXOk0vmQKGfc94nOJyQjouF9H/wCc9gECEYfONoAAAAASUVORK5CYII=)](https://scala-steward.org)

Opinionated [JWT](https://tools.ietf.org/html/rfc7519) authentication library for [http4s](https://http4s.org/).

### Dependencies

[jwt-scala](https://github.com/pauldijou/jwt-scala) is being used to encode and decode JWT tokens.

Add the following dependency to your `build.sbt` (check latest version on the badge):

```
"dev.profunktor" %% "http4s-jwt-auth" % Version
```

### Usage

```scala
import cats.effect.IO
import cats.implicits._
import dev.profunktor.auth._
import dev.profunktor.auth.jwt._
import pdi.jwt._
import org.http4s._

case class AuthUser(id: Long, name: String)

// i.e. retrieve user from database
val authenticate: JwtToken => IO[Option[AuthUser]] =
  token => AuthUser(123L, "joe").some.pure[IO]

val jwtAuth    = JwtAuth.hmac("53cr3t", JwtAlgorithm.HS256)
val middleware = JwtAuthMiddleware[IO, AuthUser](jwtAuth, authenticate)

val routes: AuthedRoutes[AuthUser, IO] = ???
val securedRoutes: HttpRoutes[IO] = middleware(routes)
```

### Build microsite

If you only need `jekyll` for this, it is recommended to use `nix-shell` to avoid installing it globally:

```
nix-shell -p jekyll
sbt makeMicrosite
cd site/target/site && jekyll serve
```

### Notes

This library is quite opinionated, use with caution. Examples and docs coming soon!

If you would like to see support for any other functionality come have a chat in the Gitter channel!
