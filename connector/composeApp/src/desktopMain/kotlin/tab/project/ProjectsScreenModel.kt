package tab.project

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.anthonyberg.connector.shared.ProjectTransaction
import io.anthonyberg.connector.shared.entity.Project
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProjectsScreenModel (
    private val db: ProjectTransaction
) : StateScreenModel<ProjectsScreenModel.State>(State.UnInit) {

    override fun onDispose() {
        println("Project Disposed")
    }

    sealed class State {
        object UnInit : State()
        object Init : State()
        object Loading : State()
        data class Result(val projects: List<Project>) : State()
    }

    fun projectExists() {
        screenModelScope.launch {
            mutableState.value = State.Loading
            delay(5000)
            val exists = db.projectExists()

            if (exists) {
                getProjects()
            } else {
                mutableState.value = State.Init
            }
        }
    }

    fun getProjects() {
        mutableState.value = State.Loading
        val projects = db.getProjects()

        mutableState.value = State.Result(projects)
    }

}
