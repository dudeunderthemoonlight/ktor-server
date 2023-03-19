package com.moonnl.data.exposed

import com.moonnl.data.DatabaseFactory.dbQuery
import com.moonnl.data.UserInfoDataSource
import com.moonnl.data.models.UserInfo
import org.jetbrains.exposed.sql.select
import java.util.*

class ExposedUserInfoDataSource : UserInfoDataSource {

    override suspend fun getUserInfo(id: UUID): UserInfo? = dbQuery {
        UserInfoEntity.find { UserInfoTable.id eq id }.firstOrNull()?.asUserInfo()
    }

    override suspend fun addNewUserInfo(firstName: String, lastName: String, email: String): UserInfo = dbQuery {
        UserInfoEntity.new {
            this.firstName = firstName
            this.lastName = lastName
            this.email = email
        }.asUserInfo()
    }
}