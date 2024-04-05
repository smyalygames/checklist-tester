package io.anthonyberg.connector.vdmj

import com.fujitsu.vdmj.Settings
import com.fujitsu.vdmj.VDMJMain
import com.fujitsu.vdmj.config.Properties
import com.fujitsu.vdmj.plugins.Lifecycle
import com.fujitsu.vdmj.plugins.VDMJ
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.file.Paths
import kotlin.io.path.pathString

/**
 * Handler for the VDM Model
 */
class VDMJ : VDMJMain {
    fun run(expression: String): String {
        Settings.mainClass = VDMJ::class.java
        Properties.init()

        val lifecycle: Lifecycle = createLifecycle(expression)

        // Create a ByteArrayOutputStream to capture the output
        val byteArrayOutputStream = ByteArrayOutputStream()
        val printStream = PrintStream(byteArrayOutputStream)

        val oldOut = System.out

        // Redirect System.out to the PrintStream
        System.setOut(printStream)

        // TODO add exitStatus handling if something went wrong
        var exitStatus = lifecycle.run()

        // Reset System.out to the old output stream
        System.setOut(oldOut)

        // Convert the captured output to a string
        val output = byteArrayOutputStream.toString()

        return output

//        exitProcess(if (lifecycle.run() == ExitStatus.EXIT_OK) 0 else 1)
    }

    /**
     * Creates arguments for VDMJ
     */
    private fun createLifecycle(command: String): Lifecycle {
        val vdmPath = Paths.get( "src/main/resources/checklist.vdmsl")

        // Creates the arguments for VDMJ - i.e. where the file is located
        val vdmArgs = arrayOf(vdmPath.pathString, "-vdmsl", "-e", command, "-q", "-w")

        return Lifecycle(vdmArgs)
    }

}
