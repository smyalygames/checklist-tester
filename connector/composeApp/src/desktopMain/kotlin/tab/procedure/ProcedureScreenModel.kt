package tab.procedure

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.anthonyberg.connector.shared.ProcedureTransaction
import io.anthonyberg.connector.shared.entity.Procedure
import kotlinx.coroutines.launch

class ProcedureScreenModel (
    private val db: ProcedureTransaction
) : StateScreenModel<ProcedureState>(ProcedureState.Loading) {

    fun procedureExists() {
        screenModelScope.launch {
            val exists = db.proceduresExist()

            if (exists) {
                val procedures = getProcedures()
                mutableState.value = ProcedureState.Result(procedures = procedures)
            } else {
                mutableState.value = ProcedureState.Init
            }
        }
    }

    private fun getProcedures(): List<Procedure> {
        val procedures = db.getProcedures()

        return procedures
    }

    fun createProcedure(
        procedureName: String,
        procedureType: String,
        procedureDescription: String
    ) {
        screenModelScope.launch {
            mutableState.value = ProcedureState.Loading

            // Add procedure to the database
            db.createProcedure(procedureName, procedureType, procedureDescription)

            // Load new procedures
            val procedures = getProcedures()
            mutableState.value = ProcedureState.Result(procedures)
        }

    }
}

sealed class ProcedureState {
    data object Init : ProcedureState()
    data object Loading : ProcedureState()
    data class Result(val procedures: List<Procedure>) : ProcedureState()
}
