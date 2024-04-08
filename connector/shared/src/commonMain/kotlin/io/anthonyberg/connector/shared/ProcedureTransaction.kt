package io.anthonyberg.connector.shared

import io.anthonyberg.connector.shared.database.DriverFactory
import io.anthonyberg.connector.shared.database.ProcedureDatabase
import io.anthonyberg.connector.shared.entity.Procedure
import kotlinx.datetime.Clock


/**
 * All database transactions for Procedure
 */
class ProcedureTransaction (driverFactory: DriverFactory) {
    private val database = ProcedureDatabase(driverFactory)

    /**
     * Creates a procedure for a project
     *
     * @param projectId ID of the selected Project
     * @param name Name of the procedure
     * @param type Procedure type (e.g. Normal, Emergency)
     * @param description Description of what the procedure will do
     */
    fun createProcedure(projectId: Int, name: String, type: String, description: String) {
        val currentTime = Clock.System.now().toString()

        database.createProcedure(
            projectId = projectId,
            name = name,
            type = type,
            description = description,
            createdUTC = currentTime
        )
    }

    /**
     * Gets all procedures in current project
     *
     * @param projectId ID of the selected Project
     * @return List of [Procedure] in current project
     */
    fun getProcedures(projectId: Int): List<Procedure> {

        val procedures = database.getAllProcedures(projectId = projectId)

        return procedures
    }

    /**
     * Gets a specific procedure by its ID
     *
     * @param id Procedure ID
     * @return [Procedure] based on ID given
     */
    fun getProcedureById(id: Int): Procedure {
        val procedure = database.getProcedureById(id)

        return procedure
    }

    /**
     * Counts the amount of procedures that exist based on the current project
     *
     * @param projectId ID of current Project
     * @return Number of procedures in the database
     */
    fun countProcedures(projectId: Int): Long {
        return database.countProcedures(projectId = projectId)
    }

    /**
     * Checks if there are procedures on the database for the current project
     *
     * @param projectId ID of current Project
     * @return `true` if procedures exist for project
     */
    fun proceduresExist(projectId: Int): Boolean {
        return database.countProcedures(projectId = projectId) > 0
    }
}
