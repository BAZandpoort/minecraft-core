package com.cedricverlinden.bazandpoort.listeners;

import com.cedricverlinden.bazandpoort.Core;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.Objects;

public class ServerListener implements Listener {

	YamlConfiguration motd = Core.getMotd().getEditableFile();

	@EventHandler
	public void onServerListPing(ServerListPingEvent event) {
		event.setMaxPlayers(event.getNumPlayers()+1);

		event.motd(Component.text(ChatColor.translateAlternateColorCodes('&',
				Objects.requireNonNull(motd.getString("motd")))));
	}
}
