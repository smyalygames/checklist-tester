package di

import io.anthonyberg.connector.shared.ProcedureTransaction
import io.anthonyberg.connector.shared.ProjectTransaction
import io.anthonyberg.connector.shared.database.DriverFactory
import org.koin.dsl.module
import tab.procedure.ProcedureScreenModel
import tab.project.ProjectsScreenModel

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
}

fun viewModelModule() = module {
    single<ProjectsScreenModel> {
        ProjectsScreenModel(db = get())
    }

    single<ProcedureScreenModel> {
        ProcedureScreenModel(db = get())
    }
}
