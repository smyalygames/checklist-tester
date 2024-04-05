package io.anthonyberg.connector.vdmj

import com.fujitsu.vdmj.Settings
import com.fujitsu.vdmj.config.Properties
import com.fujitsu.vdmj.messages.Console
import com.fujitsu.vdmj.messages.ConsolePrintWriter
import com.fujitsu.vdmj.plugins.EventHub
import com.fujitsu.vdmj.plugins.Lifecycle
import com.fujitsu.vdmj.plugins.VDMJ
import io.anthonyberg.connector.models.VDMJExpression
import java.io.ByteArrayOutputStream
import java.nio.file.Paths
import kotlin.io.path.pathString

/**
 * Handler for the VDM Model
 */
fun vdmjExecute(expression: String): VDMJExpression {
    Settings.mainClass = VDMJ::class.java
    Properties.init()

    val lifecycle: Lifecycle = createLifecycle(expression)

    // Create a ByteArrayOutputStream to capture the output
    val byteArrayOutputStream = ByteArrayOutputStream()
    val printStream = ConsolePrintWriter(byteArrayOutputStream)

    // Save the old PrintStreams
    val oldOut = Console.out
    val oldErr = Console.err

    // Redirect Console's PrintStreams to the new PrintStream
    Console.out = printStream
    Console.err = printStream

    // TODO check if there is actually a memory leak or if it is just Java
    val exitStatus = lifecycle.run()

    // Reset Console's PrintStreams to the old PrintStreams
    Console.out = oldOut
    Console.err = oldErr

    // Convert the captured output to a string
    val console = byteArrayOutputStream.toString()

    // Reset the ByteArrayOutputStream
    byteArrayOutputStream.reset()

    // Resets VDMJ's EventHub after closing lifecycle
    EventHub.reset()

    val output = VDMJExpression(output = console, exitStatus = exitStatus)

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
