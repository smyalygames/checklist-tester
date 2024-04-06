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
}

sealed class ProcedureState {
    data object Init : ProcedureState()
    data object Loading : ProcedureState()
    data class Result(val procedures: List<Procedure>) : ProcedureState()
}
