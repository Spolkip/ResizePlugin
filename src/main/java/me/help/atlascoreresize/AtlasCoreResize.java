package me.help.atlascoreresize;

import me.help.atlascoreresize.commands.ResizeCommand;
import me.help.atlascoreresize.commands.ResizeGroupCommand;
import me.help.atlascoreresize.commands.ResizeReloadCommand;
import me.help.atlascoreresize.commands.ResizeHelpCommand;
import me.help.atlascoreresize.commands.ResizeTabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public class AtlasCoreResize extends JavaPlugin {

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

        getLogger().info("AtlasCoreResize has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("AtlasCoreResize has been disabled.");
    }
}
