package com.github.ipecter.rtu.personaldifficulty.manager;

import com.github.ipecter.rtu.personaldifficulty.Difficulty;
import com.github.ipecter.rtu.personaldifficulty.RTUPersonalDifficulty;
import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ConfigManager {

    private Plugin plugin = RTUPersonalDifficulty.getPlugin(RTUPersonalDifficulty.class);
    private boolean enablePlugin = true;
    private boolean motd = true;
    private String locale = "EN";
    private Map<String, List<String>> cmdList = Collections.synchronizedMap(new HashMap<>());
    private String prefix = IridiumColorAPI.process("<GRADIENT:1f4dcc>[ RTUPersonalDifficulty ]</GRADIENT:a3a3a3> ");
    private String reloadMsg = "";
    private String commandWrongUsage = "";
    private String commandWrongUsageOp = "";
    private String commandWrongUsageConsole = "";
    private String noPermission = "";
    private String guiTitle = "";
    private String difficultyChanged = "";
    private List<String> mobList = Collections.synchronizedList(new ArrayList<>());
    private List<String> keys = Collections.synchronizedList(new ArrayList<>());

    private DifficultyManager manager = DifficultyManager.getInstance();

    public boolean isEnablePlugin() {
        return enablePlugin;
    }

    public void setEnablePlugin(boolean enablePlugin) {
        this.enablePlugin = enablePlugin;
    }

    public boolean isMotd() {
        return motd;
    }

    public void setMotd(boolean motd) {
        this.motd = motd;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Map<String, List<String>> getCmdList() {
        return cmdList;
    }

    public void setCmdList(Map<String, List<String>> cmdList) {
        this.cmdList = cmdList;
    }

    public List<String> getMobList() {
        return mobList;
    }

    public void setMobList(List<String> mobList) {
        this.mobList = mobList;
    }

    public void initConfigFiles() {
        initSetting(RTUPluginLib.getFileManager().copyResource("Setting.yml"));
        initMessage(RTUPluginLib.getFileManager().copyResource("Translations", "Locale_" + locale + ".yml"));
        initDfficult(RTUPluginLib.getFileManager().copyResource("Difficulty.yml"));
        initMobList(RTUPluginLib.getFileManager().copyResource("MobList.yml"));
    }

    private void initSetting(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        enablePlugin = config.getBoolean("enablePlugin");
        motd = config.getBoolean("motd");
        locale = config.getString("locale");
    }

    public List<String> getKeys() {
        return keys;
    }

    private Map<String, String> msgKeyMap = Collections.synchronizedMap(new HashMap<>());

    public final static ConfigManager getInstance() {
        return InnerInstanceClass.instance;
    }

    public String getTranslation(String key) {
        return msgKeyMap.getOrDefault(key, "");
    }

    private void initMessage(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (String key : config.getKeys(false)) {
            if (key.equals("prefix")) {
                msgKeyMap.put(key, config.getString("prefix", "").isEmpty() ? prefix : config.getString("prefix"));
            } else {
                msgKeyMap.put(key, config.getString(key));
            }
        }

        RTUPluginLib.getFileManager().copyResource("Translations", "Locale_EN.yml");
        RTUPluginLib.getFileManager().copyResource("Translations", "Locale_KR.yml");
    }

    private void initMobList(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        mobList.addAll(config.getStringList("list"));
    }

    private void initDfficult(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        manager.clearDifficulties();
        manager.flushCache();
        manager.setDefaultDifficulty(config.getInt("defaultDifficulty", 0));
        ConfigurationSection difficultySection = config.getConfigurationSection("difficulties");
        if (config.getConfigurationSection("difficulties") != null) {
            keys = difficultySection.getKeys(false).stream().collect(Collectors.toList());
            for (String diff : difficultySection.getKeys(false)) {
                Difficulty difficulty = new Difficulty();
                difficulty.setDifficulty(keys.indexOf(diff));
                difficulty.setName(diff);
                difficulty.setDisplayName(difficultySection.getString(diff + ".display", "Default Name"));
                difficulty.setMaterial(Material.getMaterial(difficultySection.getString(diff + ".gui.material", "WHITE_STAINED_GLASS")));
                difficulty.setCustomModelData(difficultySection.getInt(diff + ".gui.customModelData"));
                difficulty.setDescription(difficultySection.getStringList(diff + ".gui.description"));
                difficulty.setFallMultiplier(difficultySection.getDouble(diff + ".fallDamageMultiplier", 1.0D));
                difficulty.setFireMultiplier(difficultySection.getDouble(diff + ".fireDamageMultiplier", 1.0D));
                difficulty.setSuffocationMultiplier(difficultySection.getDouble(diff + ".suffocationDamageMultiplier", 1.0D));
                difficulty.setDrowningMultiplier(difficultySection.getDouble(diff + ".drowningDamageMultiplier", 1.0D));
                difficulty.setPvpMultiplier(difficultySection.getDouble(diff + ".PVPDamageMultiplier", 1.0D));
                difficulty.setPveMultiplier(difficultySection.getDouble(diff + ".PVEDamageMultiplier", 1.0D));
                difficulty.setExpMultiplier(difficultySection.getDouble(diff + ".mobExperienceMultiplier", 1.0D));
                difficulty.setLoseHunger(difficultySection.getBoolean(diff + ".loseHunger", true));
                difficulty.setEnableDebuffEffect(difficultySection.getBoolean(diff + ".debuffEffect", true));
                difficulty.setMonsterIgnorePlayer(difficultySection.getBoolean(diff + ".monsterIgnorePlayer", false));
                difficulty.setEnableCreeperExplosionDamage(difficultySection.getBoolean(diff + ".creeperExplosionDamage", true));
                difficulty.setClearAllWhenDie(difficultySection.getBoolean(diff + ".clearAllWhenDie", false));
                manager.registerDifficulty(difficulty);
            }
        }
    }

    private static class InnerInstanceClass {
        private static final ConfigManager instance = new ConfigManager();
    }

}
