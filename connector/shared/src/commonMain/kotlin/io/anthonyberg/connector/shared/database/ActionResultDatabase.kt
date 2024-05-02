package io.anthonyberg.connector.shared.database

internal class ActionResultDatabase (driverFactory: DriverFactory) {
    private val database = Database(driverFactory.createDriver())
    private val dbQuery = database.actionResultQueries

    /**
     * Adds the start of a test for an action to the database
     *
     * @return ID of the ActionResult created
     */
    internal fun startResult(
        testId: Long,
        actionId: Long,
        initState: String,
        startUTC: String
    ): Long {
        dbQuery.startResult(
            testId = testId,
            actionId = actionId,
            initState = initState,
            startUTC = startUTC
        )

        return dbQuery.lastInsertedRowId().executeAsOne()
    }

    /**
     * Updates the final results of the test for the action in the database
     */
    internal fun finishResult(id: Long, endState: String, endUTC: String) {
        dbQuery.finishResult(
            id = id,
            endState = endState,
            endUTC = endUTC,
        )
    }
}
