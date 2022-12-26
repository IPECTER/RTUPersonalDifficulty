package com.github.ipecter.rtu.personaldifficulty;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Difficulty {

    int difficulty;

    private String name;

    private String display;

    private Material material;

    private int customModelData;

    private List<String> description = new ArrayList<>();

    private double pvpMultiplier;

    private double pveMultiplier;

    private double expMultiplier;

    private boolean loseHunger;

    private boolean enableDebuffEffect;

    private boolean monsterIgnorePlayer;

    private boolean monsterCounterAttackPlayer;

    private boolean enableCreeperExplosionDamage;

    private boolean isClearAllWhenDie;

    private double fallMultiplier;

    private double fireMultiplier;

    private double suffocationMultiplier;

    private double drowningMultiplier;

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isClearAllWhenDie() {
        return this.isClearAllWhenDie;
    }

    public void setClearAllWhenDie(boolean clearAllWhenDie) {
        this.isClearAllWhenDie = clearAllWhenDie;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return this.display;
    }

    public void setDisplayName(String display) {
        this.display = ChatColor.translateAlternateColorCodes('&', display);
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public void setCustomModelData(int customModelData) {
        this.customModelData = customModelData;
    }

    public List<String> getDescription() {
        return this.description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public double getFallMultiplier() {
        return this.fallMultiplier;
    }

    public void setFallMultiplier(double fallMultiplier) {
        this.fallMultiplier = fallMultiplier;
    }

    public double getFireMultiplier() {
        return this.fireMultiplier;
    }

    public void setFireMultiplier(double fireMultiplier) {
        this.fireMultiplier = fireMultiplier;
    }

    public double getSuffocationMultiplier() {
        return this.suffocationMultiplier;
    }

    public void setSuffocationMultiplier(double suffocationMultiplier) {
        this.suffocationMultiplier = suffocationMultiplier;
    }

    public double getDrowningMultiplier() {
        return this.drowningMultiplier;
    }

    public void setDrowningMultiplier(double drowningMultiplier) {
        this.drowningMultiplier = drowningMultiplier;
    }

    public double getPvpMultiplier() {
        return this.pvpMultiplier;
    }

    public void setPvpMultiplier(double pvpMultiplier) {
        this.pvpMultiplier = pvpMultiplier;
    }

    public double getPveMultiplier() {
        return this.pveMultiplier;
    }

    public void setPveMultiplier(double pveMultiplier) {
        this.pveMultiplier = pveMultiplier;
    }

    public boolean isLoseHunger() {
        return this.loseHunger;
    }

    public void setLoseHunger(boolean loseHunger) {
        this.loseHunger = loseHunger;
    }

    public double getExpMultiplier() {
        return this.expMultiplier;
    }

    public void setExpMultiplier(double expMultiplier) {
        this.expMultiplier = expMultiplier;
    }

    public boolean isEnableDebuffEffect() {
        return this.enableDebuffEffect;
    }

    public void setEnableDebuffEffect(boolean enableDebuffEffect) {
        this.enableDebuffEffect = enableDebuffEffect;
    }

    public boolean isEnableCreeperExplosionDamage() {
        return this.enableCreeperExplosionDamage;
    }

    public void setEnableCreeperExplosionDamage(boolean enableCreeperExplosionDamage) {
        this.enableCreeperExplosionDamage = enableCreeperExplosionDamage;
    }

    public boolean isMonsterIgnorePlayer() {
        return this.monsterIgnorePlayer;
    }

    public void setMonsterIgnorePlayer(boolean monsterIgnorePlayer) {
        this.monsterIgnorePlayer = monsterIgnorePlayer;
    }

    public boolean isMonsterCounterAttackPlayer() {
        return this.monsterCounterAttackPlayer && this.monsterIgnorePlayer;
    }

    public void setMonsterCounterAttackPlayer(boolean monsterCounterAttackPlayer) {
        this.monsterCounterAttackPlayer = monsterCounterAttackPlayer && this.monsterIgnorePlayer;
    }
}
