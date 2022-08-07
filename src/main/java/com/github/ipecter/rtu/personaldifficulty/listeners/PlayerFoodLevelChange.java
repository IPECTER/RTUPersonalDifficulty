package com.github.ipecter.rtu.personaldifficulty.listeners;

import com.github.ipecter.rtu.personaldifficulty.Difficulty;
import com.github.ipecter.rtu.personaldifficulty.manager.DifficultyManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerFoodLevelChange implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onFoodChange(FoodLevelChangeEvent e) {

        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (player.getFoodLevel() >= e.getFoodLevel()) {
                Difficulty difficulty = DifficultyManager.getInstance().getDifficulty(player);
                if (!difficulty.isLoseHunger()) e.setCancelled(true);
            }
        }

    }

}
