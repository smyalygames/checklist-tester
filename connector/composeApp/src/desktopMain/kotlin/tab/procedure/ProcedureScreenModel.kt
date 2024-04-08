package tab.procedure

import InterfaceState
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.anthonyberg.connector.shared.ProcedureTransaction
import io.anthonyberg.connector.shared.entity.Procedure
import kotlinx.coroutines.launch

class ProcedureScreenModel (
    private val db: ProcedureTransaction,
    private val interfaceState: InterfaceState
) : StateScreenModel<ProcedureState>(ProcedureState.Loading) {

    fun procedureExists() {
        screenModelScope.launch {
            mutableState.value = ProcedureState.Loading

            val projectId = interfaceState.projectId ?: return@launch

            val exists = db.proceduresExist(projectId)

            if (exists) {
                val procedures = getProcedures(projectId)
                mutableState.value = ProcedureState.Result(procedures = procedures)
            } else {
                mutableState.value = ProcedureState.Init
            }
        }
    }

    private fun getProcedures(projectId: Int): List<Procedure> {
        val procedures = db.getProcedures(projectId)

        return procedures
    }

    fun createProcedure(
        procedureName: String,
        procedureType: String,
        procedureDescription: String
    ) {
        screenModelScope.launch {
            mutableState.value = ProcedureState.Loading

            val projectId = interfaceState.projectId ?: return@launch

            // Add procedure to the database
            db.createProcedure(projectId, procedureName, procedureType, procedureDescription)

            // Load new procedures
            val procedures = getProcedures(projectId)
            mutableState.value = ProcedureState.Result(procedures)
        }

    }
}

sealed class ProcedureState {
    data object Init : ProcedureState()
    data object Loading : ProcedureState()
    data class Result(val procedures: List<Procedure>) : ProcedureState()
}
