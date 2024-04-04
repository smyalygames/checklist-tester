package io.anthonyberg.connector.shared.database

import io.anthonyberg.connector.shared.entity.Project
import kotlinx.datetime.Clock
import java.nio.file.Paths
import kotlin.io.path.deleteIfExists
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ProjectDatabaseTest {

    /**
     * Removes previous test databases
     */
    @BeforeTest
    fun setUp() {
        // Deletes SQLite database file if it already exists
        val db = Paths.get("connector.db")
        db.deleteIfExists()
    }

    /**
     * Create database connection
     * @return dbQuery of type [ProjectDatabase]
     */
    private fun createDatabaseConnection(): ProjectDatabase {
        val driverFactory = DriverFactory()
        val dbQuery = ProjectDatabase(driverFactory)

        return dbQuery
    }

    /**
     * Creates a boilerplate [Project] from an integer
     * Project.name will be "Project `id`"
     * @param id project number
     */
    private fun createProjectData(id: Int): Project {
        return Project(
            id = id,
            name = "Project $id",
            aircraftType = "Airbus A320",
            createdUTC = Clock.System.now().toString(),
            modifiedUTC = null,
        )
    }

    /**
     * Iterates through a list of [Project] and creates them on the database
     * @param projects list of [Project]
     * @param dbQuery initialised [ProjectDatabase]
     */
    private fun createAllProjectsDb(projects: List<Project>, dbQuery: ProjectDatabase) {
        for (project in projects) {
            dbQuery.createProject(
                name = project.name,
                aircraftType = project.aircraftType,
                createdUTC = project.createdUTC,
            )
        }
    }

    @Test
    fun `getProjectById$connector_shared_commonMain`() {
        val id = 1

        // Create dbQuery
        val dbQuery = createDatabaseConnection()

        // Create Project
        val project = createProjectData(id)

        // Insert project into database
        createAllProjectsDb(projects = listOf(project), dbQuery = dbQuery)

        // Gets item from database by id
        val projectFromDb = dbQuery.getProjectById(id)

        assertEquals(
            expected = project,
            actual = projectFromDb,
            message = "Check got correct project from database"
        )
    }

    @Test
    fun `getAllProjects$connector_shared_commonMain`() {
        val dbQuery = createDatabaseConnection()

        val numberOfProjects = 100

        // Creates `numberOfProjects` amount of Projects
        val projects: MutableList<Project> = MutableList(numberOfProjects) { index -> createProjectData(index + 1)}

        assertEquals(
            expected = numberOfProjects,
            actual = projects.size,
            message = "Number of projects to be inserted into database is expected value"
        )

        // Pushes all projects to the database
        for (project in projects) {
            dbQuery.createProject(
                name = project.name,
                aircraftType = project.aircraftType,
                createdUTC = project.createdUTC,
            )
        }

        val allDbProjects = dbQuery.getAllProjects()

        assertEquals(
            expected = projects.toList(),
            actual = allDbProjects,
            message = "Check if all data was selected"
        )
    }

    @Test
    fun `createProject$connector_shared_commonMain`() {
        val dbQuery = createDatabaseConnection()

        val numberOfProjects = 100

        // Creates `numberOfProjects` amount of Projects
        val projects: MutableList<Project> = MutableList(numberOfProjects) { index -> createProjectData(index + 1)}

        assertEquals(
            expected = numberOfProjects,
            actual = projects.size,
            message = "Number of projects to be inserted into database is expected value"
        )

        // Pushes all projects to the database
        for (project in projects) {
            dbQuery.createProject(
                name = project.name,
                aircraftType = project.aircraftType,
                createdUTC = project.createdUTC,
            )
        }

        val allDbProjects = dbQuery.getAllProjects()
        val countDbProjects = dbQuery.countProjects()

        assertEquals(
            expected = projects.toList(),
            actual = allDbProjects,
            message = "Check if all items on the database are the same as the ones put in"
        )

        assertEquals(
            expected = numberOfProjects.toLong(),
            actual = countDbProjects,
            message = "Checks the count in the database is the same as the number of Projects provided"
        )

    }

    @Test
    fun `countProjects$connector_shared_commonMain`() {
        val dbQuery = createDatabaseConnection()

        val numberOfProjects = 100

        // Creates `numberOfProjects` amount of Projects
        val projects: MutableList<Project> = MutableList(numberOfProjects) { index -> createProjectData(index + 1)}

        // Pushes all projects to the database
        createAllProjectsDb(
            projects = projects.toList(),
            dbQuery = dbQuery,
        )

        val count = dbQuery.countProjects()

        assertEquals(
            expected = numberOfProjects.toLong(),
            actual = count,
            message = "Number of projects in database equal to created projects"
        )
    }

    /**
     * Cleans up the test database file
     */
    @AfterTest
    fun cleanup() {
        // Deletes leftover test SQLite database file
        val db = Paths.get("connector.db")
        db.deleteIfExists()
    }
}
