package io.anthonyberg.connector.shared.database

import io.anthonyberg.connector.shared.entity.Action

class ActionDatabase (driverFactory: DriverFactory) {
    private val database = Database(driverFactory.createDriver())
    private val dbQuery = database.actionQueries

    /**
     * Inserts an Action to the database
     */
    internal fun createAction(procedureId: Long, step: Long, type: String, goal: String) {
        dbQuery.createAction(
            procedureId = procedureId,
            step = step,
            type = type,
            goal = goal,
        )
    }

    /**
     * Inserts a list of Action to the database
     */
    internal fun createMultipleActions(actions: List<Action>) {
        dbQuery.transaction {
            actions.forEach { action ->
                dbQuery.createAction(
                    procedureId = action.procedureId.toLong(),
                    step = action.step.toLong(),
                    type = action.type,
                    goal = action.goal,
                )
            }
        }
    }

    /**
     * Gets all Actions for a procedure
     *
     * @param procedureId id of procedure in database
     * @return List of all Actions for that certain procedure
     */
    internal fun getActions(procedureId: Long): List<Action> {
        return dbQuery.selectActions(procedureId, ::mapActionSelecting).executeAsList()
    }

    /**
     * Counts all actions that exist for a procedure
     *
     * @param procedureId ID of the procedure to count for
     * @return Count of all the actions for the procedure specified
     */
    internal fun countActions(procedureId: Long): Long {
        return dbQuery.countActions(procedureId = procedureId).executeAsOne()
    }

    /**
     * Deletes all actions in a procedure
     * @param procedureId id of procedure to delete all actions from
     */
    internal fun deleteByProcedure(procedureId: Long) {
        dbQuery.deleteByProcedure(procedureId = procedureId)
    }

    private fun mapActionSelecting(
        id: Long,
        procedureId: Long,
        step: Long,
        type: String,
        goal: String,
    ): Action {
        return Action(
            id = id.toInt(),
            procedureId = procedureId.toInt(),
            step = step.toInt(),
            type = type,
            goal = goal,
        )
    }
}
