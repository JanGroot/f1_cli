import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    application
    kotlin("plugin.serialization") version "1.4.0"
    id("org.mikeneck.graalvm-native-image") version "0.8.0"
}
group = "me.jangroot"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
dependencies {
    implementation("com.github.ajalt:clikt:1.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    implementation("de.vandermeer:asciitable:0.3.2")
    implementation("com.github.kittinunf.fuel:fuel:2.3.0")
    testImplementation(kotlin("test-junit5"))
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}
application {
    mainClassName = "MainKt"
}
nativeImage {
    graalVmHome = "/Users/jg76/.sdkman/candidates/java/20.2.0.r11-grl"
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
