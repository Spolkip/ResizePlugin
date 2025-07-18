package me.help.resizeplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ResizeHelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.GOLD + "╔════════[ " + ChatColor.YELLOW + "AtlasCoreResize Help" + ChatColor.GOLD + " ]════════╗");
        sender.sendMessage(ChatColor.DARK_AQUA + "┃ " + ChatColor.YELLOW + "/resize <player> <scale> " + ChatColor.GRAY + "➜ Change size of yourself or another player.");
        sender.sendMessage(ChatColor.DARK_AQUA + "┃ " + ChatColor.YELLOW + "/resizegroup <player> <group> " + ChatColor.GRAY + "➜ Assign a group size to yourself or others.");
        sender.sendMessage(ChatColor.DARK_AQUA + "┃ " + ChatColor.YELLOW + "/resizereload " + ChatColor.GRAY + "➜ Reloads the plugin configuration.");
        sender.sendMessage(ChatColor.DARK_AQUA + "┃ " + ChatColor.YELLOW + "/resizehelp " + ChatColor.GRAY + "➜ Show this help menu.");
        sender.sendMessage(ChatColor.GOLD + "╚══════════════════════════════╝");
        return true;
    }
}
