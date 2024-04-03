package io.anthonyberg.connector.shared.database

import io.anthonyberg.connector.shared.entity.Project

internal class ProjectDatabase (driverFactory: DriverFactory) {
    private val database = Database(driverFactory.createDriver())
    private val dbQuery = database.projectQueries

    /**
     * Gets all the Projects in the database
     */
    internal fun getAllProjects(): List<Project> {
        return dbQuery.selectAllProjects(::mapProjectSelecting).executeAsList()
    }

    private fun mapProjectSelecting(
        id: Long,
        name: String,
        aircraftType: String,
        createdUTC: String,
        modifiedUTC: String?
    ): Project {
        return Project(
            id = id.toInt(),
            name = name,
            aircraftType = aircraftType,
            createdUTC = createdUTC,
            modifiedUTC = modifiedUTC,
        )
    }

    /**
     * Inserts a project into the database
     */
    internal fun createProject(project: Project) {
        dbQuery.createProject(
            name = project.name,
            aircraftType = project.aircraftType,
            createdUTC = project.createdUTC,
        )
    }
}
