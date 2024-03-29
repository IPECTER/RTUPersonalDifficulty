package com.github.ipecter.rtu.personaldifficulty;

import com.github.ipecter.rtu.personaldifficulty.commands.Command;
import com.github.ipecter.rtu.personaldifficulty.gui.listeners.InventoryClick;
import com.github.ipecter.rtu.personaldifficulty.listeners.*;
import com.github.ipecter.rtu.personaldifficulty.manager.ConfigManager;
import com.github.ipecter.rtu.personaldifficulty.placeholderapi.Placeholders;
import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RTUPersonalDifficulty extends JavaPlugin {
    //85%, 80%
    private String prefix = IridiumColorAPI.process("<GRADIENT:1f4dcc>[ RTUPersonalDifficulty ]</GRADIENT:a3a3a3> ");

    @Override
    public void onEnable() {
        try {
            RTUPluginLib.init(this);
            Bukkit.getLogger().info(RTUPluginLib.getTextManager().formatted(prefix + "&aEnable&f!"));
            ConfigManager.getInstance().initConfigFiles();
            registerEvent();
            setExecutor();
            loadDependencies();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(RTUPluginLib.getTextManager().formatted(prefix + "&cDisable&f!"));
    }

    protected void registerEvent() {
        Bukkit.getPluginManager().registerEvents(new EntityDeath(), this);
        Bukkit.getPluginManager().registerEvents(new EntityTargetPlayer(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDamage(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDamageByEntity(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerFoodLevelChange(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerPotionEffect(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
    }

    protected void setExecutor() {
        getCommand("rtupd").setExecutor(new Command());
    }

    private void loadDependencies() {
        loadPAPI();
    }

    private void loadPAPI() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            RTUPluginLib.getDependencyManager().setUsePAPI(true);
            new Placeholders(this).register();
        }
    }

}
