package io.anthonyberg.connector.shared

import io.anthonyberg.connector.shared.database.ActionDatabase
import io.anthonyberg.connector.shared.database.DriverFactory
import io.anthonyberg.connector.shared.entity.Action

/**
 * All database transactions for Action
 */
class ActionTransaction (driverFactory: DriverFactory) {
    private val database = ActionDatabase(driverFactory)

    /**
     * Inserts one action into the database
     *
     * @param procedureId ID of the procedure
     * @param step The order of when the action should be executed
     * @param type Dataref for the action
     * @param goal Desired value for the dataref/type
     */
    fun createAction(procedureId: Int, step: Int, type: String, goal: String) {
        database.createAction(
            procedureId = procedureId.toLong(),
            step = step.toLong(),
            type = type,
            goal = goal,
        )
    }

    /**
     * Inserts a list of Action into the database
     *
     * @param actions List of [Action]
     */
    fun createActionFromList(actions: List<Action>) {
        database.createMultipleActions(actions = actions)
    }

    /**
     * Gets a list of Actions for the procedure
     *
     * @param procedureId ID of the procedure to get actions from
     * @return List of [Action] that are for the Procedure ID
     */
    fun getActions(procedureId: Int): List<Action> {
        val actions = database.getActions(procedureId = procedureId.toLong())

        return actions
    }

    /**
     * Counts the number of actions there are for a procedure
     *
     * @param procedureId ID of the procedure to get a count for
     * @return Amount of actions for the procedure specified
     */
    fun countActionsInProcedure(procedureId: Int): Long {
        val count = database.countActions(procedureId = procedureId.toLong())

        return count
    }

    /**
     * Deletes all actions for a specific procedure
     *
     * @param procedureId ID of the procedure to delete actions from
     */
    fun deleteActionByProcedure(procedureId: Int) {
        database.deleteByProcedure(procedureId = procedureId.toLong())
    }
}
