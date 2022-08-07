package com.github.ipecter.rtu.personaldifficulty.listeners;

import com.github.ipecter.rtu.personaldifficulty.Difficulty;
import com.github.ipecter.rtu.personaldifficulty.manager.DifficultyManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener {

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {

        if (event.getEntity().getKiller() instanceof Player) {
            Player player = event.getEntity().getKiller();
            if (player != null) {
                Difficulty difficulty = DifficultyManager.getInstance().getDifficulty(player);
                event.setDroppedExp((int) Math.round(event.getDroppedExp() * difficulty.getExpMultiplier()));
            }
        }

    }

}
