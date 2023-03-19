package com.moonnl.plugins

import com.moonnl.data.UserDataSource
import com.moonnl.data.UserInfoDataSource
import com.moonnl.features.login.signIn
import com.moonnl.features.register.signUp
import com.moonnl.features.userinfo.getUserInfo
import com.moonnl.security.hashing.HashingService
import com.moonnl.security.token.JwtTokenService
import com.moonnl.security.token.TokenConfig
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting(
    userDataSource: UserDataSource,
    userInfoDataSource: UserInfoDataSource,
    hashingService: HashingService,
    tokenService: JwtTokenService,
    tokenConfig: TokenConfig
) {
    routing {
        signUp(hashingService, userDataSource)
        signIn(userDataSource, hashingService, tokenService, tokenConfig)
        getUserInfo(userInfoDataSource)
    }
}
