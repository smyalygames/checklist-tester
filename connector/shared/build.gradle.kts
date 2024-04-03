
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    kotlin("plugin.serialization").version("1.9.23")
    id("app.cash.sqldelight").version("2.0.1")
}

val coroutinesVersion = "1.7.3"
val ktorVersion = "2.3.7"
val sqlDelightVersion = "2.0.1"
val dateTimeVersion = "0.4.1"

kotlin {
    jvm()
    jvmToolchain(21)

    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            implementation("io.ktor:ktor-client-core:$ktorVersion")
            implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
            implementation("app.cash.sqldelight:sqlite-driver:$sqlDelightVersion")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:$dateTimeVersion")
        }
    }
}

sqldelight {
    databases {
        create("ConnectorDatabase") {
            packageName.set("io.anthonyberg.connector.shared.database")
        }
    }
}


