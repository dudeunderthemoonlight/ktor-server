package com.moonnl.data

import com.moonnl.data.models.UserInfo
import java.util.UUID

interface UserInfoDataSource {
    suspend fun getUserInfo(id: UUID): UserInfo?
    suspend fun addNewUserInfo(firstName: String, lastName: String, email: String): UserInfo?
}