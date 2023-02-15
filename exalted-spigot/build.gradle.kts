plugins {
    kotlin("jvm")
    id("io.pixeloutlaw.gradle.buildconfigkt")
    id("me.bristermitten.pdm")
}

description = "Spigot-specific implementation of exalted-api"

dependencies {
    // exposed as part of method signatures
    compileOnly("org.spigotmc:spigot-api:_")

    // exposed as part of method signatures
    api(project(":exalted-api"))

    // not exposed as part of method signatures but still used
    pdm("org.jetbrains.kotlin:kotlin-stdlib-jdk8:_")
    pdm("io.pixeloutlaw:kindling:_")

    testImplementation("com.github.seeseemelk:MockBukkit-v1.16:_")
}

pdm {
    disableRuntimeBundling()
}

tasks {
    jar {
        dependsOn("pdm")
    }
}