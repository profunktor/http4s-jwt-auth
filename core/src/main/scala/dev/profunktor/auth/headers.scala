package dev.profunktor.auth

import io.estatico.newtype.ops._
import jwt._
import org.http4s._
import org.http4s.Credentials.Token
import org.http4s.headers.Authorization

object AuthHeaders {
  def getBearerToken[F[_]](request: Request[F]): Option[JwtToken] =
    request.headers.get(Authorization).collect {
      case Authorization(Token(AuthScheme.Bearer, token)) => token.coerce[JwtToken]
    }
}
