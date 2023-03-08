package com.cedricverlinden.bazandpoort.listeners;

import com.cedricverlinden.bazandpoort.Core;
import com.cedricverlinden.bazandpoort.managers.PlayerManager;
import com.cedricverlinden.bazandpoort.utils.ChatUtil;
import com.cedricverlinden.bazandpoort.conversations.initial.InitialConvo;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

// Using deprecated setQuitMessage method because component does not remove color codes properly in console.
public class PlayerListener implements Listener {

	PlayerManager playerManager;
	YamlConfiguration messages = Core.instance().getMessages().getEditableFile();

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.joinMessage(null);

		if (!(player.isOp()) && (PlayerManager.getPlayer(player) == null)) {
			player.sendMessage(ChatUtil.color("&8[&cOMROEP&8] &2Hey, welkom op het BA Zandpoort Minecraft netwerk, " +
					"voor we beginnen hebben wij een aantal vragen voor je."));

			Bukkit.getScheduler().runTaskLater(Core.core(), () -> new InitialConvo(player).begin(), 20);
			return;
		}

		playerManager = PlayerManager.getPlayer(player);
		event.setJoinMessage(ChatUtil.color(messages.getString("join-message").replace("$player", playerManager.getCustomName())));
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		playerManager = PlayerManager.getPlayer(player);
		if (playerManager == null) {
			event.quitMessage(null);
			return;
		}

		Bukkit.getScheduler().runTaskLater(Core.core(), () -> {
			if (!player.isOnline()) {
				playerManager.resetPlayer();
			}
		}, 300 * 20L);

		String customName = playerManager.getCustomName();
		String quitMessage = messages.getString("quit-message");
		event.setQuitMessage(ChatUtil.color((quitMessage == null) ? "" : quitMessage)
				.replace("$player", customName));
	}
}
