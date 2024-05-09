package tab.test

import InterfaceState
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.anthonyberg.connector.shared.TestTransaction
import io.anthonyberg.connector.shared.entity.Action
import io.anthonyberg.connector.shared.vdmj.VDMJTransaction
import io.anthonyberg.connector.shared.xpc.XPC
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestScreenModel (
    private val db: TestTransaction,
    private val interfaceState: InterfaceState
) : StateScreenModel<TestState>(TestState.Init) {
    private val xpc = XPC()
    private val logger = KotlinLogging.logger {}

    /**
     * Sets up necessary connections
     */
    fun init(actions: List<Action>) {
        screenModelScope.launch {
            mutableState.value = TestState.Init

            val procedureId = interfaceState.procedureId

            // Checks if procedureId is set
            if (procedureId == null) {
                mutableState.value = TestState.Error
                return@launch
            }

            // Checks if the simulator is running
            val simConnection = xpc.connected()
            interfaceState.simConnection.value = simConnection

            if (!simConnection) {
                mutableState.value = TestState.NoSimulator
                return@launch
            }

            val altitude = interfaceState.altitude
            if (altitude != null) {
                val posi = DoubleArray(7) { -998.0 }
                posi[2] = altitude.toDouble()
                posi[6] = 0.0
                xpc.setPOSI(posi)
                delay(60000L)
            }


//            val vdm = VDMJTransaction(actions = actions, xpc = xpc)
//            val expected = vdm.expectedEndState()
//
//            logger.debug { expected }

            // Starts the test in the database
            interfaceState.testId = db.startTest(procedureId)

            mutableState.value = TestState.Ready
        }
    }

    /**
     * Run an action in the simulator
     *
     * @param action Action to be run in the simulator
     */
    fun runAction(action: Action) {
        screenModelScope.launch {
            mutableState.value = TestState.Running(step = action.step)

            val testId = interfaceState.testId

            if (testId == null) {
                mutableState.value = TestState.Error
                return@launch
            }

            // Checks if the simulator is still running
            val simConnection = xpc.connected()
            interfaceState.simConnection.value = simConnection

            if (!simConnection) {
                mutableState.value = TestState.Error
                return@launch
            }

            val goal = action.goal.toInt()
            val isCommand = goal == -998

            // Prerequisite before testing the action
            val initDref = if (isCommand) goal else xpc.getState(action.type)[0]
            val actionTestId = db.startAction(
                testId = testId,
                actionId = action.id,
                initState = initDref.toString()
            )

            delay(8000L)

            // Running the action in the simulator
            // TODO deal with action.goal being a String in the database
            val result: Float = if (!isCommand) {
                xpc.runChecklist(action.type, goal)[0]
            } else {
                xpc.sendCOMM(action.type)
                goal.toFloat()
            }

            // Saving result to the database
            db.finishAction(
                id = actionTestId,
                endState = result.toString()
            )

            mutableState.value = TestState.Complete(
                step = action.step,
                pass = goal.toFloat() == result
            )
        }
    }

    /**
     * To be run after running all the actions in the simulator
     */
    fun end() {
        screenModelScope.launch {
            val testId = interfaceState.testId

            if (testId == null) {
                mutableState.value = TestState.Error
                return@launch
            }

            // Completes the test on the database
            db.endTest(testId)

            mutableState.value = TestState.Idle
        }
    }
}

sealed class TestState {
    data object Init : TestState()
    data object NoSimulator : TestState()
    data object Error : TestState()
    data object Ready : TestState()
    data class Running(val step: Int) : TestState()
    data class Complete(val step: Int, val pass: Boolean) : TestState()
    data object Idle : TestState()
}
