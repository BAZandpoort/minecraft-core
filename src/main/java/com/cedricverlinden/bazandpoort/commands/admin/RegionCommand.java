package com.cedricverlinden.bazandpoort.commands.admin;

import com.cedricverlinden.bazandpoort.Core;
import com.cedricverlinden.bazandpoort.utils.ChatUtils;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class RegionCommand implements CommandExecutor, TabCompleter {

    WorldGuard wg = Core.instance().getWorldGuard();
    RegionContainer container = wg.getPlatform().getRegionContainer();

    YamlConfiguration data = Core.instance().getLectures().getEditableFile();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatUtils.color("&cYou can only use this command in-game."));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatUtils.color("&cUsage: /region <add, list> [region] [lecture]"));
            return true;
        }

        World world = wg.getPlatform().getMatcher().getWorldByName(player.getWorld().getName());
        RegionManager regions = container.get(world);

        ConfigurationSection lecturesSection = data.getConfigurationSection("lectures");
        if (args[0].equals("list")) {
            if (regions == null || regions.getRegions() == null || regions.getRegions().isEmpty()) {
                player.sendMessage(ChatUtils.color("There are no available regions."));
                return true;
            }

            player.sendMessage(ChatUtils.color("&8&m----------------------------------------"));
            player.sendMessage(ChatUtils.color("&2Available regions:"));
            for (Map.Entry<String, ProtectedRegion> region: regions.getRegions().entrySet()) {
                player.sendMessage(ChatUtils.color("&8- &7" + region.getValue().getId()));
            }

            if (lecturesSection == null) {
                player.sendMessage(ChatUtils.color("&8&m----------------------------------------"));
                return true;
            }

            Set<String> lectures = lecturesSection.getKeys(false);

            player.sendMessage("");
            player.sendMessage(ChatUtils.color("&2Added regions:"));

            for (String name : lectures) {
                String regionName = data.getString("lectures." + name + ".region");
                name = name.substring(0, 1).toUpperCase() + name.substring(1);

                if (regionName == null || regionName.isEmpty()) {
                    player.sendMessage(ChatUtils.color("&8- &c" + name + " (none)"));
                    continue;
                }

                ProtectedRegion region = regions.getRegion(regionName);
                if (region == null) {
                    player.sendMessage(ChatUtils.color("&8- &c" + name + " (not valid)"));
                    continue;
                }

                player.sendMessage(ChatUtils.color("&8- &a" + region.getId() + " (" + name + ")"));
            }

            player.sendMessage(ChatUtils.color("&8&m----------------------------------------"));
            return true;
        }

        if (args[0].equals("add")) {
            if (args.length <= 2) {
                player.sendMessage(ChatUtils.color("&cUsage: /region add <region> <lecture>"));
                return true;
            }

            String regionName = args[1].toLowerCase();
            String lectureName = args[2].toLowerCase();
            if (regions == null || regions.getRegions() == null || regions.getRegions().isEmpty()) {
                player.sendMessage(ChatUtils.color("&cThere are no available regions."));
                return true;
            }

            if (lecturesSection == null) {
                player.sendMessage(ChatUtils.color("&cThere are no available lectures."));
                return true;
            }

            ProtectedRegion region = regions.getRegion(regionName);
            if (region == null) {
                player.sendMessage(ChatUtils.color("&cThe region '" + regionName + "' does not exist."));
                return true;
            }

//            try {
//                Lectures.valueOf(lectureName.toUpperCase());
//            } catch (IllegalArgumentException exception) {
//                player.sendMessage(ChatUtils.color("&cThe lecture '" + lectureName + "' does not exist."));
//                return true;
//            }


            data.set("lectures." + lectureName + ".region", regionName);
            Core.instance().getLectures().save();
            player.sendMessage(ChatUtils.color("&aSuccessfully added '&2" + regionName + "&a' as region to the &2" + lectureName + " &alecture."));
            return true;
        }

        player.sendMessage(ChatUtils.color("&cUsage: /region <add, list> [region] [lecture]"));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return new ArrayList<>();
        }

        ArrayList<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.addAll(List.of("list", "add"));
        }

        if (args.length >= 2) {
            if (args[0].equals("add")) {
                World world = wg.getPlatform().getMatcher().getWorldByName(player.getWorld().getName());
                RegionManager regions = container.get(world);
                ConfigurationSection lecturesSection = data.getConfigurationSection("lectures");

                if (regions == null) {
                    completions.add("");
                    return completions;
                }

                if (lecturesSection == null) {
                    completions.add("");
                    return completions;
                }

                Set<String> lectures = lecturesSection.getKeys(false);

                if (args.length == 2) {
                    completions.addAll(regions.getRegions().keySet());
                }
                if (args.length == 3) {
                    completions.addAll(lectures);
                }
            }
        }

        return completions;
    }
}