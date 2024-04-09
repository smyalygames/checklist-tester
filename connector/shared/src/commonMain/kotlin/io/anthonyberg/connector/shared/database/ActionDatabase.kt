package io.anthonyberg.connector.shared.database

class ActionDatabase (driverFactory: DriverFactory) {
    private val database = Database(driverFactory.createDriver())
    private val dbQuery = database.actionQueries
}
