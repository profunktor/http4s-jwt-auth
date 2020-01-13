package dev.profunktor.auth

import cats._
import cats.implicits._
import pdi.jwt._
import pdi.jwt.algorithms.JwtHmacAlgorithm

import scala.util.Try

object jwt {

  case class JwtToken(value: String) extends AnyVal

  case class JwtSecretKey(value: String) extends AnyVal

  sealed trait JwtAuth
  case object JwtNoValidation extends JwtAuth
  case class JwtSymmetricAuth(secretKey: JwtSecretKey, jwtAlgorithms: Seq[JwtHmacAlgorithm]) extends JwtAuth
  case class JwtAsymmetricAuth(publicKey: JwtPublicKey) extends JwtAuth
  object JwtAuth {
    def noValidation: JwtAuth = JwtNoValidation
    def hmac(secretKey: String, algorithm: JwtHmacAlgorithm): JwtSymmetricAuth =
      JwtSymmetricAuth(JwtSecretKey(secretKey), Seq(algorithm))
    def hmac(secretKey: String, algorithms: Seq[JwtHmacAlgorithm] = JwtAlgorithm.allHmac()): JwtSymmetricAuth =
      JwtSymmetricAuth(JwtSecretKey(secretKey), algorithms)
  }

  // ----- Common JWT Functions -----

  def jwtDecode[F[_]: ApplicativeError[*[_], Throwable]](
      jwtToken: JwtToken,
      jwtAuth: JwtAuth
  ): F[JwtClaim] =
    (jwtAuth match {
      case JwtNoValidation                          => Jwt.decode(jwtToken.value, JwtOptions.DEFAULT.copy(signature = false))
      case JwtSymmetricAuth(secretKey, algorithms)  => Jwt.decode(jwtToken.value, secretKey.value, algorithms)
      case JwtAsymmetricAuth(publicKey)             => Jwt.decode(jwtToken.value, publicKey.key, publicKey.algorithm)
    }).liftTo[F]

  def jwtEncode[F[_]: Applicative](
      jwtClaim: JwtClaim,
      jwtSecretKey: JwtSecretKey,
      jwtAlgorithm: JwtHmacAlgorithm
  ): F[JwtToken] =
    JwtToken(Jwt.encode(jwtClaim, jwtSecretKey.value, jwtAlgorithm)).pure[F]

  def jwtEncode[F[_]: ApplicativeError[*[_], Throwable]](
      jwtClaim: JwtClaim,
      jwtPrivateKey: JwtPrivateKey,
  ): F[JwtToken] =
    Try(JwtToken(Jwt.encode(jwtClaim, jwtPrivateKey.key, jwtPrivateKey.algorithm))).liftTo[F]

}
