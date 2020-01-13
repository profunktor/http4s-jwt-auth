package dev.profunktor.auth

import java.security.KeyPairGenerator
import java.util.Base64

import cats.effect.IO
import cats.implicits._
import org.scalatest.funsuite.AsyncFunSuite
import org.scalatest.matchers.should.Matchers
import pdi.jwt.JwtAlgorithm

class AsymmetricKeysSpec extends AsyncFunSuite with Matchers {

  test("Build private key") {
    val privateKey = generateKeyPair().getPrivate
    val encodedPrivateKey = encodeKey(privateKey.getEncoded)
    JwtPrivateKey
        .make[IO](encodedPrivateKey, JwtAlgorithm.RS256)
        .attempt
        .map { res =>
          val expectedPrivateKeyBytes = privateKey.getEncoded.sorted.toList.asRight[Throwable]
          assertResult(JwtAlgorithm.RS256.asRight[Throwable])(res.map(_.algorithm))
          assertResult(expectedPrivateKeyBytes)(res.map(_.key.getEncoded.sorted.toList))
        }
        .unsafeToFuture()
  }

  test("Build private key with exception") {
    val encodedPrivateKey = "wrongprivatekey"
    JwtPrivateKey
        .make[IO](encodedPrivateKey, JwtAlgorithm.RS256)
        .attempt
        .map { res => assert(res.isLeft)}
        .unsafeToFuture()
  }

  test("Build public key") {
    val publicKey = generateKeyPair().getPublic
    val encodedPublicKey = encodeKey(publicKey.getEncoded)
    JwtPublicKey
        .rsa[IO](encodedPublicKey, Seq(JwtAlgorithm.RS256))
        .attempt
        .map { res =>
          val expectedPublicKeyBytes = publicKey.getEncoded.sorted.toList.asRight[Throwable]
          assertResult(Seq(JwtAlgorithm.RS256).asRight[Throwable])(res.map(_.algorithm))
          assertResult(expectedPublicKeyBytes)(res.map(_.key.getEncoded.sorted.toList))
        }
        .unsafeToFuture()
  }

  test("Build public key with Exception") {
    val encodedPublicKey = "wrongpublickey"
    JwtPublicKey
        .rsa[IO](encodedPublicKey, Seq(JwtAlgorithm.RS256))
        .attempt
        .map { res => assert(res.isLeft) }
        .unsafeToFuture()
  }

  private def generateKeyPair() = {
    val kpg = KeyPairGenerator.getInstance("RSA")
    kpg.initialize(2048)
    kpg.generateKeyPair()
  }

  private def encodeKey(key: Array[Byte]): String = {
    val encoder = Base64.getEncoder
    val l = encoder.encodeToString(key)
    s"-----BEGIN RSA PRIVATE KEY-----\n$l\n-----END RSA PRIVATE KEY-----\n"
  }

}
