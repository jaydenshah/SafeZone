package org.example.safezone;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class SafeZoneCommand implements CommandExecutor {
    private final SafeZone plugin;

    public SafeZoneCommand(SafeZone plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sender.sendMessage(ChatColor.GREEN + "SafeZone Commands");
            sender.sendMessage(ChatColor.WHITE + "/safezone add <name> <radius>" + ChatColor.GREEN + " - Create a new safezone");
            sender.sendMessage(ChatColor.WHITE + "/safezone remove <name>" + ChatColor.GREEN + " - Delete a safezone");
            sender.sendMessage(ChatColor.WHITE + "/safezone list" + ChatColor.GREEN + " - Show all safezones");
            sender.sendMessage(ChatColor.WHITE + "/safezone setradius <name> <radius>" + ChatColor.GREEN + " - Update radius");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "add": addZone(sender, player, args); break;
            case "remove": removeZone(sender, args); break;
            case "list": listZones(sender); break;
            case "setradius": setRadius(sender, args); break;
            default: sender.sendMessage(ChatColor.RED + "Usage: /safezone <add|remove|list|setradius>"); break;
        }

        return true;
    }

    private void addZone(CommandSender sender, Player player, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /safezone add <name> <radius>");
            return;
        }
        String name = args[1];
        int radius;
        try {
            radius = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Radius must be a number!");
            return;
        }
        ConfigurationSection zone = plugin.getConfig().createSection("safezones." + name);
        zone.set("world", player.getWorld().getName());
        Block block = player.getLocation().getBlock();
        zone.set("x", block.getX() + 0.5);
        zone.set("y", block.getY());
        zone.set("z", block.getZ() + 0.5);
        zone.set("radius", radius);
        plugin.saveConfig();
        sender.sendMessage(String.format(ChatColor.GREEN + "Safezone %s was created at %d %d %d with radius %d", name, block.getX(), block.getY(), block.getZ(), radius));
    }

    private void removeZone(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /safezone remove <name>");
            return;
        }
        ConfigurationSection zones = plugin.getConfig().getConfigurationSection("safezones");
        if (!zones.getKeys(false).contains(args[1])) {
            sender.sendMessage(String.format(ChatColor.RED + "Safezone %s doesn't exist!", args[1]));
            return;
        }
        zones.set(args[1], null);
        plugin.saveConfig();
        sender.sendMessage(String.format(ChatColor.GREEN + "Safezone %s was removed", args[1]));
    }

    private void listZones(CommandSender sender) {
        ConfigurationSection zones = plugin.getConfig().getConfigurationSection("safezones");
        if (zones.getKeys(false).isEmpty()) {
            sender.sendMessage(ChatColor.GREEN + "No safezones currently exist");
            return;
        }
        sender.sendMessage(ChatColor.GREEN + "Safezones:");
        for (String key : zones.getKeys(false)) {
            ConfigurationSection zone = zones.getConfigurationSection(key);
            sender.sendMessage(String.format(ChatColor.WHITE + "%s " + ChatColor.GREEN + "(%.1f, %.1f, %.1f), radius: %d, world: %s", key, zone.getDouble("x"), zone.getDouble("y"), zone.getDouble("z"), zone.getInt("radius"), zone.getString("world")));
        }
    }

    private void setRadius(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /safezone setradius <name> <radius>");
            return;
        }
        ConfigurationSection zones = plugin.getConfig().getConfigurationSection("safezones");
        if (!zones.getKeys(false).contains(args[1])) {
            sender.sendMessage(String.format(ChatColor.RED + "Safezone %s doesn't exist!", args[1]));
            return;
        }
        int radius;
        try {
            radius = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Radius must be a number!");
            return;
        }
        ConfigurationSection zone = zones.getConfigurationSection(args[1]);
        zone.set("radius", radius);
        plugin.saveConfig();
        sender.sendMessage(String.format(ChatColor.GREEN + "The radius of safezone %s was updated to %d", args[1], radius));
    }
}
