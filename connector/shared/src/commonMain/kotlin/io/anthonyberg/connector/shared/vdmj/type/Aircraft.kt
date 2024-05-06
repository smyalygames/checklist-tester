package io.anthonyberg.connector.shared.vdmj.type

/**
 * Aircraft record type in the VDM-SL model
 */
data class Aircraft(
    val items: Map<String, String>, // TODO value should be ItemObject type
    val procedure: MutableList<String> // TODO change String to a ProcedureItem type
) {
    /**
     * Converts variables in object to a String that can be passed into VDM
     *
     * @return VDM record String
     */
    fun toVDMString(): String {
        var output = "mk_Aircraft("
        output += itemsToVDMString()
        output += ","
        output += procedureToVDMString()
        output += ")"

        return output
    }

    /**
     * Creates a String VDM map for items
     *
     * @return String VDM representation of a map
     */
    private fun itemsToVDMString(): String {
        var output = "{"

        for (dref: String in items.keys) {
            val value = items.getValue(dref)

            // TODO make sure to run toString on value once items Map changes the value type
            output += "\"$dref\" |-> $value,"
        }

        // Removes the last comma in the VDM map as it will error otherwise
        output.dropLast(1)

        output += "}"

        return output
    }

    /**
     * Creates a String VDM sequence for the procedure
     *
     * @return String VDM representation of a sequence
     */
    private fun procedureToVDMString(): String {
        var output = "["

        // TODO change item type to Procedure type
        for (item: String in procedure) {
            output += item
            output += ","
        }

        // Removes the last comma in the VDM sequence as it will error otherwise
        output.dropLast(1)

        output += "]"

        return output
    }
}
