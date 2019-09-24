package dev.profunktor.auth

import pdi.jwt.algorithms.JwtHmacAlgorithm

object jwt {

  case class JwtToken(value: String) extends AnyVal
  case class JwtSecretKey(value: String) extends AnyVal

  case class JwtAuth(
      secretKey: JwtSecretKey,
      jwtAlgorithm: JwtHmacAlgorithm
  )

}
