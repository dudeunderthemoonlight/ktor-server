package com.moonnl.data.exposed

import com.moonnl.data.DatabaseFactory.dbQuery
import com.moonnl.data.UserDataSource
import com.moonnl.data.models.User
import com.moonnl.data.models.UserInfo
import java.util.*

class ExposedUserDataSource : UserDataSource {

    override suspend fun getUserByUsername(username: String): User? = dbQuery {
        UserEntity.find { UserTable.username eq username }.firstOrNull()?.asUser()
    }

    override suspend fun insertUser(user: User): User = dbQuery {
        UserEntity.new(UUID.randomUUID()) {
            username = user.username
            password = user.password
            salt = user.salt
        }.asUser()
    }
}