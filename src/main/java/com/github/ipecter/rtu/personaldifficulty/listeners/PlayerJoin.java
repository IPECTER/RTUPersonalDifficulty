package com.github.ipecter.rtu.personaldifficulty.listeners;

import com.github.ipecter.rtu.personaldifficulty.manager.ConfigManager;
import com.github.ipecter.rtu.utilapi.RTUUtilAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private ConfigManager configManager = ConfigManager.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        if (!configManager.isEnablePlugin()) return;
        Player player = e.getPlayer();
        if (configManager.isMotd()) {
            player.sendMessage(RTUUtilAPI.getTextManager().formatted(player, configManager.getPrefix() + "&fRTU PersonalDifficulty developed by IPECTER & Coll1234567 (Original)"));
        } else {
            if (player.isOp())
                player.sendMessage(RTUUtilAPI.getTextManager().formatted(player, configManager.getPrefix() + "&fRTU PersonalDifficulty developed by IPECTER & Coll1234567 (Original)"));
        }

    }

}
