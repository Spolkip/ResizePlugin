package me.help.resizeplugin.listeners;

import me.help.resizeplugin.ResizePlugin;
import me.help.resizeplugin.utils.PlayerAttributesUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerChangedWorldListener implements Listener {

    private final ResizePlugin plugin;

    public PlayerChangedWorldListener(ResizePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        String lastGroup = plugin.getPlayerGroups().get(player.getUniqueId());

        if (PlayerAttributesUtil.isWorldDisabled(player, plugin.getConfig())) {
            // Reset attributes if the new world is disabled
            PlayerAttributesUtil.resetPlayerAttributes(player, plugin.getConfig());
            player.sendMessage("§cResizing is disabled in this world. Your attributes have been reset.");
        } else if (lastGroup != null) {
            // Reapply the player's group if the new world is enabled
            PlayerAttributesUtil.setPlayerGroup(player, lastGroup, plugin.getConfig());
            player.sendMessage("§aYour group has been reapplied in this world.");
        }
    }
}