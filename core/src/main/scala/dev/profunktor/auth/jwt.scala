/*
 * Copyright 2019 ProfunKtor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.profunktor.auth

import java.security.PrivateKey
import java.nio.charset.StandardCharsets
import cats.*
import cats.syntax.all.*
import pdi.jwt.*
import pdi.jwt.algorithms.JwtHmacAlgorithm

object jwt {

  case class JwtToken(value: String) extends AnyVal

  object JwtSecretKey {
    def apply(key: Array[Byte]): JwtSecretKey = new JwtSecretKeyByteArr(key)
    def apply(key: Array[Char]): JwtSecretKey = new JwtSecretKeyCharArr(key)
    def apply(key: PrivateKey): JwtSecretKey  = new JwtSecretKeyPK(key)
  }
  sealed trait JwtSecretKey {
    def value: Array[Char]
  }
  private class JwtSecretKeyCharArr(val value: Array[Char]) extends JwtSecretKey
  private class JwtSecretKeyByteArr(bytes: Array[Byte]) extends JwtSecretKey {
    lazy val value = {
      val byteBuffer = java.nio.ByteBuffer.wrap(bytes)
      val charBuffer = StandardCharsets.UTF_8.decode(byteBuffer)
      val charArray  = new Array[Char](charBuffer.remaining())
      charBuffer.get(charArray)
      charArray
    }
  }
  private class JwtSecretKeyPK(key: PrivateKey) extends JwtSecretKey {
    lazy val value = {
      val byteBuffer = java.nio.ByteBuffer.wrap(key.getEncoded())
      val charBuffer = StandardCharsets.UTF_8.decode(byteBuffer)
      val charArray  = new Array[Char](charBuffer.remaining())
      charBuffer.get(charArray)
      charArray
    }
  }

  sealed trait JwtAuth
  case object JwtNoValidation extends JwtAuth
  case class JwtSymmetricAuth(secretKey: JwtSecretKey, jwtAlgorithms: Seq[JwtHmacAlgorithm]) extends JwtAuth
  case class JwtAsymmetricAuth(publicKey: JwtPublicKey) extends JwtAuth
  object JwtAuth {
    def noValidation: JwtAuth = JwtNoValidation
    @deprecated(message = "use of string to hold secret keys is deprecated and potentially unsafe", since = "2.x")
    def hmac(secretKey: String, algorithm: JwtHmacAlgorithm): JwtSymmetricAuth =
      JwtSymmetricAuth(JwtSecretKey(secretKey.toArray[Char]), Seq(algorithm))
    def hmac(secretKey: Array[Char], algorithm: JwtHmacAlgorithm): JwtSymmetricAuth =
      JwtSymmetricAuth(JwtSecretKey(secretKey), Seq(algorithm))
    @deprecated(message = "use of string to hold secret keys is deprecated and potentially unsafe", since = "2.x")
    def hmac(secretKey: String, algorithms: Seq[JwtHmacAlgorithm] = JwtAlgorithm.allHmac()): JwtSymmetricAuth =
      JwtSymmetricAuth(JwtSecretKey(secretKey.toArray[Char]), algorithms)
    def hmac(
        secretKey: Array[Char],
        algorithms: Seq[JwtHmacAlgorithm]
    ): JwtSymmetricAuth =
      JwtSymmetricAuth(JwtSecretKey(secretKey), algorithms)
  }

  // ----- Common JWT Functions -----

  def jwtDecode[F[_]: ApplicativeThrow](
      jwtToken: JwtToken,
      jwtAuth: JwtAuth
  ): F[JwtClaim] =
    (jwtAuth match {
      case JwtNoValidation => Jwt.decode(jwtToken.value, JwtOptions.DEFAULT.copy(signature = false))
      case JwtSymmetricAuth(secretKey, algorithms) => Jwt.decode(jwtToken.value, secretKey.value.mkString, algorithms)
      case JwtAsymmetricAuth(publicKey)            => Jwt.decode(jwtToken.value, publicKey.key, publicKey.algorithm)
    }).liftTo[F]

  def jwtEncode[F[_]: Applicative](
      jwtClaim: JwtClaim,
      jwtSecretKey: JwtSecretKey,
      jwtAlgorithm: JwtHmacAlgorithm
  ): F[JwtToken] =
    JwtToken(Jwt.encode(jwtClaim, jwtSecretKey.value.mkString, jwtAlgorithm)).pure[F]

  def jwtEncode[F[_]](jwtClaim: JwtClaim, jwtPrivateKey: JwtPrivateKey)(implicit
      F: ApplicativeError[F, Throwable]
  ): F[JwtToken] =
    F.catchNonFatal(JwtToken(Jwt.encode(jwtClaim, jwtPrivateKey.key, jwtPrivateKey.algorithm)))
}
