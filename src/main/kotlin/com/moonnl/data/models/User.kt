package com.moonnl.data.models

import java.util.UUID

data class User(
    val id: UUID? = null,
    val username: String,
    val password: String,
    val salt: String
)