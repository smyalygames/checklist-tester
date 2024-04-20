rootProject.name = "connector"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven {
            url = uri("https://maven.pkg.github.com/smyalygames/XPlaneConnect")
            credentials {
                username = "smyalygames"
                // Most awful way of storing a token - it's read only anyway
                password = "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"
            }
        }
    }
}

include(":composeApp")
include(":server")
include(":shared")
//include(":lib:xpc:buildSrc")
//include(":lib:xpc")
//project(":lib:xpc:buildSrc").projectDir = file("lib/xpc/buildSrc")
//project(":xpc:xpc").projectDir = file("lib/xpc/xpc")
