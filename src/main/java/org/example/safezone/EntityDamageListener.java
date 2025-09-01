package org.example.safezone;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageListener implements Listener {
    private final SafeZone plugin;

    public EntityDamageListener(SafeZone plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Location loc = entity.getLocation();
        ConfigurationSection zones = plugin.getConfig().getConfigurationSection("safezones");
        if (zones != null) {
            for (String key : zones.getKeys(false)) {
                ConfigurationSection zone = zones.getConfigurationSection(key);
                String world = zone.getString("world");
                if (entity.getWorld().getName().equals(world)) {
                    double x = zone.getDouble("x");
                    double y = zone.getDouble("y");
                    double z = zone.getDouble("z");
                    int radius = zone.getInt("radius");
                    if (Math.abs(loc.getX() - x) <= radius
                    && Math.abs(loc.getY() - y) <= radius
                    && Math.abs(loc.getZ() - z) <= radius) {
                        event.setCancelled(true);
                        if (event.getDamager() instanceof Player) {
                            Player damager = (Player) event.getDamager();
                            damager.sendMessage(ChatColor.RED + "You can't attack something within the safe zone!");
                        }
                    }

                }
            }
        }
    }
}
