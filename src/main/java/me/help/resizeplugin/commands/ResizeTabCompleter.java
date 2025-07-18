package me.help.resizeplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ResizeTabCompleter implements TabCompleter {

    private final JavaPlugin plugin;

    public ResizeTabCompleter(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("resize")) {
            if (args.length == 1) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    completions.add(player.getName());
                }
            }
        }

        if (command.getName().equalsIgnoreCase("resizegroup")) {
            if (args.length == 1) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    completions.add(player.getName());
                }
            } else if (args.length == 2) {
                FileConfiguration config = plugin.getConfig();
                if (config.getConfigurationSection("groups") != null) {
                    completions.addAll(config.getConfigurationSection("groups").getKeys(false));
                }
            }
        }

        return completions;
    }
}
