package com.github.ipecter.rtu.personaldifficulty.listeners;

import com.github.ipecter.rtu.personaldifficulty.Difficulty;
import com.github.ipecter.rtu.personaldifficulty.manager.ConfigManager;
import com.github.ipecter.rtu.personaldifficulty.manager.DifficultyManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamageByEntity implements Listener {

    private ConfigManager configManager = ConfigManager.getInstance();

    @EventHandler
    public void onPlayerDamageByEntity(EntityDamageByEntityEvent e) {

        if (!configManager.isEnablePlugin()) return;
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            Difficulty difficulty = DifficultyManager.getInstance().getDifficulty(player);
            if (e.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile) e.getDamager();
                if (projectile.getShooter() instanceof Player) {
                    e.setDamage(e.getDamage() * difficulty.getPvpMultiplier());
                } else {
                    e.setDamage(e.getDamage() * difficulty.getPveMultiplier());
                }
            } else if (e.getDamager() instanceof Player) {
                e.setDamage(e.getDamage() * difficulty.getPvpMultiplier());
            }
            if (e.getDamager().getType() == EntityType.CREEPER) {
                if (!difficulty.isEnableCreeperExplosionDamage()) e.setCancelled(true);
            } else {
                e.setDamage(e.getDamage() * difficulty.getPveMultiplier());
            }
        }

    }

}
