package tab.project

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.anthonyberg.connector.shared.ProjectTransaction
import io.anthonyberg.connector.shared.entity.Project
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.launch

class ProjectsScreenModel (
    private val db: ProjectTransaction
) : StateScreenModel<ProjectState>(ProjectState.Loading) {

    private val logger = KotlinLogging.logger {}

    fun projectExists() {
        screenModelScope.launch {
            mutableState.value = ProjectState.Loading

            val exists = db.projectExists()

            if (exists) {
                val projects = getProjects()
                mutableState.value = ProjectState.Result(projects = projects)
            } else {
                mutableState.value = ProjectState.Init
            }
        }
    }

    private fun getProjects(): List<Project> {
        val projects = db.getProjects()
        logger.debug { "Loaded projects from database: $projects" }

        return projects
    }

    fun createProject(projectName : String, aircraftType : String) {
        screenModelScope.launch {
            mutableState.value = ProjectState.Loading

            // Add project to database
            db.createProject(projectName = projectName, aircraftType = aircraftType)

            // Load new projects
            val projects = getProjects()
            mutableState.value = ProjectState.Result(projects = projects)
        }
    }
}

sealed class ProjectState {
    data object Init : ProjectState()
    data object Loading : ProjectState()
    data class Result(val projects: List<Project>) : ProjectState()
}
