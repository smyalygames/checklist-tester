package io.anthonyberg.connector.shared.vdmj

import io.anthonyberg.connector.shared.entity.Action
import io.anthonyberg.connector.shared.vdmj.type.*
import io.anthonyberg.connector.shared.xpc.XPC
import io.ktor.utils.io.errors.*

/**
 * VDMJ transactions used to control the aircraft in the simulator
 * and run steps
 */
class VDMJTransaction(val actions: List<Action>, private val xpc: XPC) {
    private val vdmj = VDMJ

    private val drefs: Array<String> = actions.map { it.type }.toTypedArray()

    private var aircraft: Aircraft
    private var step = 1

    init {
        // Check X-Plane is running
        if (!xpc.connected()) {
            throw IOException("Could not connect to X-Plane")
        }

        // Create Aircraft
        val items = getAircraftState()

        val procedures: MutableList<ProcedureItem> = actions.map {
            ProcedureItem(
                dref = it.type,
                type = ItemType.SWITCH,
                goal = it.goal.toInt(),
                complete = false
            )
        }.toMutableList()

        aircraft = Aircraft(items = items, procedure = procedures)
    }

    suspend fun expectedEndState(): Aircraft {
        val command = "p complete_procedure(${aircraft.toVDMString()})"

        println(command)

        val result = vdmj.run(command = command)

        return parseAircraftString(result.output)
    }

//    fun nextStep() {
//        val command = "do_proc_item("
//    }

    /**
     * Gets the state of all the DREFs in X-Plane for [Aircraft.items]
     *
     * @return Map with key that is a DREF and value [ItemObject]
     */
    private fun getAircraftState(): Map<String, ItemObject> {
        val initDrefs = xpc.getStates(drefs = drefs)

        val items: Map<String, ItemObject> = actions.associateBy(
            { it.type },
            { ItemObject(
                type = ItemType.SWITCH,
                item = Switch(
                    position = initDrefs[it.step][0].toInt(),
                    middlePosition = it.goal.toInt() > 1
                )
            )}
        )

        return items
    }

    private fun parseAircraftString(aircraftString: String): Aircraft {
        val itemsString = aircraftString.substringAfter("mk_Aircraft({").substringBefore("}, [")
        val proceduresString = aircraftString.substringAfter("}, [").substringBefore("])")

        val items = itemsString.split("), ").associate { itemString ->
            val key = itemString.substringBefore(" |-> ")
            val switch = itemString.substringAfter("mk_Switch(").substringBefore(")")
            val valueString = switch.substringAfter("<").substringBefore(">, ")
            val middlePosition = switch.takeLast(4) == "true"
            val position = when (valueString) {
                "OFF" -> 0
                "MIDDLE" -> 1
                "ON" -> if (middlePosition) 2 else 1
                else -> throw IOException("Switch has incorrect switch type object")
            }
            key to ItemObject(ItemType.SWITCH, Switch(position, middlePosition))
        }

        val procedures = proceduresString.split("), ").map { procedureString ->
            val parts = procedureString.removePrefix("mk_ChecklistItem(").removeSuffix(")").split(", ")
            val dref = parts[0].removeSurrounding("\"")
            val type = ItemType.SWITCH
            val goal = when (parts[2]) {
                "<OFF>" -> 0
                "<MIDDLE>" -> 1
                "<ON>" -> if (aircraft.items[dref]?.item?.middlePosition == true) 2 else 1
                else -> throw IOException("Procedure has incorrect switch type object")
            }
            val complete = parts[3] == "true"
            ProcedureItem(dref, type, goal, complete)
        }.toMutableList()

        return Aircraft(items, procedures)
    }
}
