package com.moonnl.data.exposed

import com.moonnl.data.models.User
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNotNull
import java.util.*

object UserTable : UUIDTable("user", "id") {
    val username = varchar("username", 20)
    val password = varchar("password", 100)
    val salt = varchar("salt", 100)

    val userInfo = reference("user_info", UserInfoTable).nullable()
}

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(UserTable)

    var username by UserTable.username
    var password by UserTable.password
    var salt by UserTable.salt

    var userInfo by UserInfoEntity optionalReferencedOn UserTable.userInfo

    fun asUser(): User = User(id.value, username, password, salt)
}
