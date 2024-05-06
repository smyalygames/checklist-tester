package io.anthonyberg.connector.shared.vdmj.type

/**
 * Item type to identify what an Item is in an aircraft
 * and therefore used to change the logic for testing the item
 */
enum class ItemType {
    SWITCH,
    KNOB,
    BUTTON,
    THROTTLE;

    /**
     * Returns a VDM Object as a string
     *
     * @return String that is a VDM representation, for example `<SWITCH>`
     */
    fun toVDMString(): String {
        return "<$name>"
    }
}
