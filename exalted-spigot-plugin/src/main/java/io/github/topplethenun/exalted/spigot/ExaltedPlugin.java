package io.github.topplethenun.exalted.spigot;

import me.bristermitten.pdm.PluginDependencyManager;
import me.bristermitten.pdm.SpigotDependencyManager;
import org.bukkit.plugin.java.JavaPlugin;

/** Spigot plugin for Exalted. */
public class ExaltedPlugin extends JavaPlugin {
  @Override
  public void onEnable() {
    PluginDependencyManager pluginDependencyManager = SpigotDependencyManager.of(this);
    pluginDependencyManager
        .loadAllDependencies() // Reads dependencies.json and starts downloading / loading all
        // dependencies async
        .thenRun(
            () -> {
              getLogger().info("All dependencies loaded");
              io.github.topplethenun.exalted.Exalted exalted =
                  new io.github.topplethenun.exalted.spigot.ExaltedSpigot(this);
              exalted.onEnable();
              io.github.topplethenun.exalted.Exalted.setExalted(exalted);
            });
  }

  @Override
  public void onDisable() {
    io.github.topplethenun.exalted.Exalted exalted =
        io.github.topplethenun.exalted.Exalted.getExalted();
    if (exalted != null) {
      exalted.onDisable();
      io.github.topplethenun.exalted.Exalted.setExalted(null);
    }
  }
}
