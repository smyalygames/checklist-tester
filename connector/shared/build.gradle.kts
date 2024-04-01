
plugins {
    alias(libs.plugins.kotlinMultiplatform)

}

kotlin {
    jvm()
    jvmToolchain(21)

    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
        }
    }
}

