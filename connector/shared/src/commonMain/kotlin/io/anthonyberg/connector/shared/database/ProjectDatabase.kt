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
     * Converts database [Project](io.anthonyberg.connector.shared.database.Project) data class to entity [Project] class
     */
    private fun io.anthonyberg.connector.shared.database.Project.toProject() = Project(
        id = id.toInt(),
        name = name,
        aircraftType = aircraftType,
        createdUTC = createdUTC,
        modifiedUTC = modifiedUTC,
    )

    /**
     * Gets Project from the unique ID of the Project
     * @param id Project id
     * @return [Project]
     */
    internal fun getProjectById(id: Int): Project {
        return dbQuery.selectProjectById(id.toLong()).executeAsOne().toProject()
    }

    /**
     * Inserts a project into the database
     */
    internal fun createProject(name: String, aircraftType: String, createdUTC: String) {
        dbQuery.createProject(
            name = name,
            aircraftType = aircraftType,
            createdUTC = createdUTC,
        )
    }

    /**
     * Counts the amount of entries there are for projects
     */
    internal fun countProjects(): Long {
        return dbQuery.countProjects().executeAsOne()
    }
}
