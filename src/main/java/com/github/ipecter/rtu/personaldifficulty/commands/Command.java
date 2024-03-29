package com.github.ipecter.rtu.personaldifficulty.commands;

import com.github.ipecter.rtu.personaldifficulty.gui.GUIManager;
import com.github.ipecter.rtu.personaldifficulty.manager.ConfigManager;
import com.github.ipecter.rtu.personaldifficulty.manager.DifficultyManager;
import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import com.github.ipecter.rtu.pluginlib.managers.TextManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Command implements CommandExecutor, TabCompleter {

    private ConfigManager configManager = ConfigManager.getInstance();
    private TextManager textManager = RTUPluginLib.getTextManager();

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!sender.hasPermission("rtupd.use")) {
            sender.sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("noPermission")));
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("rtupd.reload")) {
                configManager.initConfigFiles();
                sender.sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("reloadMsg")));
            } else {
                sender.sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("noPermission")));
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("gui")) {
            GUIManager.getInstance().openInventory(((Player) sender));
            return true;
        } else if (args.length >= 1 && configManager.getKeys().contains(args[0])) {
            if (args.length >= 2 && Bukkit.getPlayer(args[1]) != null) {
                if (sender.hasPermission("rtupd.toggle.other")) {
                    setDifficulty(Bukkit.getPlayer(args[1]), args[0]);
                    sender.sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + "&f" + args[1] + configManager.getTranslation("difficultyOtherChanged").replace("{difficulty}", getDifficulty(args[0]))));
                } else {
                    sender.sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("noPermission")));
                }
            } else {
                if (sender instanceof Player) {
                    setDifficulty((Player) sender, args[0]);
                    sender.sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + "&f" + configManager.getTranslation("difficultyChanged").replace("{difficulty}", getDifficulty(args[0]))));
                } else {
                    sender.sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("commandWrongUsageConsole")));
                }
            }
            return true;
        } else {
            if (sender.hasPermission("rtupd.reload")) {
                sender.sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("commandWrongUsageOp").replace("{difficulties}", String.join("&7/&f", configManager.getKeys()))));

            } else {
                sender.sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("commandWrongUsage").replace("{difficulties}", String.join("&7/&f", configManager.getKeys()))));
            }
            return true;
        }
    }

    private void setDifficulty(Player player, String name) {
        DifficultyManager.getInstance().setDifficulty(player, configManager.getKeys().indexOf(name));
    }

    private String getDifficulty(String name) {
        return DifficultyManager.getInstance().getDifficulties().get(configManager.getKeys().indexOf(name)).getDisplayName();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            if (!sender.hasPermission("rtupd.use")) {
                return list;
            }
            if (sender.hasPermission("rtupd.reload")) {
                list.add("reload");
            }
            if (sender.hasPermission("rtupd.toggle")) {
                list.addAll(configManager.getKeys());
            }
            if (sender.hasPermission("rtupd.gui")) {
                list.add("gui");
            }
            return list;
        } else if (args.length == 2) {
            if (sender.hasPermission("rtupd.toggle.other")) {
                return Bukkit.getOnlinePlayers().stream().map(player -> player.getName()).collect(Collectors.toList());
            }
        }
        return Arrays.asList();
    }
}
