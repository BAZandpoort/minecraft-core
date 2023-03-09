package com.cedricverlinden.bazandpoort.commands.player;

import com.cedricverlinden.bazandpoort.conversations.lectures.computerscience.ComputerScienceConvo;
import com.cedricverlinden.bazandpoort.conversations.lectures.economy.EconomyConvo;
import com.cedricverlinden.bazandpoort.conversations.lectures.math.MathConvo;
import com.cedricverlinden.bazandpoort.conversations.lectures.science.ScienceConvo;
import com.cedricverlinden.bazandpoort.managers.PlayerManager;
import com.cedricverlinden.bazandpoort.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class OefeningCommand implements CommandExecutor, TabCompleter {

	PlayerManager playerManager;

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (!(sender instanceof Player player)) {
			sender.sendMessage(ChatUtil.color("&cYou can only use this command in-game."));
			return true;
		}

		playerManager = PlayerManager.getPlayer(player);

		if (playerManager == null) {
			player.sendMessage(ChatUtil.color((player.isOp() ? "You shouldn't be able to use this command anyway. LOL": "Je moet je eerst registreren.")));
			return true;
		}


		if (playerManager.getCurrentRegion().equals("Hallways")) {
			player.sendMessage(ChatUtil.color("&cJe moet je in een klas begeven om een oefening te starten."));
			return true;
		}

		if (args.length == 0) {
			player.sendMessage(ChatUtil.color("&cGebruik: /lecture <start>"));
			return true;
		}

		String param = args[0].toLowerCase();

		if ("start".equals(param)) {
			if (!(playerManager.getCurrentLecture().equals("Exploring"))) {
				player.sendMessage(ChatUtil.color("&cJe bent al begonnen met een oefening."));
				return true;
			}

			switch (playerManager.getCurrentRegion()) {
				case "economy" -> new EconomyConvo(player).begin();
				case "computerscience" -> new ComputerScienceConvo(player).begin();
				case "science" -> new ScienceConvo(player).begin();
				case "math" -> new MathConvo(player).begin();
				default -> player.sendMessage(ChatUtil.color("&cJe moet je in een klas begeven om een oefening te starten."));
			}

			return true;
		}

//		if ("end".equals(param)) {
//			if (playerManager.getCurrentLecture().equals("Hallways")) {
//				player.sendMessage(ChatUtil.color("&cJe bent nog niet begonnen met een oefening."));
//				return true;
//			}
//
//			// end conversation
//			playerManager.setCurrentLecture("Hallways");
//			player.sendMessage(ChatUtil.color("&cEnding the lecture..."));
//			return true;
//		}

		player.sendMessage(ChatUtil.color("&cUsage: /lecture start"));
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (!(sender instanceof Player)) {
			return new ArrayList<>();
		}

		ArrayList<String> completions = new ArrayList<>();

		if (args.length == 1) {
			completions.add("start");
		}

		return completions;
	}
}
