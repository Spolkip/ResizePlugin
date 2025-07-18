package me.help.resizeplugin.commands;

import me.help.resizeplugin.ResizePlugin;
import me.help.resizeplugin.utils.PlayerAttributesUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ResizeGroupCommand implements CommandExecutor {

    private final ResizePlugin plugin;

    public ResizeGroupCommand(ResizePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1 || args.length > 2) {
            sender.sendMessage("§cUsage: /resizegroup <playername> <group>");
            return true;
        }

        Player target;
        String group;

        if (args.length == 1) { // Self group assignment
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cOnly players can use this command for themselves!");
                return true;
            }

            target = (Player) sender;

            if (!target.hasPermission("resize.group")) {
                target.sendMessage("§cYou do not have permission to use this command!");
                return true;
            }

            group = args[0].toLowerCase();

        } else { // Assign group to another player
            if (!sender.hasPermission("resize.others")) {
                sender.sendMessage("§cYou do not have permission to assign groups to other players!");
                return true;
            }

            target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage("§cPlayer not found!");
                return true;
            }

            group = args[1].toLowerCase();
        }

        FileConfiguration config = plugin.getConfig();
        if (PlayerAttributesUtil.isWorldDisabled(target, config)) {
            sender.sendMessage("§cResizing is disabled in this world.");
            return true;
        }

        if (!config.contains("groups." + group)) {
            sender.sendMessage("§cInvalid group! §eAvailable groups: " + String.join(", ", config.getConfigurationSection("groups").getKeys(false)));
            return true;
        }

        PlayerAttributesUtil.setPlayerGroup(target, group, config);
        plugin.getPlayerGroups().put(target.getUniqueId(), group); // This now works correctly
        target.sendMessage("§aYou have joined the " + group + " group!");
        sender.sendMessage("§aSuccessfully set " + target.getName() + " to the " + group + " group!");

        return true;
    }
}