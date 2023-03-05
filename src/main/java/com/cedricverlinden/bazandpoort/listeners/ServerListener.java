package com.cedricverlinden.bazandpoort.listeners;

import com.cedricverlinden.bazandpoort.Core;
import com.cedricverlinden.bazandpoort.utils.ChatUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListener implements Listener {

	YamlConfiguration motd = Core.instance().getMotd().getEditableFile();

	@EventHandler
	public void onServerListPing(ServerListPingEvent event) {
		event.setMaxPlayers(event.getNumPlayers()+1);

		String motdMessage = motd.getString("motd");
		event.motd(Component.text(ChatUtils.color(
				(motdMessage == null) ? "&4&l&nNO MOTD SET!\n&4&l&nPLEASE FIX THIS" : motdMessage)));
	}
}
