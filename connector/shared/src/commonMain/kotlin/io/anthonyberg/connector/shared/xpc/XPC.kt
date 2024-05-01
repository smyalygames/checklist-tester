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
}
