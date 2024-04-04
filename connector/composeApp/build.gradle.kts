import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)

    alias(libs.plugins.jetbrainsCompose)
}

val material3Version = "1.6.1"
val voyagerVersion = "1.0.0"
val kotlinxVersion = "1.8.0"
val koinVersion = "3.5.3"
val kodeinVersion = "7.21.2"

kotlin {
    jvm("desktop")
    jvmToolchain(21)

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(projects.shared)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation("org.jetbrains.compose.material3:material3-desktop:$material3Version")

            // Koin
            implementation(project.dependencies.platform("io.insert-koin:koin-bom:$koinVersion"))
            implementation("io.insert-koin:koin-core:$koinVersion")
            implementation("io.insert-koin:koin-compose:1.1.2")

            // Voyager - Navigation
            // Multiplatform

            // Navigator
            implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")

            // Screen Model
            implementation("cafe.adriel.voyager:voyager-screenmodel:$voyagerVersion")

            // BottomSheetNavigator
            implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")

            // TabNavigator
            implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")

            // Transitions
            implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")

            // Koin integration
            implementation("cafe.adriel.voyager:voyager-koin:$voyagerVersion")

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:$kotlinxVersion")
        }
    }
}


compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "io.anthonyberg.connector"
            packageVersion = "1.0.0"
        }
    }
}
