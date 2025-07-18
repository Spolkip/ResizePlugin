package me.help.resizeplugin;

import me.help.resizeplugin.commands.*;
import me.help.resizeplugin.listeners.PlayerChangedWorldListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ResizePlugin extends JavaPlugin {

    private final Map<UUID, String> playerGroups = new HashMap<>();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        // Register commands
        this.getCommand("resize").setExecutor(new ResizeCommand(this));
        this.getCommand("resizegroup").setExecutor(new ResizeGroupCommand(this));
        this.getCommand("resizereload").setExecutor(new ResizeReloadCommand(this));
        this.getCommand("resizehelp").setExecutor(new ResizeHelpCommand());

        // Register tab completer
        ResizeTabCompleter tabCompleter = new ResizeTabCompleter(this);
        this.getCommand("resize").setTabCompleter(tabCompleter);
        this.getCommand("resizegroup").setTabCompleter(tabCompleter);

        // Register event listeners
        getServer().getPluginManager().registerEvents(new PlayerChangedWorldListener(this), this);

        getLogger().info("AtlasCoreResize has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("AtlasCoreResize has been disabled.");
    }

    public Map<UUID, String> getPlayerGroups() {
        return playerGroups;
    }
}