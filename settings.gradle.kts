import de.fayard.refreshVersions.bootstrapRefreshVersions

buildscript {
    repositories { gradlePluginPortal() }
    dependencies.classpath("de.fayard.refreshVersions:refreshVersions:0.9.7")
}

bootstrapRefreshVersions()

gradle.allprojects {
    group = "io.github.topplethenun"

    repositories {
        mavenCentral()
        jcenter {
            content {
                includeModule("org.jetbrains.kotlinx", "kotlinx-html-jvm")
            }
        }
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
        }
        maven {
            url = uri("https://repo.codemc.org/repository/nms")
        }
    }
}

rootProject.name = "exalted"

include(
    "exalted-api",
    "exalted-spigot",
    "exalted-spigot-plugin"
)
