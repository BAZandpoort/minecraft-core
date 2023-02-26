package com.cedricverlinden.bazandpoort.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

// Using deprecated because component does not remove color codes properly in console.
public class PlayerListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&f[&a+&f] &a" + player.getName() + " has joined the server!"));
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&f[&c-&f] &c" + player.getName() + " has left the server!"));
	}
}
