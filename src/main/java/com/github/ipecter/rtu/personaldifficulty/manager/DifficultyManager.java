package com.github.ipecter.rtu.personaldifficulty.manager;

import com.github.ipecter.rtu.personaldifficulty.Difficulty;
import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import org.bukkit.entity.Player;

import java.util.*;

public class DifficultyManager {

    private final Map<UUID, Difficulty> playerCache = Collections.synchronizedMap(new HashMap<>());
    private List<Difficulty> difficulties = Collections.synchronizedList(new ArrayList<>());
    private Set<String> d = new HashSet<>();
    private int defaultDifficulty = 0;

    public final static DifficultyManager getInstance() {
        return DifficultyManagerClass.instance;
    }

    public void registerDifficulty(Difficulty difficulty) {
        difficulties.add(difficulty);
    }

    public void setDefaultDifficulty(int defaultDifficulty) {
        this.defaultDifficulty = defaultDifficulty;
    }

    public Difficulty getDifficulty(Player player) {
        UUID uuid = player.getUniqueId();
        Difficulty difficulty = playerCache.get(uuid);
        if (difficulty == null) {
            String value = RTUPluginLib.getStatusManager().getStatus(player, "difficulty");
            int index = value != null ? Integer.valueOf(value) : defaultDifficulty;
            difficulty = difficulties.get(index) != null ? difficulties.get(index) : difficulties.get(defaultDifficulty);
            playerCache.put(uuid, difficulty);
        }
        return difficulty;
    }

    public void setDifficulty(Player player, int value) {
        if (value >= difficulties.size())
            throw new IllegalArgumentException("A difficulty with value " + value + " is not registered");
        Difficulty difficulty = difficulties.get(value);
        RTUPluginLib.getStatusManager().setStatus(player, "difficulty", difficulty.getDifficulty());
        playerCache.put(player.getUniqueId(), difficulty);
    }

    public List<Difficulty> getDifficulties() {
        return this.difficulties;
    }

    public void clearDifficulties() {
        this.difficulties.clear();
    }

    public void flushCache() {
        this.playerCache.clear();
    }

    private static class DifficultyManagerClass {
        private static final DifficultyManager instance = new DifficultyManager();
    }
}
