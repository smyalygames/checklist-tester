plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
}

val sl4jVersion = "2.0.12"
val vdmjVersion = "4.5.0"

group = "io.anthonyberg.connector"
version = "1.0.0"
application {
    mainClass.set("io.anthonyberg.connector.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)

    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")

    implementation("dk.au.ece.vdmj:vdmj:$vdmjVersion")
}
