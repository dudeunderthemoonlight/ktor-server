package com.moonnl.data

import com.moonnl.data.exposed.UserTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import io.github.cdimascio.dotenv.dotenv


object DatabaseFactory {
    fun init() {
        val dotenv = dotenv()

        val user = dotenv["PGUSER"]
        val password = dotenv["PGPASSWORD"]
        val jdbcURL = dotenv["JDBCURL"]
        val driver = dotenv["JDBCDRIVER"]

        val database = Database.connect(jdbcURL, driver, user, password)
    }

    private fun createTables(db: Database) = transaction(db) {
        SchemaUtils.create(UserTable)
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}