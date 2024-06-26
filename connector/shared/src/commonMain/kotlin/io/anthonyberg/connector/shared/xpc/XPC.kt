package io.anthonyberg.connector.shared.xpc

import gov.nasa.xpc.XPlaneConnect
import io.ktor.utils.io.errors.*
import java.net.SocketException

class XPC {

    private lateinit var xpc: XPlaneConnect

    /**
     * Connects to the X-Plane and tests the connection
     *
     * @return `true` if connection successfully established, `false` otherwise
     */
    fun connected(): Boolean {
        try {
            xpc = XPlaneConnect()

            // Ensure connection is established
            xpc.getDREF("sim/test/test_float")
            return true
        } catch (ex: SocketException) {
            return false
        } catch (ex: IOException) {
            return false
        }
    }

    /**
     * Gets the state of all Datarefs
     *
     * @param drefs Array of Datarefs (maximum list size = 255)
     *
     * @return List of all the values for each Dataref
     */
    @Throws(IllegalArgumentException::class)
    fun getStates(drefs: Array<String>): Array<FloatArray> {
        if (drefs.size > 255) {
            throw IllegalArgumentException("The drefs cannot contain more than 255 elements")
        }

        val result = xpc.getDREFs(drefs)

        return result
    }

    /**
     * Gets the state of a specific Dataref
     *
     * @param dref Dataref name to get the value for
     * @return Value of the Dataref
     */
    fun getState(dref: String): FloatArray {
        val result = xpc.getDREF(dref)

        return result
    }

    /**
     * Sets a dataref in X-Plane to the set goal
     *
     * @param dref Dataref name in X-Plane to change the value for
     * @param goal The value that should be set for the dataref in X-Plane
     *
     * @return The state of the Dataref in the simulator
     */
    @Throws(SocketException::class, IOException::class)
    fun runChecklist(dref: String, goal: Int) : FloatArray {
        xpc.sendDREF(dref, goal.toFloat())

        val result = xpc.getDREF(dref)

        return result
    }

    fun sendCOMM(dref: String) {
        xpc.sendCOMM(dref)
    }

    fun getPOSI(): DoubleArray {
        return xpc.getPOSI(0)
    }

    fun setPOSI(posi: DoubleArray) {
        xpc.sendPOSI(posi, 0)
    }
}
