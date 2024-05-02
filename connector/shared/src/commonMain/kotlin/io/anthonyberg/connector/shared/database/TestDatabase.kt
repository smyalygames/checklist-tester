package io.anthonyberg.connector.shared.database

internal class TestDatabase (driverFactory: DriverFactory) {
    private val database = Database(driverFactory.createDriver())
    private val dbQuery = database.testQueries

    /**
     * Creates a Test record on the database
     *
     * @return ID of the Test created
     */
    internal fun startTest(procedureId: Long, startUTC: String) : Long {
        dbQuery.startTest(
            procedureId = procedureId,
            startUTC = startUTC,
        )

        return dbQuery.lastInsertedRowId().executeAsOne()
    }

    /**
     * Updates Test record to include the end time
     */
    internal fun endTest(testId: Long, endUTC: String) {
        dbQuery.endTest(
            id = testId,
            endUTC = endUTC,
        )
    }
}
