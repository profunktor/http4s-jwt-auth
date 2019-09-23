package dev.profunktor.auth

import cats.{ MonadError }
import cats.data.{ Kleisli, OptionT }
import cats.implicits._
import io.estatico.newtype.Coercible
import io.estatico.newtype.ops._
import jwt._
import org.http4s.{ AuthedRoutes, AuthScheme, Request }
import org.http4s.Credentials.Token
import org.http4s.dsl.Http4sDsl
import org.http4s.headers.Authorization
import org.http4s.server.AuthMiddleware
import pdi.jwt._
import pdi.jwt.algorithms._
import pdi.jwt.exceptions.JwtException

case class JwtAuthMiddleware[F[_]: MonadError[?[_], Throwable]](
    jwtAuth: JwtAuth
) extends Http4sDsl[F] {

  private val onFailure: AuthedRoutes[String, F] =
    Kleisli(req => OptionT.liftF(Forbidden(req.authInfo)))

  private def bearerTokenFromRequest(request: Request[F]): Option[JwtToken] =
    request.headers.get(Authorization).collect {
      case Authorization(Token(AuthScheme.Bearer, token)) => token.coerce[JwtToken]
    }

  // TODO: Any way to improve this repetitive code?
  private def decodeToken(token: JwtToken): F[JwtClaim] =
    jwtAuth.jwtAlgorithm match {
      case alg: JwtHmacAlgorithm =>
        Jwt.decode(token.value, jwtAuth.secretKey.value, Seq(alg)).liftTo[F]
      case alg: JwtRSAAlgorithm =>
        Jwt.decode(token.value, jwtAuth.secretKey.value, Seq(alg)).liftTo[F]
      case alg: JwtECDSAAlgorithm =>
        Jwt.decode(token.value, jwtAuth.secretKey.value, Seq(alg)).liftTo[F]
    }

  private def authUser[A: Coercible[String, ?]]: Kleisli[F, Request[F], Either[String, A]] =
    Kleisli { request =>
      bearerTokenFromRequest(request).fold("No token in headers".asLeft[A].pure[F]) { token =>
        decodeToken(token)
          .map(_.content.coerce[A].asRight[String])
          .recover {
            case _: JwtException => "Invalid token".asLeft[A]
          }
      }
    }

  def middleware[A: Coercible[String, ?]]: AuthMiddleware[F, A] =
    AuthMiddleware(authUser, onFailure)

}

