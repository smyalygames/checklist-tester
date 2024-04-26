package tab.procedure

import InterfaceState
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.anthonyberg.connector.shared.ActionTransaction
import io.anthonyberg.connector.shared.entity.Action
import kotlinx.coroutines.launch

class ActionsScreenModel (
    private val db: ActionTransaction,
    private val interfaceState: InterfaceState
) : StateScreenModel<ActionsState>(ActionsState.Idle) {
    fun getActions() {
        screenModelScope.launch {
            mutableState.value = ActionsState.Loading

            val procedureId = interfaceState.procedureId ?: return@launch

            val actions = db.getActions(procedureId = procedureId)

            mutableState.value = ActionsState.Result(actions = actions)
        }
    }

    fun loadedActions() {
        mutableState.value = ActionsState.Idle
    }
}

sealed class ActionsState {
    data object Idle : ActionsState()
    data object Loading : ActionsState()
    data class Result(val actions: List<Action>) : ActionsState()
}
