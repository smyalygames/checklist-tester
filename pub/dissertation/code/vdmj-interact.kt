package io.anthonyberg.connector.shared.vdmj

import com.fujitsu.vdmj.messages.Console
import com.fujitsu.vdmj.messages.ConsolePrintWriter
import com.fujitsu.vdmj.plugins.VDMJ
import java.io.*

object VDMJ {
    // Create a ByteArrayOutputStream to capture the output
    private val byteArrayOutputStream = ByteArrayOutputStream()
    private val printStream = ConsolePrintWriter(byteArrayOutputStream)

    // Input handlers
    private val inputStream = PipedInputStream()
    private val inputOutput = PipedOutputStream(inputStream)
    private val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    private val writer = BufferedWriter(OutputStreamWriter(inputOutput))

    // Save the old PrintStreams
    private val oldOut = Console.out
    private val oldErr = Console.err
    private val oldIn = Console.`in`


    init {
        //VDMJ Initialization

        // Redirect Console's PrintStreams to the new PrintStream
        Console.out = printStream
        Console.err = printStream
        Console.`in` = bufferedReader

        // Rest of VDMJ setup
    }

    suspend fun run(command: String): String {
        // Clean previous console outputs
        byteArrayOutputStream.reset()

        // Run commands
        withContext(Dispatchers.IO) {
            writer.write(command)
            writer.write(System.lineSeparator())
            writer.flush()
        }

        // Convert the captured output to a string
        var output = byteArrayOutputStream.toString()

        // Clear console output again
        byteArrayOutputStream.reset()

        return VDMJExpression(output = output)
    }
}
