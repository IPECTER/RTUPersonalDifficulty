package com.github.ipecter.rtu.personaldifficulty.gui.listeners;

import com.github.ipecter.rtu.personaldifficulty.gui.GUIManager;
import com.github.ipecter.rtu.personaldifficulty.manager.ConfigManager;
import com.github.ipecter.rtu.personaldifficulty.manager.DifficultyManager;
import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    private ConfigManager configManager = ConfigManager.getInstance();
    private DifficultyManager difficultyManager = DifficultyManager.getInstance();
    private GUIManager guiManager = GUIManager.getInstance();

    @EventHandler
    public void onClickInventorySlot(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals(guiManager.getTitle(player))) {
            e.setCancelled(true);
            difficultyManager.setDifficulty(player, e.getSlot());
            String name = difficultyManager.getDifficulty(player).getDisplayName();
            player.sendMessage(RTUPluginLib.getTextManager().formatted(player, configManager.getTranslation("prefix") + configManager.getTranslation("difficultyChanged").replace("{difficulty}", name)));
        }
    }
}
