plugins {
    kotlin("jvm") version "1.9.23"
}

group = "io.anthonyberg"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("dk.au.ece.vdmj:vdmj:4.5.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
