package com.cedricverlinden.bazandpoort.commands.admin;

import com.cedricverlinden.bazandpoort.Core;
import com.cedricverlinden.bazandpoort.database.Database;
import com.cedricverlinden.bazandpoort.managers.PlayerManager;
import com.cedricverlinden.bazandpoort.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlayerManagerCommand implements CommandExecutor, TabCompleter {

	private final Database database = Core.instance().database();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage(ChatUtil.color("&cYou can only use this command in-game."));
			return true;
		}

		String usage = "&cGebruik: /playermanager <playerName, resetall> [info, set, reset] [customName, age]";

		if (args.length == 0) {
			player.sendMessage(ChatUtil.color(usage));
			return true;
		}

		if (args[0].equalsIgnoreCase("resetall")) {
			database.resetAll();
			player.sendMessage(ChatUtil.color("&8[&6&lPM&8] &aSuccesvol alle spelersgegevens gereset."));
			return true;
		}

		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			player.sendMessage(ChatUtil.color("&cDeze speler bestaat niet of is niet online."));
			return true;
		}

		PlayerManager playerManager = PlayerManager.getPlayer(player);
		if (playerManager == null) {
			player.sendMessage(ChatUtil.color("&cDeze speler heeft nog geen profiel aangemaakt."));
			return true;
		}

		if (args.length == 1) {
			player.sendMessage(ChatUtil.color(usage));
			return true;
		}

		if (args[1].equalsIgnoreCase("info")) {
			player.sendMessage(ChatUtil.color("&8&m----------------------------------------"));
			player.sendMessage(ChatUtil.color("&2Spelerinformatie voor &a" + playerManager.getPlayerName()));
			player.sendMessage(ChatUtil.color("&r"));
			player.sendMessage(ChatUtil.color("&fSpeler naam: &a" + playerManager.getPlayerName()));
			player.sendMessage(ChatUtil.color("&fEigen naam: &a" + playerManager.getCustomName()));
			player.sendMessage(ChatUtil.color("&fLeeftijd: &a" + playerManager.getAge()));
			player.sendMessage(ChatUtil.color("&8&m----------------------------------------"));
			return true;
		}

		if (args[1].equalsIgnoreCase("set") && args.length == 4) {
			if (args[2].equalsIgnoreCase("name")) {
				String oldName = playerManager.getCustomName();
				playerManager.setCustomName(args[3]);
				player.sendMessage(ChatUtil.color("&8[&6&lPM&8] &aGeüpdatete naar voor &2" + target.getName() +
						"&a (" + oldName + ") naar &2" + playerManager.getCustomName() + "&a."));
				return true;
			}

			if (args[2].equalsIgnoreCase("age")) {
				playerManager.setAge(Integer.parseInt(args[3]));
				player.sendMessage(ChatUtil.color("&8[&6&lPM&8] &aGeüpdatete leeftijd voor &2" + target.getName() +
						"&a (" + playerManager.getCustomName() + ") naar &2" + playerManager.getAge() + "."));
				return true;
			}

			player.sendMessage(ChatUtil.color(usage));
			return true;
		}

		if (args[1].equalsIgnoreCase("reset")) {
			playerManager.resetPlayer();
			player.sendMessage(ChatUtil.color("&8[&6&lPM&8] &aSuccesvol alle spelergegevens verwijderd."));
			return true;
		}

		player.sendMessage(ChatUtil.color(usage));
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!(sender instanceof Player player)) {
			return new ArrayList<>();
		}

		List<String> completions = new ArrayList<>();

		switch (args.length) {
			case 1 -> {
				completions.add("resetall");
				for (Player players : Bukkit.getOnlinePlayers()) {
					String name = players.getName();
					if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
						completions.add(name);
					}
				}
			}
			case 2 -> {
				if (!(args[0].equalsIgnoreCase("resetall"))) {
					completions.addAll(List.of("info", "set", "reset"));
				}
			}
			case 3 -> {
				if (args[1].equalsIgnoreCase("set")) {
					completions.addAll(List.of("name", "age"));
				}
			}
		}

		return completions;
	}
}
