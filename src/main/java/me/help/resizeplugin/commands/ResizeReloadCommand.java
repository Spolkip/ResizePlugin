package me.help.resizeplugin.commands;

import me.help.resizeplugin.utils.PlayerAttributesUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ResizeReloadCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public ResizeReloadCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("resize.reload")) {
            sender.sendMessage("§cYou do not have permission to use this command!");
            return true;
        }

        plugin.reloadConfig();
        sender.sendMessage("§aResize configuration reloaded successfully!");

        FileConfiguration config = plugin.getConfig();

        // Reapply attributes for all online players
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            double currentScale = PlayerAttributesUtil.getPlayerScale(player);
            boolean groupUpdated = false;

            // Loop through defined groups to find a matching scale
            if (config.getConfigurationSection("groups") != null) {
                for (String group : config.getConfigurationSection("groups").getKeys(false)) {
                    double groupScale = config.getDouble("groups." + group + ".scale", 1.0);

                    if (currentScale == groupScale) {
                        PlayerAttributesUtil.setPlayerGroup(player, group, config);
                        player.sendMessage("§eYour group attributes have been updated after reload!");
                        groupUpdated = true;
                        break;
                    }
                }
            }

            // If player is not in any predefined group, apply attributes normally
            if (!groupUpdated) {
                PlayerAttributesUtil.applyPlayerAttributes(player, config);
                player.sendMessage("§eYour attributes have been updated after reload!");
            }
        }

        sender.sendMessage("§aAll player attributes have been updated!");
        return true;
    }
}
