package tab.project

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import tab.LoadingScreen

class ProjectsContent : Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<ProjectsScreenModel>()
        val state by screenModel.state.collectAsState()

        when (val s = state) {
            is ProjectState.Init -> NoProjects().Content()
            is ProjectState.Loading -> LoadingScreen("Projects").Content()
            is ProjectState.Result -> ListProjects(s.projects).Content()
        }


        LaunchedEffect(currentCompositeKeyHash) {
            screenModel.projectExists()
        }
    }
}

