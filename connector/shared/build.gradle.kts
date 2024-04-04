
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    kotlin("plugin.serialization") version "1.9.23"
    id("app.cash.sqldelight") version "2.0.1"
}

val coroutinesVersion = "1.8.0"
val ktorVersion = "2.3.9"
val sqlDelightVersion = "2.0.1"
val dateTimeVersion = "0.5.0"
val sl4jVersion = "2.0.12"
val jupyterVersion = "5.10.1"

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
            implementation("app.cash.sqldelight:coroutines-extensions:$sqlDelightVersion")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:$dateTimeVersion")
            implementation("org.slf4j:slf4j-api:$sl4jVersion")
            implementation("org.slf4j:slf4j-reload4j:2.0.12")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation("org.junit.jupiter:junit-jupiter:$jupyterVersion")
        }
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("io.anthonyberg.connector.shared.database")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/databases"))
        }
    }
}


