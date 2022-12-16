package com.github.ipecter.rtu.personaldifficulty.listeners;

import com.github.ipecter.rtu.personaldifficulty.Difficulty;
import com.github.ipecter.rtu.personaldifficulty.manager.ConfigManager;
import com.github.ipecter.rtu.personaldifficulty.manager.DifficultyManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    private ConfigManager configManager = ConfigManager.getInstance();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {

        if (!configManager.isEnablePlugin()) return;
        Player player = e.getEntity();
        Difficulty difficulty = DifficultyManager.getInstance().getDifficulty(player);
        if (difficulty.isClearAllWhenDie()) {
            player.getInventory().clear();
            player.setLevel(0);
            player.setExp(0.0F);
        }

    }
}
