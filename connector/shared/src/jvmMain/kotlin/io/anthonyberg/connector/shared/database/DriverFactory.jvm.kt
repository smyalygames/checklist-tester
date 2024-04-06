package io.anthonyberg.connector.shared.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.util.*

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        val url = "jdbc:sqlite:connector.db"
        val properties = Properties().apply { put("foreign_keys", "true") }

        val driver: SqlDriver = JdbcSqliteDriver(url = url, properties = properties)
        Database.Schema.create(driver)
        return driver
    }
}
