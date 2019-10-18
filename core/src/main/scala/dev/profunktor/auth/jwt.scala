package dev.profunktor.auth

import cats._
import cats.implicits._
import pdi.jwt._
import pdi.jwt.algorithms.JwtHmacAlgorithm

object jwt {

  case class JwtToken(value: String) extends AnyVal
  case class JwtSecretKey(value: String) extends AnyVal

  case class JwtAuth(
      secretKey: JwtSecretKey,
      jwtAlgorithm: JwtHmacAlgorithm
  )

  // ----- Common JWT Functions -----

  def jwtDecode[F[_]: ApplicativeError[?[_], Throwable]](
      jwtToken: JwtToken,
      jwtSecretKey: JwtSecretKey,
      jwtAlgorithm: JwtHmacAlgorithm
  ): F[JwtClaim] =
    Jwt.decode(jwtToken.value, jwtSecretKey.value, Seq(jwtAlgorithm)).liftTo[F]

  def jwtEncode[F[_]: Applicative](
      jwtClaim: JwtClaim,
      jwtSecretKey: JwtSecretKey,
      jwtAlgorithm: JwtHmacAlgorithm
  ): F[JwtToken] =
    JwtToken(Jwt.encode(jwtClaim, jwtSecretKey.value, jwtAlgorithm)).pure[F]

}
