package com.cedricverlinden.bazandpoort.commands.player;

import com.cedricverlinden.bazandpoort.Core;
import com.cedricverlinden.bazandpoort.utils.ChatUtil;
import com.cedricverlinden.bazandpoort.conversations.lectures.MathConvo;
import com.cedricverlinden.bazandpoort.utils.TempDataUtil;
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

public class LectureCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (!(sender instanceof Player player)) {
			sender.sendMessage(ChatUtil.color("&cYou can only use this command in-game."));
			return true;
		}

		if (!(TempDataUtil.region.containsKey(player))) {
			player.sendMessage(ChatUtil.color("&cYou have to be in a class to start a lecture."));
			return true;
		}

		if (args.length == 0) {
			player.sendMessage(ChatUtil.color("&cUsage: /lecture <start, end>"));
			return true;
		}

		String param = args[0].toLowerCase();

		if ("start".equals(param)) {
			if (TempDataUtil.lecture.contains(player)) {
				player.sendMessage(ChatUtil.color("&cYou have already started a lecture."));
				return true;
			}

			// start conversation
			TempDataUtil.lecture.add(player);
			player.sendMessage(ChatUtil.color("&aStarting the Math lecture..."));

			Bukkit.getScheduler().runTaskLater(Core.core(), () -> {
				new MathConvo(player).begin();
			}, 20);
			return true;
		}

		if ("end".equals(param)) {
			if (!(TempDataUtil.lecture.contains(player))) {
				player.sendMessage(ChatUtil.color("&cYou haven't started any lecture yet."));
				return true;
			}

			// end conversation
			TempDataUtil.lecture.remove(player);
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
