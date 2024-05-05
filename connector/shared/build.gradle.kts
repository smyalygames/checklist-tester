
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    kotlin("plugin.serialization") version libs.versions.kotlin
    alias(libs.plugins.sqldelight)
}

kotlin {
    jvm()
    jvmToolchain(21)

    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.sqldelight.driver)
            implementation(libs.sqldelight.coroutines.extensions)
            implementation(libs.kotlinx.datetime)
            implementation(libs.slf4j.api)
            implementation(libs.slf4j.reload4j)
            implementation(libs.vdmj)
            implementation(libs.xpc)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.junit.api)
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


