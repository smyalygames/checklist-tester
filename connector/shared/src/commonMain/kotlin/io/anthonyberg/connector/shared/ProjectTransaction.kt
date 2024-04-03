package io.anthonyberg.connector.shared

import io.anthonyberg.connector.shared.database.DriverFactory
import io.anthonyberg.connector.shared.database.ProjectDatabase
import io.anthonyberg.connector.shared.entity.Project

/**
 * All database transactions for Project
 */
class ProjectTransaction (driverFactory: DriverFactory) {
    private val database = ProjectDatabase(driverFactory)

    /**
     * Creates a project in the database.
     */
    fun createProject(project: Project) {
        database.createProject(project)
    }

    /**
     * Gets all Projects in the database.
     */
    fun getProjects(): List<Project> {
        val projects = database.getAllProjects()
        return projects
    }

    /**
     * Gets the number of projects in the database
     */
    fun countProjects(): Long {
        return database.countProjects()
    }

    /**
     * Checks if projects are on the database.
     * @return `true` if projects exist
     */
    fun projectExists(): Boolean {
        return database.countProjects() > 0
    }
}
