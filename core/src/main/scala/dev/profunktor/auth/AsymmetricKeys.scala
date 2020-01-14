package dev.profunktor.auth

import java.security.spec.{PKCS8EncodedKeySpec, X509EncodedKeySpec}
import java.security.{KeyFactory, PrivateKey, PublicKey}

import cats.implicits._
import cats.ApplicativeError
import pdi.jwt.{JwtBase64, JwtUtils}
import pdi.jwt.algorithms.{JwtAsymmetricAlgorithm, JwtECDSAAlgorithm, JwtRSAAlgorithm}

import scala.util.Try

final case class PKCS8(value: String) extends AnyVal

final case class JwtPrivateKey(key: PrivateKey, algorithm: JwtAsymmetricAlgorithm)

object JwtPrivateKey {
  def make[F[_]: ApplicativeError[*[_], Throwable]](
      privateKey: PKCS8,
      algorithm: JwtAsymmetricAlgorithm): F[JwtPrivateKey] = {
    Try(algorithm match {
      case _: JwtRSAAlgorithm => ParserKey.parsePrivateKey(privateKey.value, JwtUtils.RSA)
      case _: JwtECDSAAlgorithm => ParserKey.parsePrivateKey(privateKey.value, JwtUtils.ECDSA)
    }).liftTo[F].map(key => JwtPrivateKey(key, algorithm))
  }
}

final case class JwtPublicKey(key: PublicKey, algorithm: Seq[JwtAsymmetricAlgorithm])

object JwtPublicKey {

  def rsa[F[_]: ApplicativeError[*[_], Throwable]](
      publicKey: String,
      algorithms: Seq[JwtRSAAlgorithm]): F[JwtPublicKey] = {
    build[F](publicKey, algorithms, JwtUtils.RSA)
  }

  def ecdsa[F[_]: ApplicativeError[*[_], Throwable]](
      publicKey: String,
      algorithms: Seq[JwtECDSAAlgorithm]): F[JwtPublicKey] = {
    build[F](publicKey, algorithms, JwtUtils.ECDSA)
  }

  private def build[F[_]: ApplicativeError[*[_], Throwable]](
      publicKey: String,
      algorithms: Seq[JwtAsymmetricAlgorithm],
      keyAlgo: String): F[JwtPublicKey] = {
    Try(ParserKey.parsePublicKey(publicKey, keyAlgo)).liftTo[F].map(key => JwtPublicKey(key, algorithms))
  }
}

object ParserKey {

  def parsePrivateKey(key: String, keyAlgo: String): PrivateKey = {
    val spec = new PKCS8EncodedKeySpec(parseKey(key))
    KeyFactory.getInstance(keyAlgo).generatePrivate(spec)
  }

  def parsePublicKey(key: String, keyAlgo: String): PublicKey = {
    val spec = new X509EncodedKeySpec(parseKey(key))
    KeyFactory.getInstance(keyAlgo).generatePublic(spec)
  }

  private def parseKey(key: String): Array[Byte] = JwtBase64.decodeNonSafe(
    key.replaceAll("-----BEGIN (.*)-----", "")
        .replaceAll("-----END (.*)-----", "")
        .replaceAll("\r\n", "")
        .replaceAll("\n", "")
        .trim
  )

}
