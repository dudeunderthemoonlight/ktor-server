package com.moonnl.utils

import com.moonnl.data.models.User
import com.moonnl.data.models.requests.AuthRequest
import com.moonnl.security.hashing.HashingService
import com.moonnl.security.hashing.SaltedHash

fun isValidPassword(
    user: User,
    request: AuthRequest,
    hashingService: HashingService
): Boolean =
    hashingService.verify(
        value = request.password,

        saltedHash = SaltedHash(
            hash = user.password,
            salt = user.salt
        )
    )