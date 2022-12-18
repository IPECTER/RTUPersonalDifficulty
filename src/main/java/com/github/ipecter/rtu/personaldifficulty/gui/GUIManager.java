package com.github.ipecter.rtu.personaldifficulty.gui;

import com.github.ipecter.rtu.personaldifficulty.Difficulty;
import com.github.ipecter.rtu.personaldifficulty.manager.ConfigManager;
import com.github.ipecter.rtu.personaldifficulty.manager.DifficultyManager;
import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class GUIManager {

    private ConfigManager configManager = ConfigManager.getInstance();
    private DifficultyManager difficultyManager = DifficultyManager.getInstance();

    public GUIManager() {
    }

    public final static GUIManager getInstance() {
        return GUIManagerClass.instance;
    }

    public void openInventory(Player player) {
        player.openInventory(getInventory(player));
    }

    public String getTitle(Player player) {
        return RTUPluginLib.getTextManager().formatted(player, configManager.getTranslation("prefix") + configManager.getTranslation("guiTitle"));
    }


    private Inventory getInventory(Player player) {
        Inventory inv;
        String title = RTUPluginLib.getTextManager().formatted(player, configManager.getTranslation("prefix") + configManager.getTranslation("guiTitle"));
        int size = difficultyManager.getDifficulties().size();
        if (size <= 9) {
            inv = Bukkit.createInventory(null, 9, title);
        } else if (size <= 18) {
            inv = Bukkit.createInventory(null, 18, title);
        } else if (size <= 27) {
            inv = Bukkit.createInventory(null, 27, title);
        } else if (size <= 36) {
            inv = Bukkit.createInventory(null, 36, title);
        } else if (size <= 48) {
            inv = Bukkit.createInventory(null, 48, title);
        } else {
            inv = Bukkit.createInventory(null, 56, title);
        }
        for (Difficulty difficulty : difficultyManager.getDifficulties()) {
            ItemStack itemStack = new ItemStack(difficulty.getMaterial());
            createItem(difficulty, itemStack, difficulty.getDisplayName(), difficulty.getDescription());
            inv.setItem(difficulty.getDifficulty(), itemStack);
        }
        return inv;
    }

    private ItemStack createItem(Difficulty difficulty, ItemStack item, String name, List<String> lore) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(RTUPluginLib.getTextManager().colored(name));
        im.setLore(lore.stream().map(s -> RTUPluginLib.getTextManager().colored(s)).collect(Collectors.toList()));
        im.setCustomModelData(difficulty.getCustomModelData());
        item.setItemMeta(im);
        return item;
    }

    private static class GUIManagerClass {
        private static final GUIManager instance = new GUIManager();
    }
}
