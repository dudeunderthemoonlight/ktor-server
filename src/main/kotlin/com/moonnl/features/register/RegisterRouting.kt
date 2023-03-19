package com.moonnl.features.register

import com.moonnl.data.UserDataSource
import com.moonnl.data.models.User
import com.moonnl.data.models.requests.AuthRequest
import com.moonnl.security.hashing.HashingService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.signUp(
    hashingService: HashingService,
    userDataSource: UserDataSource
) {
    post("signup") {
        val request = kotlin.runCatching { call.receiveNullable<AuthRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val areFieldsBlank = request.username.isBlank() || request.password.isBlank()
        val isPasswordShort = request.password.length < 8

        if (userDataSource.getUserByUsername(request.username) != null) {
            call.respond(HttpStatusCode.Conflict, "Username is already exists")
            return@post
        }

        if (areFieldsBlank || isPasswordShort) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        val saltedHash = hashingService.generateSaltedHash(request.password)

        val user = User(
            username = request.username,
            password = saltedHash.hash,
            salt = saltedHash.salt
        )

        userDataSource.insertUser(user)
        call.respond(HttpStatusCode.OK)
        return@post
    }
}
