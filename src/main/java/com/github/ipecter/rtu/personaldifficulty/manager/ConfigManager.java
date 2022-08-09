package com.github.ipecter.rtu.personaldifficulty.manager;

import com.github.ipecter.rtu.personaldifficulty.Difficulty;
import com.github.ipecter.rtu.personaldifficulty.RTUPersonalDifficulty;
import com.github.ipecter.rtu.utilapi.RTUUtilAPI;
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

    public ConfigManager() {
    }

    public final static ConfigManager getInstance() {
        return ConfigManager.InnerInstanceClass.instance;
    }

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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getReloadMsg() {
        return reloadMsg;
    }

    public void setReloadMsg(String reloadMsg) {
        this.reloadMsg = reloadMsg;
    }

    public String getCommandWrongUsage() {
        return commandWrongUsage;
    }

    public void setCommandWrongUsage(String commandWrongUsage) {
        this.commandWrongUsage = commandWrongUsage;
    }

    private DifficultyManager manager = DifficultyManager.getInstance();

    public String getCommandWrongUsageOp() {
        return commandWrongUsageOp;
    }

    public void setCommandWrongUsageOp(String commandWrongUsageOp) {
        this.commandWrongUsageOp = commandWrongUsageOp;
    }

    public String getCommandWrongUsageConsole() {
        return commandWrongUsageConsole;
    }

    public String getNoPermission() {
        return noPermission;
    }

    public void setNoPermission(String noPermission) {
        this.noPermission = noPermission;
    }

    public void setCommandWrongUsageConsole(String commandWrongUsageConsole) {
        this.commandWrongUsageConsole = commandWrongUsageConsole;
    }

    public String getGuiTitle() {
        return guiTitle;
    }

    public void setGuiTitle(String guiTitle) {
        this.guiTitle = guiTitle;
    }

    public String getDifficultyChanged() {
        return difficultyChanged;
    }

    public void setDifficultyChanged(String difficultyChanged) {
        this.difficultyChanged = difficultyChanged;
    }

    public List<String> getMobList() {
        return mobList;
    }

    public void setMobList(List<String> mobList) {
        this.mobList = mobList;
    }

    public void initConfigFiles() {
        initSetting(RTUUtilAPI.getFileManager().copyResource("Setting.yml"));
        initMessage(RTUUtilAPI.getFileManager().copyResource("Translations", "Locale_" + locale + ".yml"));
        initDfficult(RTUUtilAPI.getFileManager().copyResource("Difficulty.yml"));
        initMobList(RTUUtilAPI.getFileManager().copyResource("MobList.yml"));
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

    private void initMessage(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        prefix = config.getString("prefix", "").isEmpty() ? prefix : config.getString("prefix");
        reloadMsg = config.getString("reloadMsg");
        commandWrongUsage = config.getString("commandWrongUsage");
        commandWrongUsageOp = config.getString("commandWrongUsageOp");
        commandWrongUsageConsole = config.getString("commandWrongUsageConsole");
        noPermission = config.getString("noPermission");
        guiTitle = config.getString("guiTitle");
        difficultyChanged = config.getString("difficultyChanged");

        RTUUtilAPI.getFileManager().copyResource("Translations", "Locale_EN.yml");
        RTUUtilAPI.getFileManager().copyResource("Translations", "Locale_KR.yml");
    }

    private void initDfficult(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
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

    private void initMobList(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        mobList.addAll(config.getStringList("list"));
    }

    private static class InnerInstanceClass {
        private static final ConfigManager instance = new ConfigManager();
    }

}
