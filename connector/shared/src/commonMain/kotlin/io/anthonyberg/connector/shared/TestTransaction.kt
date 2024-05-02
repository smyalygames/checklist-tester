package io.anthonyberg.connector.shared

import io.anthonyberg.connector.shared.database.ActionResultDatabase
import io.anthonyberg.connector.shared.database.DriverFactory
import io.anthonyberg.connector.shared.database.TestDatabase
import kotlinx.datetime.Clock

/**
 * Database transactions for testing procedures
 */
class TestTransaction (driverFactory: DriverFactory) {
    private val testDb = TestDatabase(driverFactory)
    private val actionResultDb = ActionResultDatabase(driverFactory)

    /**
     * Starts the test on the database
     *
     * @param procedureId ID of the Procedure that is being tested
     *
     * @return ID of the Test that was started
     */
    fun startTest(procedureId: Int): Int {
        val currentTime = Clock.System.now().toString()

        val id = testDb.startTest(
            procedureId = procedureId.toLong(),
            startUTC = currentTime,
        )

        return id.toInt()
    }

    /**
     * Tells the database that the test has ended
     *
     * @param id ID of the Test
     */
    fun endTest(id: Int) {
        val currentTime = Clock.System.now()

        testDb.endTest(testId = id.toLong(), endUTC = currentTime.toString())
    }

    /**
     * Starts the test for a specific action on the database
     *
     * @param testId ID of the current test that is running
     * @param actionId ID of the current action that is being tested
     * @param initState The initial state of the action in the simulator
     *
     * @return ID of the Action created on the database
     */
    fun startAction(testId: Int, actionId: Int, initState: String): Int {
        val currentTime = Clock.System.now().toString()

        val id = actionResultDb.startResult(
            testId = testId.toLong(),
            actionId = actionId.toLong(),
            initState = initState,
            startUTC = currentTime,
        )

        return id.toInt()
    }

    /**
     * Tells the database that the test has ended and adds the final state
     *
     * @param id ID of the ActionResult
     */
    fun finishAction(id: Int, endState: String) {
        val currentTime = Clock.System.now()

        actionResultDb.finishResult(
            id = id.toLong(),
            endState = endState,
            endUTC = currentTime.toString()
        )
    }
}
