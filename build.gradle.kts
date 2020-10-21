import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    application
    id("org.mikeneck.graalvm-native-image") version "0.8.0"
}
group = "me.jangroot"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
dependencies {
    implementation("com.github.ajalt:clikt:1.7.0")
    implementation("com.github.kittinunf.fuel:fuel-gson:2.3.0")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("de.vandermeer:asciitable:0.3.2")
    testImplementation(kotlin("test-junit5"))
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}
application {
    mainClassName = "MainKt"
}
nativeImage {
    graalVmHome = System.getenv("GRAAL_HOME")
    mainClass ="MainKt"
    executableName = "f1"
    outputDirectory = file("$buildDir/executable")
    arguments(
        "--no-fallback",
        "--enable-all-security-services",
        "--enable-http",
        "--initialize-at-run-time=com.example.runtime",
        "--report-unsupported-elements-at-runtime"
    )
}
