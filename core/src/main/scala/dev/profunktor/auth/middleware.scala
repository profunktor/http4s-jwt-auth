package dev.profunktor.auth

import cats.{ MonadError }
import cats.data.{ Kleisli, OptionT }
import cats.implicits._
import jwt._
import org.http4s.{ AuthedRoutes, Request }
import org.http4s.dsl.Http4sDsl
import org.http4s.server.AuthMiddleware
import pdi.jwt._
import pdi.jwt.exceptions.JwtException

object JwtAuthMiddleware {
  def apply[F[_]: MonadError[?[_], Throwable], A](
      jwtAuth: JwtAuth,
      authenticate: JwtClaim => F[Option[A]]
  ): AuthMiddleware[F, A] = {
    val dsl = new Http4sDsl[F] {}; import dsl._

    val onFailure: AuthedRoutes[String, F] =
      Kleisli(req => OptionT.liftF(Forbidden(req.authInfo)))

    def decodeToken(token: JwtToken): F[JwtClaim] =
      Jwt.decode(token.value, jwtAuth.secretKey.value, Seq(jwtAuth.jwtAlgorithm)).liftTo[F]

    val authUser: Kleisli[F, Request[F], Either[String, A]] =
      Kleisli { request =>
        AuthHeaders.getBearerToken(request).fold("Bearer token not found".asLeft[A].pure[F]) { token =>
          decodeToken(token)
            .flatMap(authenticate)
            .map(_.fold("not found".asLeft[A])(_.asRight[String]))
            .recover {
              case _: JwtException => "Invalid access token".asLeft[A]
            }
        }
      }

    AuthMiddleware(authUser, onFailure)
  }
}
