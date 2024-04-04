package tab.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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

        when (state) {
            is ProjectsScreenModel.State.UnInit -> screenModel.projectExists()
            is ProjectsScreenModel.State.Init -> Navigator(NoProjects())
            is ProjectsScreenModel.State.Loading -> Navigator(LoadingScreen("Projects"))
            is ProjectsScreenModel.State.Result -> Navigator(ListProjects())
        }
    }
}
