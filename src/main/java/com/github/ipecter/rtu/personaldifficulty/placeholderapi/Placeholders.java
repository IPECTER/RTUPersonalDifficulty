package com.github.ipecter.rtu.personaldifficulty.placeholderapi;

import com.github.ipecter.rtu.personaldifficulty.Difficulty;
import com.github.ipecter.rtu.personaldifficulty.RTUPersonalDifficulty;
import com.github.ipecter.rtu.personaldifficulty.manager.DifficultyManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Placeholders extends PlaceholderExpansion {

    private RTUPersonalDifficulty plugin;

    public Placeholders(RTUPersonalDifficulty plugin) {
        this.plugin = plugin;
    }

    public boolean persist() {
        return true;
    }

    public boolean canRegister() {
        return true;
    }

    public String getAuthor() {
        return "IPECTER";
    }

    public String getIdentifier() {
        return "rtupd";
    }

    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    public String onRequest(OfflinePlayer offlinePlayer, String params) {
        Player player = offlinePlayer.getPlayer();
        if (player.isOnline()) {
            Difficulty difficulty = DifficultyManager.getInstance().getDifficulty(player);
            switch (params) {
                case "displayname": {
                    return difficulty.getDisplayName();
                }
                case "name": {
                    return difficulty.getName();
                }
            }
        }
        return "Player is offline!";
    }
}
