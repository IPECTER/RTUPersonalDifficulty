package com.github.ipecter.rtu.personaldifficulty;

import com.github.ipecter.rtu.personaldifficulty.commands.Command;
import com.github.ipecter.rtu.personaldifficulty.listeners.PlayerJoin;
import com.github.ipecter.rtu.personaldifficulty.manager.ConfigManager;
import com.github.ipecter.rtu.utilapi.RTUUtilAPI;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RTUPersonalDifficulty extends JavaPlugin {

    private String prefix = IridiumColorAPI.process("<GRADIENT:9ba832>[ RTUCommandControl ]</GRADIENT:a3a3a3> ");

    @Override
    public void onEnable() {
        try {
            RTUUtilAPI.init(this);
            Bukkit.getLogger().info(RTUUtilAPI.getTextManager().formatted(prefix + "&aEnable&f!"));
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
        Bukkit.getLogger().info(RTUUtilAPI.getTextManager().formatted(prefix + "&cDisable&f!"));
    }

    protected void registerEvent() {
        Bukkit.getPluginManager().registerEvents(new PlayerCommandSend(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocess(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
    }

    protected void setExecutor() {
        getCommand("rtupd").setExecutor(new Command());
    }

    private void loadDependencies() {
        loadPAPI();
    }

    private void loadPAPI() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            RTUUtilAPI.getDependencyManager().setUsePAPI(true);
        }
    }

}
