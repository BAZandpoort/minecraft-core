package com.cedricverlinden.bazandpoort.commands.player;

import com.cedricverlinden.bazandpoort.conversations.lectures.computerscience.ComputerScienceConvo;
import com.cedricverlinden.bazandpoort.conversations.lectures.math.MathConvo;
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

public class LectureCommand implements CommandExecutor, TabCompleter {

	PlayerManager playerManager;

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (!(sender instanceof Player player)) {
			sender.sendMessage(ChatUtil.color("&cYou can only use this command in-game."));
			return true;
		}

		playerManager = PlayerManager.getPlayer(player);

		if (playerManager == null) {
			player.sendMessage(ChatUtil.color((player.isOp() ? "You shouldn't be able to use this command anyway.": "You first have to register.")));
			return true;
		}


		if (playerManager.getCurrentRegion().equals("Hallways")) {
			player.sendMessage(ChatUtil.color("&cYou have to be in a class to start a lecture."));
			return true;
		}

		if (args.length == 0) {
			player.sendMessage(ChatUtil.color("&cUsage: /lecture <start, end>"));
			return true;
		}

		String param = args[0].toLowerCase();

		if ("start".equals(param)) {
			if (!(playerManager.getCurrentLecture().equals("Exploring"))) {
				player.sendMessage(ChatUtil.color("&cYou have already started a lecture."));
				return true;
			}

			switch (playerManager.getCurrentRegion()) {
				case "economy" -> player.sendMessage(ChatUtil.color("ECONOMY -> WIP"));
				case "computerscience" -> new ComputerScienceConvo(player).begin();
				case "science" -> player.sendMessage(ChatUtil.color("SCIENCE -> WIP"));
				case "math" -> new MathConvo(player).begin();
				default -> player.sendMessage(ChatUtil.color("You have to be in a class to start a lecture."));
			}

			return true;
		}

		if ("end".equals(param)) {
			if (playerManager.getCurrentLecture().equals("Hallways")) {
				player.sendMessage(ChatUtil.color("&cYou haven't started any lecture yet."));
				return true;
			}

			// end conversation
			playerManager.setCurrentLecture("Hallways");
			player.sendMessage(ChatUtil.color("&cEnding the lecture..."));
			return true;
		}

		player.sendMessage(ChatUtil.color("&cUsage: /lecture <start, end>"));
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (!(sender instanceof Player)) {
			return new ArrayList<>();
		}

		ArrayList<String> completions = new ArrayList<>();

		if (args.length == 1) {
			completions.addAll(List.of("start", "stop"));
		}

		return completions;
	}
}
