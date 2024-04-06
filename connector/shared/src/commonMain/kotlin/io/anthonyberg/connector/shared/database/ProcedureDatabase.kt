package io.anthonyberg.connector.shared.database

import io.anthonyberg.connector.shared.entity.Procedure

class ProcedureDatabase (driverFactory: DriverFactory) {
    private val database = Database(driverFactory.createDriver())
    private val dbQuery = database.procedureQueries

    /**
     * Creates a procedure on the database
     *
     * @param projectId Project ID
     * @param name Name of the procedure
     * @param type The type of the procedure
     * @param description Description of what the procedure will do
     * @param createdUTC Time of running this command
     */
    internal fun createProcedure(projectId: Int, name: String, type: String, description: String, createdUTC: String) {
        dbQuery.createProcedure(
            projectId = projectId.toLong(),
            name = name,
            type = type,
            description = description,
            createdUTC = createdUTC
        )
    }

    /**
     * Gets all the procedures for a project
     * @param projectId Project ID
     */
    internal fun getAllProcedures(projectId: Int): List<Procedure> {
        return dbQuery.selectProcedures(projectId.toLong(), ::mapProcedureSelecting).executeAsList()
    }

    /**
     * Gets a specific procedure by its ID
     * @param id Procedure ID
     */
    internal fun getProcedureById(id: Int): Procedure {
        return dbQuery.selectProcedureById(id.toLong(), ::mapProcedureSelecting).executeAsOne()
    }

    /**
     * Counts how many procedures there are for a project
     * @param projectId Project ID
     */
    internal fun countProcedures(projectId: Int): Long {
        return dbQuery.countProcedures(projectId.toLong()).executeAsOne()
    }

    private fun mapProcedureSelecting(
        id: Long,
        projectId: Long,
        name: String,
        type: String,
        description: String,
        createdUTC: String,
        modifiedUTC: String?
    ): Procedure {
        return Procedure(
            id = id.toInt(),
            projectId = projectId.toInt(),
            name = name,
            type = type,
            description = description,
            createdUTC = createdUTC,
            modifiedUTC = modifiedUTC,
        )
    }


}
