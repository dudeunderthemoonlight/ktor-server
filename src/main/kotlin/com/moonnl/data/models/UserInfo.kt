package com.moonnl.data.models

import com.moonnl.data.models.responses.UserInfoResponse
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

data class UserInfo(
    val userId: UUID,
    val firstName: String?,
    val lastName: String?,
    val email: String?
) {
    fun asUserInfoResponse(): UserInfoResponse =
        UserInfoResponse(
            firstName = firstName!!,
            lastName = lastName!!,
            email = email!!
        )
}

