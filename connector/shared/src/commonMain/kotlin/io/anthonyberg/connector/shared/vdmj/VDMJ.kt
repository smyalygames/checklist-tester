package io.anthonyberg.connector.shared.vdmj

import com.fujitsu.vdmj.ExitStatus
import com.fujitsu.vdmj.Settings
import com.fujitsu.vdmj.config.Properties
import com.fujitsu.vdmj.messages.Console
import com.fujitsu.vdmj.messages.ConsolePrintWriter
import com.fujitsu.vdmj.plugins.EventHub
import com.fujitsu.vdmj.plugins.Lifecycle
import com.fujitsu.vdmj.plugins.VDMJ
import io.anthonyberg.connector.shared.entity.VDMJExpression
import kotlinx.coroutines.*
import java.io.*
import java.nio.file.Paths
import kotlin.io.path.pathString

@OptIn(DelicateCoroutinesApi::class)
class VDMJ {
    // Create a ByteArrayOutputStream to capture the output
    private val byteArrayOutputStream = ByteArrayOutputStream()
    private val printStream = ConsolePrintWriter(byteArrayOutputStream)

    private val inputStream = PipedInputStream()
    private val inputOutput = PipedOutputStream(inputStream)
    private val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    private val writer = BufferedWriter(OutputStreamWriter(inputOutput))

    // Save the old PrintStreams
    private val oldOut = Console.out
    private val oldErr = Console.err
    private val oldIn = Console.`in`

    private lateinit var exitStatus: ExitStatus

    /**
     * Starts VDMJ interpreter
     */
    init {
        Settings.mainClass = VDMJ::class.java
        Properties.init()

        // Redirect Console's PrintStreams to the new PrintStream
        Console.out = printStream
        Console.err = printStream
        Console.`in` = bufferedReader

        val lifecycle: Lifecycle = createLifecycle()

        // Start a coroutine for VDMJ to run in
        GlobalScope.launch {
            exitStatus = lifecycle.run()
        }
    }


    /**
     * Creates VDMJ Lifecycle with interpreter mode
     */
    private fun createLifecycle(): Lifecycle {
        val vdmPath = Paths.get("src/main/resources/checklist.vdmsl")

        // Creates the arguments for VDMJ - i.e. where the file is located
        val vdmArgs = arrayOf(vdmPath.pathString, "-vdmsl", "-i", "-q")

        return Lifecycle(vdmArgs)
    }

    fun cleanup() {
        // Reset Console's PrintStreams to the old PrintStreams
        Console.out = oldOut
        Console.err = oldErr
        Console.`in` = oldIn

        // Reset the ByteArrayOutputStream
        byteArrayOutputStream.reset()

        // Resets VDMJ's EventHub after closing lifecycle
        EventHub.reset()
    }

    /**
     * Runs VDM command
     */
    suspend fun run(command: String): VDMJExpression {
        // Clean previous console outputs
        byteArrayOutputStream.reset()

        // Run commands
        withContext(Dispatchers.IO) {
            writer.write(command)
            writer.write(System.lineSeparator())
            writer.flush()
        }

        var output = byteArrayOutputStream.toString()

        while(!this::exitStatus.isInitialized and output.isEmpty()) {
            delay(10)

            // Convert the captured output to a string
            output = byteArrayOutputStream.toString()
        }

        // Clear console output again, just to be kind
        byteArrayOutputStream.reset()

        return VDMJExpression(output = output)
    }
}
