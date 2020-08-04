plugins {
    kotlin("jvm")
}

dependencies {
    api(kotlin("stdlib-jdk8"))
    implementation("com.eatthepath:pushy:0.14.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.3.8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
    api("org.slf4j:slf4j-api:1.7.30")
    implementation(kotlin("script-runtime"))
}


tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

sourceSets {
    main {
        resources {
            srcDir("src/main/resources")
        }
    }
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "ApplicationKt"
    }
    from(configurations.runtime.get().map { if(it.isDirectory) it else zipTree(it) })
}