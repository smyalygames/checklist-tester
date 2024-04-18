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
        flatDir {
            dirs("lib")
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
