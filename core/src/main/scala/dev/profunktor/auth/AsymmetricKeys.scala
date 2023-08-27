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

import cats.ApplicativeThrow
import cats.syntax.all.*
import java.security.{ KeyFactory, PrivateKey, PublicKey }
import java.security.spec.{ PKCS8EncodedKeySpec, X509EncodedKeySpec }
import pdi.jwt.algorithms.*
import pdi.jwt.{ JwtBase64, JwtUtils }

final case class PKCS8(value: String) extends AnyVal

final case class JwtPrivateKey(key: PrivateKey, algorithm: JwtAsymmetricAlgorithm)

object JwtPrivateKey {
  def make[F[_]: ApplicativeThrow](
      privateKey: PKCS8,
      algorithm: JwtAsymmetricAlgorithm
  ): F[JwtPrivateKey] = {
    val parsedPrivateKey = algorithm match {
      case _: JwtRSAAlgorithm   => ParserKey.parsePrivateKey[F](privateKey.value, JwtUtils.RSA)
      case _: JwtECDSAAlgorithm => ParserKey.parsePrivateKey[F](privateKey.value, JwtUtils.ECDSA)
      case _: JwtEdDSAAlgorithm => ParserKey.parsePrivateKey[F](privateKey.value, JwtUtils.EdDSA)
    }
    parsedPrivateKey.map(key => JwtPrivateKey(key, algorithm))
  }
}

final case class JwtPublicKey(key: PublicKey, algorithm: Seq[JwtAsymmetricAlgorithm])

object JwtPublicKey {

  def rsa[F[_]: ApplicativeThrow](
      publicKey: String,
      algorithms: Seq[JwtRSAAlgorithm]
  ): F[JwtPublicKey] =
    build[F](publicKey, algorithms, JwtUtils.RSA)

  def ecdsa[F[_]: ApplicativeThrow](
      publicKey: String,
      algorithms: Seq[JwtECDSAAlgorithm]
  ): F[JwtPublicKey] =
    build[F](publicKey, algorithms, JwtUtils.ECDSA)

  private def build[F[_]: ApplicativeThrow](
      publicKey: String,
      algorithms: Seq[JwtAsymmetricAlgorithm],
      keyAlgo: String
  ): F[JwtPublicKey] =
    ParserKey.parsePublicKey[F](publicKey, keyAlgo).map(key => JwtPublicKey(key, algorithms))
}

object ParserKey {

  def parsePrivateKey[F[_]](key: String, keyAlgo: String)(implicit F: ApplicativeThrow[F]): F[PrivateKey] = {
    val spec = new PKCS8EncodedKeySpec(parseKey(key))
    F.catchNonFatal(KeyFactory.getInstance(keyAlgo).generatePrivate(spec))
  }

  def parsePublicKey[F[_]](key: String, keyAlgo: String)(implicit F: ApplicativeThrow[F]): F[PublicKey] = {
    val spec = new X509EncodedKeySpec(parseKey(key))
    F.catchNonFatal(KeyFactory.getInstance(keyAlgo).generatePublic(spec))
  }

  private def parseKey(key: String): Array[Byte] = JwtBase64.decodeNonSafe(
    key
      .replaceAll("-----BEGIN (.*)-----", "")
      .replaceAll("-----END (.*)-----", "")
      .replaceAll("\r\n", "")
      .replaceAll("\n", "")
      .trim
  )

}
