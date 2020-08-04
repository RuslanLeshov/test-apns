plugins {
    kotlin("jvm") version "1.4-M3"
}

group = "org.example"
version = "1.0-SNAPSHOT"

allprojects{
    repositories {
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://kotlin.bintray.com/kotlinx")
        mavenCentral()
    }
}

