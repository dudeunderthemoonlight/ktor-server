package com.moonnl.data.exposed

import com.moonnl.data.models.UserInfo
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object UserInfoTable : UUIDTable("user_info", "user_id") {
    val firstName = varchar("first_name", 30).nullable()
    val lastName = varchar("last_name", 30).nullable()
    val email = varchar("email", 20).nullable()
}

class UserInfoEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserInfoEntity>(UserInfoTable)

    var firstName by UserInfoTable.firstName
    var lastName by UserInfoTable.lastName
    var email by UserInfoTable.email

    fun asUserInfo(): UserInfo = UserInfo(id.value, firstName, lastName, email)
}
