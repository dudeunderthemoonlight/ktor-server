package com.moonnl.features.userinfo

import com.moonnl.data.UserInfoDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.getUserInfo(
    userInfoDataSource: UserInfoDataSource
) {
    authenticate {
        get("userinfo") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)

            val userInfo = userInfoDataSource.getUserInfo(UUID.fromString(userId))

            if (userInfo != null) {
                call.respond(HttpStatusCode.OK, message = userInfo.asUserInfoResponse())
            } else {
                call.respond(HttpStatusCode.Conflict, "No information for user")
            }
        }
    }
}