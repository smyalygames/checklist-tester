package tab.procedure

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import tab.LoadingScreen

class ProceduresContent : Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<ProcedureScreenModel>()
        val state by screenModel.state.collectAsState()

        when (val s = state) {
            is ProcedureState.Loading -> LoadingScreen("Procedures").Content()
            is ProcedureState.Init -> NoProcedures().Content()
            is ProcedureState.Result -> ListProcedures(s.procedures).Content()
        }

        LaunchedEffect(currentCompositeKeyHash) {
            screenModel.procedureExists()
        }
    }
}
