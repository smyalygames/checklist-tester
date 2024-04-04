package di

import io.anthonyberg.connector.shared.ProjectTransaction
import io.anthonyberg.connector.shared.database.DriverFactory
import org.koin.dsl.module
import tab.project.ProjectsScreenModel

fun commonModule() = module {
    single<DriverFactory> {
        DriverFactory()
    }

    single<ProjectTransaction> {
        ProjectTransaction(driverFactory = get<DriverFactory>())
    }

    single<ProjectsScreenModel> {
        ProjectsScreenModel(db = get())
    }
}
