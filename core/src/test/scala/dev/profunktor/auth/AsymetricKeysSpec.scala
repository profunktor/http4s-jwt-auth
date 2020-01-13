package dev.profunktor.auth

import java.security.{InvalidKeyException, KeyPairGenerator}
import java.util.Base64

import cats.effect.IO
import cats.implicits._
import org.scalatest.funsuite.AsyncFunSuite
import pdi.jwt.JwtAlgorithm

class AsymetricKeysSpec extends AsyncFunSuite {

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
        .map { res =>
            println(res.leftMap(x => x.printStackTrace()))

          assertThrows[InvalidKeyException]()
          assertResult(JwtAlgorithm.RS256.asRight[Throwable])(res.map(_.algorithm))
//          assertResult(expectedPrivateKeyBytes)(res.map(_.key.getEncoded.sorted.toList))
        }
        .unsafeToFuture()
  }

  private def generateKeyPair() = {
    val kpg = KeyPairGenerator.getInstance("RSA")
    kpg.initialize(2048)
    kpg.generateKeyPair()
  }

  private def encodeKey(key: Array[Byte]) = {
    val encoder = Base64.getEncoder
    val l = encoder.encodeToString(key)
    s"-----BEGIN RSA PRIVATE KEY-----\n$l\n-----END RSA PRIVATE KEY-----\n"
  }

}
