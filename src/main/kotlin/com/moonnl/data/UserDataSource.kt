package com.moonnl.data

import com.moonnl.data.models.User

interface UserDataSource {
    suspend fun getUserByUsername(username: String): User?
    suspend fun insertUser(user: User): User
}