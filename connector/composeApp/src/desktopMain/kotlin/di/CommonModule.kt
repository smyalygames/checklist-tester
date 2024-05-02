package di

import InterfaceState
import io.anthonyberg.connector.shared.ActionTransaction
import io.anthonyberg.connector.shared.ProcedureTransaction
import io.anthonyberg.connector.shared.ProjectTransaction
import io.anthonyberg.connector.shared.TestTransaction
import io.anthonyberg.connector.shared.database.DriverFactory
import org.koin.dsl.module
import tab.procedure.ActionsScreenModel
import tab.procedure.ProcedureScreenModel
import tab.project.ProjectsScreenModel
import tab.test.TestScreenModel

fun commonModule() = module {
    single<DriverFactory> {
        DriverFactory()
    }

    single<ProjectTransaction> {
        ProjectTransaction(driverFactory = get<DriverFactory>())
    }

    single<ProcedureTransaction> {
        ProcedureTransaction(driverFactory = get<DriverFactory>())
    }

    single<ActionTransaction> {
        ActionTransaction(driverFactory = get<DriverFactory>())
    }

    single<TestTransaction> {
        TestTransaction(driverFactory = get<DriverFactory>())
    }
}

fun viewModelModule() = module {
    single<InterfaceState> {
        InterfaceState()
    }

    single<ProjectsScreenModel> {
        ProjectsScreenModel(db = get())
    }

    single<ProcedureScreenModel> {
        ProcedureScreenModel(db = get(), interfaceState = get())
    }

    single<ActionsScreenModel> {
        ActionsScreenModel(db = get(), interfaceState = get())
    }

    single<TestScreenModel> {
        TestScreenModel(db = get(), interfaceState = get())
    }
}
