plugins {
    `java-library`
    id("io.pixeloutlaw.gradle.buildconfigkt")
    id("com.github.johnrengelman.shadow")
    id("net.minecrell.plugin-yml.bukkit")
    id("me.bristermitten.pdm")
}

description = "Spigot plugin for next generation of ARPG drops for Minecraft"

dependencies {
    compileOnly("org.spigotmc:spigot-api:_")

    implementation(project(":exalted-spigot"))

    testImplementation("com.github.seeseemelk:MockBukkit-v1.16:_")
}

buildConfigKt {
    appName = "Exalted"
}

bukkit {
    main = "${project.group}.exalted.spigot.ExaltedPlugin"
    authors = listOf("Richard Harrah")
    load = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.PluginLoadOrder.STARTUP
    apiVersion = "1.16"

    description = "Next generation of ARPG drops for Minecraft"
}

tasks {
    assemble {
        dependsOn("shadowJar")
    }
    jar {
        dependsOn("pdm")
    }
    shadowJar {
        archiveBaseName.set(project.name)
        archiveClassifier.set("")
        archiveVersion.set("")
        dependencies {
            exclude(dependency("org.jetbrains.kotlin::"))
        }
        mergeServiceFiles()
        minimize()
    }
}

class Repositories(var )

class PdmJsonTransformer : com.github.jengelman.gradle.plugins.shadow.transformers.Transformer {
    @get:org.gradle.api.tasks.Input
    var resource: String? = null

    var jsonNode: com.google.gson.JsonObject? = null

    override fun canTransformResource(element: FileTreeElement): Boolean {
        val path = element.relativePath.pathString
        val resource = this.resource
        if (resource != null && resource.equals(path, ignoreCase = true)) {
            return true
        }
        return false
    }

    override fun transform(context: com.github.jengelman.gradle.plugins.shadow.transformers.TransformerContext) {
        val inMemoryJsonNode = objectMapper.readTree(context.`is`)
        val currentJsonNode = jsonNode
        if (currentJsonNode == null) {
            jsonNode = inMemoryJsonNode
            return
        }
        val mergedNode = com.fasterxml.jackson.databind.node.ObjectNode()
    }

    override fun hasTransformedResource(): Boolean = jsonNode != null

    override fun modifyOutputStream(zip: shadow.org.apache.tools.zip.ZipOutputStream, preserveFileTimestamps: Boolean) {

    }
}
