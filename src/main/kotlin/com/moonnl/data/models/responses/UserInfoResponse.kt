package com.moonnl.data.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    val firstName: String,
    val lastName: String,
    val email: String
)