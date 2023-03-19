package com.moonnl.features.login

import com.moonnl.data.UserDataSource
import com.moonnl.data.models.requests.AuthRequest
import com.moonnl.data.models.responses.AuthResponse
import com.moonnl.security.hashing.HashingService
import com.moonnl.security.hashing.SaltedHash
import com.moonnl.security.token.JwtTokenService
import com.moonnl.security.token.TokenClaim
import com.moonnl.security.token.TokenConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import com.moonnl.data.models.User

fun Route.signIn(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: JwtTokenService,
    tokenConfig: TokenConfig
) {
    post("signin") {
        val request = kotlin.runCatching { call.receiveNullable<AuthRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        fun generateToken(user: User): String =
            tokenService.generate(
                config = tokenConfig,
                TokenClaim(
                    name = "userId",
                    value = user.id.toString()
                )
            )

        fun isValidPassword(user: User): Boolean =
            hashingService.verify(
                value = request.password,

                saltedHash = SaltedHash(
                    hash = user.password,
                    salt = user.salt
                )
            )

        val user = userDataSource.getUserByUsername(request.username)


        if ((user == null) || !isValidPassword(user)) {
            call.respond(HttpStatusCode.Conflict, "Incorrect username or password")
            return@post
        }

        call.respond(
            status = HttpStatusCode.OK,
            message = AuthResponse(
                token = generateToken(user)
            )
        )
    }
}