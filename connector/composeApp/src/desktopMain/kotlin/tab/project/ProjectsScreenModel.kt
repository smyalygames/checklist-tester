package tab.project

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.anthonyberg.connector.shared.ProjectTransaction
import io.anthonyberg.connector.shared.entity.Project
import kotlinx.coroutines.launch

class ProjectsScreenModel (
    private val db: ProjectTransaction
) : StateScreenModel<ProjectState>(ProjectState.Loading) {

    fun projectExists() {
        screenModelScope.launch {
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
            println(projects)

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
