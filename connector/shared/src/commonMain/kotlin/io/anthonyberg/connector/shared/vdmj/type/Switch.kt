package io.anthonyberg.connector.shared.vdmj.type

/**
 * Switch record type in VDM-SL
 *
 * @param position Position of the switch
 * @param middlePosition `true` if there is a middle position for the switch,
 * `false` if the switch only has 2 positions
 */
data class Switch(val position: Int, val middlePosition: Boolean = false) {
    init {
        require((position >= 0) and ((position <= 1) and !middlePosition) or ((position <= 2) and middlePosition))
    }

    /**
     * Converts to String for a VDM representation of an ItemObject record
     *
     * @return String representation for VDM ItemObject
     */
    fun toVDMString(): String {
        return "mk_ItemObject(<SWITCH>, ${toSwitchVDMString()})"
    }

    /**
     * Converts to String for a VDM representation of a Switch record
     *
     * @return String representation for VDM Switch
     */
    private fun toSwitchVDMString(): String {
        return "mk_Switch(${getSwitchState()}, $middlePosition)"
    }

    /**
     * Gets the VDM object for the switch state
     *
     * @return String representation for switch state
     */
    private fun getSwitchState(): String {
        return when (position) {
            0 -> "<OFF>"
            1 -> if (middlePosition) "<MIDDLE>" else "<ON>"
            2 -> "<ON>"
            else -> throw IllegalArgumentException("Position must be between 0 and 2")
        }
    }
}
