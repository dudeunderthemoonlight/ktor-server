package com.moonnl

import com.moonnl.data.DatabaseFactory
import com.moonnl.data.exposed.ExposedUserDataSource
import com.moonnl.data.exposed.ExposedUserInfoDataSource
import io.ktor.server.application.*
import com.moonnl.plugins.*
import com.moonnl.security.hashing.SHA256HashingService
import com.moonnl.security.token.JwtTokenService
import com.moonnl.security.token.TokenConfig
import io.github.cdimascio.dotenv.dotenv

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {

    val dotenv = dotenv()

    val userDataSource = ExposedUserDataSource()
    val userInfoDataSource = ExposedUserInfoDataSource()

    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 24L,
        secret = dotenv["JWT_SECRET"]
    )
    val hashingService = SHA256HashingService()

    DatabaseFactory.init()
    configureMonitoring()
    configureSerialization()
    configureSecurity(tokenConfig)
    configureRouting(
        userDataSource = userDataSource,
        userInfoDataSource =userInfoDataSource,
        hashingService = hashingService,
        tokenService = tokenService,
        tokenConfig = tokenConfig
    )
}
