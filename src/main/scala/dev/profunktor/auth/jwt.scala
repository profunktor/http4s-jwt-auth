package dev.profunktor.auth

import io.estatico.newtype.macros.newtype
import pdi.jwt._

object jwt {

  @newtype case class JwtToken(value: String)
  @newtype case class JwtSecretKey(value: String)

  case class JwtAuth(
      secretKey: JwtSecretKey,
      jwtClaimStr: JwtClaim,
      jwtAlgorithm: JwtAlgorithm
  )

}
