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

		if (Objects.requireNonNull(motd.getString("motd")).isEmpty()) {
			event.motd(Component.text(ChatColor.translateAlternateColorCodes('&', "")));
			return;
		}

		event.motd(Component.text(ChatColor.translateAlternateColorCodes('&',
				"&r   &7[NIEUW] &2&lHET BAZANDPOORT NETWERK &7[1.19.3]\n" +
						"&r              &8&m-&7&m-&f De &a#1 &feducatie server! &7&m-&8&m-")));
	}
}
