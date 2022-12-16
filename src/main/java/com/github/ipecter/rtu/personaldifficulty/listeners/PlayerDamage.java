package com.github.ipecter.rtu.personaldifficulty.listeners;

import com.github.ipecter.rtu.personaldifficulty.Difficulty;
import com.github.ipecter.rtu.personaldifficulty.manager.ConfigManager;
import com.github.ipecter.rtu.personaldifficulty.manager.DifficultyManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamage implements Listener {

    private ConfigManager configManager = ConfigManager.getInstance();

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {

        if (!configManager.isEnablePlugin()) return;
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            Difficulty difficulty = DifficultyManager.getInstance().getDifficulty(player);
            if (e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK)
                e.setDamage(e.getDamage() * difficulty.getFireMultiplier());
            else if (e.getCause() == EntityDamageEvent.DamageCause.FALL)
                e.setDamage(e.getDamage() * difficulty.getFallMultiplier());
            else if (e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION)
                e.setDamage(e.getDamage() * difficulty.getSuffocationMultiplier());
            else if (e.getCause() == EntityDamageEvent.DamageCause.DROWNING)
                e.setDamage(e.getDamage() * difficulty.getDrowningMultiplier());
        }

    }
}
