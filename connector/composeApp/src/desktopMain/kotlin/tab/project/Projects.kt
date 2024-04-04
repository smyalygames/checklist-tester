package tab.project

import androidx.compose.runtime.*
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import connector.composeapp.generated.resources.Res
import connector.composeapp.generated.resources.folder_24px
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import tab.LoadingScreen

class Projects : Tab {
    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Projects"
            val icon = painterResource(Res.drawable.folder_24px)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon,
                )
            }
        }

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<ProjectsScreenModel>()
        val state by screenModel.state.collectAsState()

        when (val s = state) {
            is ProjectState.Init -> Navigator(NoProjects())
            is ProjectState.Loading -> Navigator(LoadingScreen("Projects"))
            is ProjectState.Result -> Navigator(ListProjects(s.projects))
        }

        LaunchedEffect(currentCompositeKeyHash) {
            screenModel.projectExists()
        }
    }
}
