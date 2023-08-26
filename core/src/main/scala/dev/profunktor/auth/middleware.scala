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

import cats.MonadThrow
import cats.data.{ Kleisli, OptionT }
import cats.syntax.all.*
import jwt.*
import org.http4s.{ AuthedRoutes, Request }
import org.http4s.dsl.Http4sDsl
import org.http4s.server.AuthMiddleware
import pdi.jwt.*
import pdi.jwt.exceptions.JwtException

object JwtAuthMiddleware {
  def apply[F[_]: MonadThrow, A](
      jwtAuth: JwtAuth,
      authenticate: JwtToken => JwtClaim => F[Option[A]]
  ): AuthMiddleware[F, A] =
    apply(jwtAuth.pure, authenticate)

  def apply[F[_]: MonadThrow, A](
      jwtAuth: F[JwtAuth],
      authenticate: JwtToken => JwtClaim => F[Option[A]]
  ): AuthMiddleware[F, A] = {
    val dsl = new Http4sDsl[F] {}; import dsl.*

    val onFailure: AuthedRoutes[String, F] =
      Kleisli(req => OptionT.liftF(Forbidden(req.context)))

    val authUser: Kleisli[F, Request[F], Either[String, A]] =
      Kleisli { request =>
        AuthHeaders.getBearerToken(request).fold("Bearer token not found".asLeft[A].pure[F]) { token =>
          jwtAuth.flatMap(auth =>
            jwtDecode[F](token, auth)
              .flatMap(authenticate(token))
              .map(_.fold("not found".asLeft[A])(_.asRight[String]))
              .recover { case _: JwtException =>
                "Invalid access token".asLeft[A]
              }
          )
        }
      }
    AuthMiddleware(authUser, onFailure)
  }
}
