package com.cedricverlinden.bazandpoort.listeners;

import com.cedricverlinden.bazandpoort.Core;
import com.cedricverlinden.bazandpoort.Utils;
import com.cedricverlinden.bazandpoort.conversations.initial.InitialConvo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

// Using deprecated methods because component does not remove color codes properly in console.
public class PlayerListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&f[&a+&f] &a" + player.getName() + " has joined the server!"));

		Bukkit.getScheduler().runTaskLater(Core.instance(), () -> {
			player.sendMessage(Utils.color("&8[&cOMROEP&8] &2Hey, welkom op het BA Zandpoort Minecraft netwerk, voor we beginnen hebben wij een aantal vragen voor je."));
		}, 20);

		Bukkit.getScheduler().runTaskLater(Core.instance(), () -> new InitialConvo(player).begin(), 40);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&f[&c-&f] &c" + player.getName() + " has left the server!"));
	}
}
