package tab.test

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import tab.LoadingScreen
import tab.procedure.ActionsScreenModel
import tab.procedure.ActionsState

class TestContent : Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<ActionsScreenModel>()
        val state by screenModel.state.collectAsState()

        when (val s = state) {
            is ActionsState.Loading -> LoadingScreen("Simulator Test").Content()
            is ActionsState.Idle -> { /* TODO implement error? */ }
            is ActionsState.Result -> TestRun(s.actions).Content()
        }

        LaunchedEffect(currentCompositeKeyHash) {
            screenModel.getActions()
        }
    }
}
