package io.github.topplethenun.exalted.spigot

import io.github.topplethenun.exalted.Exalted
import io.github.topplethenun.exalted.spigot.logging.ExaltedLogger
import io.pixeloutlaw.kindling.Log
import org.bukkit.plugin.Plugin

/**
 * Spigot-specific implementation of the [Exalted] interface.
 */
class ExaltedSpigot(private val plugin: Plugin) : Exalted {
    override fun onEnable() {
        Log.addLogger(ExaltedLogger(plugin.dataFolder, plugin.name.toLowerCase(), Log.Level.DEBUG))
        Log.debug("Enabling Exalted")
    }

    override fun onDisable() {
        Log.debug("Disabling Exalted")
        Log.clearLoggers()
    }
}
