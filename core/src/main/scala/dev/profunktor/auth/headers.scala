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

import jwt.*
import org.http4s.*
import org.http4s.Credentials.Token
import org.http4s.headers.Authorization

object AuthHeaders {
  def getBearerToken[F[_]](request: Request[F]): Option[JwtToken] =
    request.headers.get[Authorization].collect { case Authorization(Token(AuthScheme.Bearer, token)) =>
      JwtToken(token)
    }
}
