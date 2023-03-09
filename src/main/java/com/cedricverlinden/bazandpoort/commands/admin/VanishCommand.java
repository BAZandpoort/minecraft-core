package com.cedricverlinden.bazandpoort.commands.admin;

import com.cedricverlinden.bazandpoort.Core;
import com.cedricverlinden.bazandpoort.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class VanishCommand implements CommandExecutor {

	public static ArrayList<Player> vanished = new ArrayList<>();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage(ChatUtil.color("&cYou can only use this command in-game."));
			return true;
		}

		if (vanished.contains(player)) {
			player.sendMessage(ChatUtil.color("&cYou have disabled vanish"));
			player.showPlayer(Core.core(), player);
			vanished.remove(player);
		} else {
			player.sendMessage(ChatUtil.color("&aYou have enabled vanish"));
			player.hidePlayer(Core.core(), player);
			vanished.add(player);
		}

		return true;
	}
}