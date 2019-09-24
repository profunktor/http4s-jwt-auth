http4s-jwt-auth
===============

[![CircleCI](https://circleci.com/gh/profunktor/http4s-jwt-auth.svg?style=svg)](https://circleci.com/gh/profunktor/http4s-jwt-auth)
[![Maven Central](https://img.shields.io/maven-central/v/dev.profunktor/http4s-jwt-auth_2.12.svg)](http://search.maven.org/#search%7Cga%7C1%7Chttp4s-jwt-auth) <a href="https://typelevel.org/cats/"><img src="https://typelevel.org/cats/img/cats-badge.svg" height="40px" align="right" alt="Cats friendly" /></a>
[![MergifyStatus](https://img.shields.io/endpoint.svg?url=https://gh.mergify.io/badges/profunktor/http4s-jwt-auth&style=flat)](https://mergify.io)

Opinionated [JWT](https://tools.ietf.org/html/rfc7519) authentication library for [http4s](https://http4s.org/).

### Dependencies

[jwt-scala](https://github.com/pauldijou/jwt-scala) is being used to encode and decode JWT tokens.

### Usage

```scala
import cats.effect.IO
import dev.profunktor.auth.jwt._
import pdi.jwt._

case class AuthUser(id: Long, name: String)

// i.e. retrieve user from database
val authenticate: JwtClaim => IO[Option[AuthUser]] =
  claim => AuthUser(123L, "joe").some.pure[IO]

val jwtAuth    = JwtAuth(JwtSecretKey("53cr3t"), JwtAlgorithm.HS256)
val middleware = JwtAuthMiddleware[IO, AuthUser](jwtAuth, authenticate)

val routes: HttpRoutes[IO] = ???
val securedRoutes: HttpRoutes[IO] = middleware(routes)
```

### Notes

This library is quite opinionated, use with caution. Examples and docs coming soon!

If you would like to see support for any other functionality come have a chat in the Gitter channel!
