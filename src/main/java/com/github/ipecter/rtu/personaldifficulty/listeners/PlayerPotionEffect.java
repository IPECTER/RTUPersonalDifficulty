package com.github.ipecter.rtu.personaldifficulty.listeners;

import com.github.ipecter.rtu.personaldifficulty.Difficulty;
import com.github.ipecter.rtu.personaldifficulty.manager.DifficultyManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class PlayerPotionEffect implements Listener {

    List<PotionEffectType> potionEffectTypeList = Arrays.asList(
            PotionEffectType.SLOW,
            PotionEffectType.SLOW_DIGGING,
            PotionEffectType.CONFUSION,
            PotionEffectType.BLINDNESS,
            PotionEffectType.HUNGER,
            PotionEffectType.WEAKNESS,
            PotionEffectType.POISON
    );

    @EventHandler
    public void onPlayerPotionEffect(EntityPotionEffectEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (e.getAction() == EntityPotionEffectEvent.Action.ADDED) {
                Difficulty difficulty = DifficultyManager.getInstance().getDifficulty(player);
                if (!difficulty.isEnableDebuffEffect()) {
                    if (potionEffectTypeList.contains(e.getModifiedType())) e.setCancelled(true);
                }
            }
        }
    }

}
