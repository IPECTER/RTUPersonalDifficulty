package com.github.ipecter.rtu.personaldifficulty.listeners;

import com.github.ipecter.rtu.personaldifficulty.Difficulty;
import com.github.ipecter.rtu.personaldifficulty.manager.ConfigManager;
import com.github.ipecter.rtu.personaldifficulty.manager.DifficultyManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityTargetPlayer implements Listener {

    private ConfigManager configManager = ConfigManager.getInstance();

    @EventHandler
    public void onTarget(EntityTargetEvent event) {

        if (!configManager.isEnablePlugin()) return;
        if (event.getTarget() instanceof Player) {
            Player player = (Player) event.getTarget();
            if (player != null) {
                Difficulty difficulty = DifficultyManager.getInstance().getDifficulty(player);
                if (difficulty.isMonsterIgnorePlayer()) {
                    if (difficulty.isMonsterCounterAttackPlayer()) {
                        if (event.getReason() == EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY) return;
                    }
                    event.setCancelled(true);
                    event.setTarget(null);
                }
            }
        }

    }
}
