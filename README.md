http4s-jwt-auth
===============

[![CircleCI](https://circleci.com/gh/profunktor/http4s-jwt-auth.svg?style=svg)](https://circleci.com/gh/profunktor/http4s-jwt-auth)
[![Maven Central](https://img.shields.io/maven-central/v/dev.profunktor/http4s-jwt-auth_2.12.svg)](http://search.maven.org/#search%7Cga%7C1%7Chttp4s-jwt-auth) <a href="https://typelevel.org/cats/"><img src="https://typelevel.org/cats/img/cats-badge.svg" height="40px" align="right" alt="Cats friendly" /></a>
[![MergifyStatus](https://img.shields.io/endpoint.svg?url=https://gh.mergify.io/badges/profunktor/http4s-jwt-auth&style=flat)](https://mergify.io)

Opinionated [JWT](https://tools.ietf.org/html/rfc7519) authentication library for [http4s](https://http4s.org/).

### Additional Dependencies

- [jwt-scala](https://github.com/pauldijou/jwt-scala)
- [newtype](https://github.com/estatico/scala-newtype)

### Usage

```scala
import cats.effect.IO
import dev.profunktor.auth.jwt._
import io.estatico.newtype.macros.newtype
import io.estatico.newtype.ops._
import pdi.jwt._

@newtype case class AuthUser(value: String)

val jwtAuth    = JwtAuth("53cr3t".coerce[JwtSecretKey], JwtAlgorithm.HS256)
val middleware = JwtAuthMiddleware[IO, AuthUser](jwtAuth)

val routes: HttpRoutes[IO] = ???
val securedRoutes: HttpRoutes[IO] = middleware(routes)
```

### Notes

This library is quite opinionated, use with caution. Examples and docs coming soon!
