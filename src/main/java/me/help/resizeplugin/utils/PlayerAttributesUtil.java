package me.help.resizeplugin.utils;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.util.List;

public class PlayerAttributesUtil {

    public static boolean isWorldDisabled(Player player, FileConfiguration config) {
        List<String> disabledWorlds = config.getStringList("settings.disabled-worlds");
        return disabledWorlds != null && disabledWorlds.contains(player.getWorld().getName());
    }

    public static void applyPlayerAttributes(Player player, FileConfiguration config) {
        if (isWorldDisabled(player, config)) {
            resetPlayerAttributes(player, config);
            return;
        }

        double scale = getPlayerScale(player);
        String detectedGroup = null;

        if (config.getConfigurationSection("groups") != null) {
            for (String group : config.getConfigurationSection("groups").getKeys(false)) {
                double groupScale = config.getDouble("groups." + group + ".scale", 1.0);
                if (scale == groupScale) {
                    detectedGroup = group;
                    break;
                }
            }
        }

        if (detectedGroup != null) {
            setPlayerGroup(player, detectedGroup, config);
        } else {
            double baseSpeed = config.getDouble("settings.base-speed", 0.2);
            double newSpeed = Math.max(0.05, Math.min(baseSpeed * (1 / scale), 1.0));

            setPlayerSpeed(player, newSpeed);
            player.sendMessage("§eYour speed has been updated based on your scale: " + scale);
        }
    }

    public static void setPlayerGroup(Player player, String group, FileConfiguration config) {
        if (isWorldDisabled(player, config)) {
            resetPlayerAttributes(player, config);
            return;
        }

        double scale = config.getDouble("groups." + group + ".scale", 1.0);
        double speedMultiplier = config.getDouble("groups." + group + ".speed-multiplier", 1.0);
        double jumpMultiplier = config.getDouble("groups." + group + ".jump-multiplier", 1.0);
        double blockReach = config.getDouble("groups." + group + ".block-reach", 4.5);
        double entityReach = config.getDouble("groups." + group + ".entity-reach", 3.0);
        double fallDistance = config.getDouble("groups." + group + ".safe-fall-distance", 3.0);

        setPlayerScale(player, scale);
        setPlayerSpeed(player, speedMultiplier);
        setPlayerJump(player, jumpMultiplier);
        setPlayerBlockReach(player, blockReach);
        setPlayerEntityReach(player, entityReach);
        setPlayerSafeFallDistance(player, fallDistance);

        player.sendMessage("§aYour group has been applied: " + group);
    }

    public static void resetPlayerAttributes(Player player, FileConfiguration config) {
        setPlayerScale(player, 1.0);
        setPlayerSpeed(player, 1.0);
        setPlayerJump(player, 1.0);
        setPlayerBlockReach(player, 4.5);
        setPlayerEntityReach(player, 3.0);
        setPlayerSafeFallDistance(player, 3.0);
    }

    public static void setPlayerScale(Player player, double scale) {
        AttributeInstance scaleAttr = player.getAttribute(Attribute.GENERIC_SCALE);
        if (scaleAttr != null) {
            scaleAttr.setBaseValue(scale);
        }
    }

    public static double getPlayerScale(Player player) {
        AttributeInstance scaleAttr = player.getAttribute(Attribute.GENERIC_SCALE);
        return (scaleAttr != null) ? scaleAttr.getBaseValue() : 1.0;
    }

    public static void setPlayerSpeed(Player player, double speedMultiplier) {
        AttributeInstance speedAttr = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        if (speedAttr != null) {
            double baseSpeed = player.getServer().getPluginManager().getPlugin("AtlasCoreResize")
                    .getConfig().getDouble("settings.base-speed", 0.2);
            double newSpeed = Math.max(0.05, Math.min(baseSpeed * speedMultiplier, 1.0));
            speedAttr.setBaseValue(newSpeed);
        }
    }

    public static void setPlayerJump(Player player, double jumpMultiplier) {
        AttributeInstance jumpAttr = player.getAttribute(Attribute.GENERIC_JUMP_STRENGTH);
        if (jumpAttr != null) {
            double baseJump = player.getServer().getPluginManager().getPlugin("AtlasCoreResize")
                    .getConfig().getDouble("settings.base-jump", 0.42);
            double newJump = Math.max(0.1, Math.min(baseJump * jumpMultiplier, 2.0));
            jumpAttr.setBaseValue(newJump);
        }
    }

    public static void setPlayerBlockReach(Player player, double blockReach) {
        AttributeInstance reachAttr = player.getAttribute(Attribute.PLAYER_BLOCK_INTERACTION_RANGE);
        if (reachAttr != null) {
            double minReach = player.getServer().getPluginManager().getPlugin("AtlasCoreResize")
                    .getConfig().getDouble("settings.min-block-reach", 2.0);
            double maxReach = player.getServer().getPluginManager().getPlugin("AtlasCoreResize")
                    .getConfig().getDouble("settings.max-block-reach", 10.0);
            reachAttr.setBaseValue(Math.max(minReach, Math.min(blockReach, maxReach)));
        }
    }

    public static void setPlayerEntityReach(Player player, double entityReach) {
        AttributeInstance reachAttr = player.getAttribute(Attribute.PLAYER_ENTITY_INTERACTION_RANGE);
        if (reachAttr != null) {
            double minReach = player.getServer().getPluginManager().getPlugin("AtlasCoreResize")
                    .getConfig().getDouble("settings.min-entity-reach", 2.0);
            double maxReach = player.getServer().getPluginManager().getPlugin("AtlasCoreResize")
                    .getConfig().getDouble("settings.max-entity-reach", 10.0);
            reachAttr.setBaseValue(Math.max(minReach, Math.min(entityReach, maxReach)));
        }
    }

    public static void setPlayerSafeFallDistance(Player player, double fallDistance) {
        AttributeInstance fallAttr = player.getAttribute(Attribute.GENERIC_SAFE_FALL_DISTANCE);
        if (fallAttr != null) {
            double minFallDistance = player.getServer().getPluginManager().getPlugin("AtlasCoreResize")
                    .getConfig().getDouble("settings.min-safe-fall-distance", 3.0);
            double maxFallDistance = player.getServer().getPluginManager().getPlugin("AtlasCoreResize")
                    .getConfig().getDouble("settings.max-safe-fall-distance", 50.0);
            fallAttr.setBaseValue(Math.max(minFallDistance, Math.min(fallDistance, maxFallDistance)));
        }
    }
}