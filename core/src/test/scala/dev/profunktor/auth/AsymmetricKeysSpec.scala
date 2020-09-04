package dev.profunktor.auth

import cats.effect.{ Clock, IO }
import cats.syntax.all._
import dev.profunktor.auth.jwt.JwtAsymmetricAuth
import java.security.spec.InvalidKeySpecException
import java.util.concurrent.TimeUnit
import org.scalatest.Assertion
import org.scalatest.funsuite.AsyncFunSuite
import org.scalatest.matchers.should.Matchers
import pdi.jwt.exceptions.JwtValidationException
import pdi.jwt.{ JwtAlgorithm, JwtClaim }
import scala.reflect.ClassTag

class AsymmetricKeysSpec extends AsyncFunSuite with Matchers {

  test("Build private key") {
    JwtPrivateKey
      .make[IO](privateKeyPKCS8, JwtAlgorithm.RS256)
      .attempt
      .map(res => assertResult(JwtAlgorithm.RS256.asRight[Throwable])(res.map(_.algorithm)))
      .unsafeToFuture()
  }

  test("Build private key with exception") {
    val encodedPrivateKey = PKCS8("wrongprivatekey")
    JwtPrivateKey
      .make[IO](encodedPrivateKey, JwtAlgorithm.RS256)
      .attempt
      .map(assertThrow[InvalidKeySpecException])
      .unsafeToFuture()
  }

  test("Build public key") {
    JwtPublicKey
      .rsa[IO](publicKey, Seq(JwtAlgorithm.RS256))
      .attempt
      .map(res => assertResult(Seq(JwtAlgorithm.RS256).asRight[Throwable])(res.map(_.algorithm)))
      .unsafeToFuture()
  }

  test("Build public key with Exception") {
    val encodedPublicKey = "wrongpublickey"
    JwtPublicKey
      .rsa[IO](encodedPublicKey, Seq(JwtAlgorithm.RS256))
      .attempt
      .map(assertThrow[InvalidKeySpecException])
      .unsafeToFuture()
  }

  test("Encode by private key and decode by public key") {
    val assertionJwtClaim = for {
      jwtClaim <- createJwtClaim
      privateKey <- JwtPrivateKey.make[IO](privateKeyPKCS8, JwtAlgorithm.RS256)
      publicKey <- JwtPublicKey.rsa[IO](publicKey, Seq(JwtAlgorithm.RS256, JwtAlgorithm.RS512))
      jwtToken <- jwt.jwtEncode[IO](jwtClaim, privateKey)
      resultJwtClaim <- jwt.jwtDecode[IO](jwtToken, JwtAsymmetricAuth(publicKey))
    } yield {
      assertResult(jwtClaim)(resultJwtClaim)
    }
    assertionJwtClaim.unsafeToFuture()
  }

  test("Encode wby private key and decode by public key") {
    val notCorrectPublicKey = publicKey.replace("Q", "B")
    val assertionJwtClaim = for {
      jwtClaim <- createJwtClaim
      privateKey <- JwtPrivateKey.make[IO](privateKeyPKCS8, JwtAlgorithm.RS256)
      publicKey <- JwtPublicKey.rsa[IO](notCorrectPublicKey, Seq(JwtAlgorithm.RS256, JwtAlgorithm.RS512))
      jwtToken <- jwt.jwtEncode[IO](jwtClaim, privateKey)
      _ <- jwt.jwtDecode[IO](jwtToken, JwtAsymmetricAuth(publicKey))
    } yield ()
    assertionJwtClaim.attempt.map(assertThrow[JwtValidationException]).unsafeToFuture()
  }

  private def assertThrow[E: ClassTag](errorOrResult: Either[Throwable, _]): Assertion = {
    val isCorrectException = true
    val isOtherException   = false
    val isRightValue       = false
    errorOrResult.fold(
      {
        case _: E => assert(isCorrectException)
        case _    => assert(isOtherException)
      },
      _ => assert(isRightValue)
    )

  }

  private def createJwtClaim: IO[JwtClaim] =
    Clock
      .create[IO]
      .realTime(TimeUnit.SECONDS)
      .map(expiration => JwtClaim("{\"userId\":123}", expiration = (expiration + 1000).some))

  private def privateKeyPKCS8: PKCS8 =
    PKCS8(
      "-----BEGIN PRIVATE KEY-----\n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\npiPMxcCEQ0O+phpOkj31E48bvSDJdr0=\n-----END PRIVATE KEY-----"
    )

  private def publicKey: String =
    "-----BEGIN PUBLIC KEY-----\nMIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA7GHLMxNE2PMCublTqGIa\nCEHljm03+VU1H8CA4JkX7ldq5iM6J4uOx2dP8pAnnkTnBK6D1+rb1QCu0FIesq8F\n8YSoEycUNONIm6dsb/61rTgkYVBqbfSdQOfEzj62nOhcB6mprAqVa6GiHat3lnv6\ntC/2NGX9pII6BroWJ0kh3f08yEp9Cn5fblOvB09uqyp/z8o8Lm0VX2u2ZdzkZrQh\n2CML9QRNxe7Qi5EDCMXrgWRR08PxVo9sb70lqtUKvIhN7A2tcsvVJlinJXcJ/4gh\nZozmU7u8AVMh6w+vDyqkV2CsncEjqXTtziTTc0UA76Op/XWX+nRfELkHtEuuIynK\nHgNdW6uBYYsfxQAR7DgAzU3lzpmpnsRQhsMXE/6bznXANfY+Xk4jPPUIlU5ek/b3\nh+Er1o+v+p24XPHlWGtu2Ewe5mUql0BVV/SLS5Ep0qhpSa9s3AgZ59/Lla6w3aJ0\nBpYrrOlHyArX4OqePPZeWRy3QUxbuqpXj5fsORWnnWMsAzEf70uXLdDYojjLRcbp\nTyLahBaLhbB9ZfXObqyp+j0AIq7VjxrZa4xk2fC3jx+ljZlrzRO5HQ4LtXb+TbLJ\nZJQEMEkzNWuIhSUzNmaTHzJyV8OP0VCBIV52UzXWe3qvimYuI1mG9pM4nsV4JBjm\nAQQZ+1xVQdaBx8IeYLm59bsCAwEAAQ==\n-----END PUBLIC KEY-----"

}
