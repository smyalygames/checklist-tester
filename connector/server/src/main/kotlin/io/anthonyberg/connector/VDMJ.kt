package io.anthonyberg.connector

import com.fujitsu.vdmj.ExitStatus
import com.fujitsu.vdmj.Settings
import com.fujitsu.vdmj.config.Properties
import com.fujitsu.vdmj.plugins.Lifecycle
import com.fujitsu.vdmj.plugins.VDMJ
import kotlin.system.exitProcess

/**
 * Handler for the VDM Model
 */
class VDMJ {
    init {
        Settings.mainClass = VDMJ::class.java
        Properties.init()

        val lifecycle: Lifecycle = createLifecycle()

        exitProcess(if (lifecycle.run() == ExitStatus.EXIT_OK) 0 else 1)
    }

    /**
     * Creates arguments for VDMJ
     */
    private fun createLifecycle(): Lifecycle {
        // Creates the arguments for VDMJ - i.e. where the file is located
        val args: Array<String> = arrayOf("resources/checklist.vdmsl")

        return Lifecycle(args)
    }

}
