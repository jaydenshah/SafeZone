package org.example.safezone;

import org.bukkit.plugin.java.JavaPlugin;

public class SafeZone extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("SafeZone has started");
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new EntityDamageListener(this), this);
        getCommand("safezone").setExecutor(new SafeZoneCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("SafeZone has stopped");
    }
}

