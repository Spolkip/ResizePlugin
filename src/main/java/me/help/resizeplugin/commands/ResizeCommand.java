package me.help.resizeplugin.commands;

import me.help.atlascoreresize.utils.PlayerAttributesUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ResizeCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public ResizeCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1 || args.length > 2) {
            sender.sendMessage("§cUsage: /resize <playername> <scale>");
            return true;
        }

        Player target;
        double scale;

        if (args.length == 1) { // Self resize
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cOnly players can use this command for themselves!");
                return true;
            }

            target = (Player) sender;

            if (!target.hasPermission("resize.use")) {
                target.sendMessage("§cYou do not have permission to use this command!");
                return true;
            }

            try {
                scale = Double.parseDouble(args[0]);
            } catch (NumberFormatException e) {
                target.sendMessage("§cInvalid scale value. Please enter a number.");
                return true;
            }

        } else { // Resize another player
            if (!sender.hasPermission("resize.others")) {
                sender.sendMessage("§cYou do not have permission to resize other players!");
                return true;
            }

            target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage("§cPlayer not found!");
                return true;
            }

            try {
                scale = Double.parseDouble(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage("§cInvalid scale value. Please enter a number.");
                return true;
            }
        }

        FileConfiguration config = plugin.getConfig();
        if (PlayerAttributesUtil.isWorldDisabled(target, config)) {
            sender.sendMessage("§cResizing is disabled in this world.");
            return true;
        }

        double minScale = config.getDouble("settings.min-scale", 0.1);
        double maxScale = config.getDouble("settings.max-scale", 10.0);

        if (scale < minScale || scale > maxScale) {
            sender.sendMessage("§cScale must be between " + minScale + " and " + maxScale + ".");
            return true;
        }

        PlayerAttributesUtil.setPlayerScale(target, scale);
        PlayerAttributesUtil.applyPlayerAttributes(target, config);

        target.sendMessage("§aYour scale has been set to " + scale + "!");
        sender.sendMessage("§aSuccessfully set " + target.getName() + "'s scale to " + scale + "!");

        return true;
    }
}