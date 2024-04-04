package di

import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import kotlin.test.Test

class CheckKoinModulesTest : KoinTest {

    /**
     * Launches and runs all modules.
     *
     * See [Koin Docs](https://insert-koin.io/docs/reference/koin-test/checkmodules) on more information
     */
    @Test
    fun verifyKoinApp() {
        koinApplication {
            modules(
                commonModule(),
                viewModelModule()
            )
            checkModules()
        }
    }
}
