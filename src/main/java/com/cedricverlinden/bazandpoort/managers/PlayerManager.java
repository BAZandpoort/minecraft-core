package com.cedricverlinden.bazandpoort.managers;

import com.cedricverlinden.bazandpoort.Core;
import com.cedricverlinden.bazandpoort.database.Database;
import com.cedricverlinden.bazandpoort.utils.ChatUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerManager {

	private static final HashMap<Player, CustomPlayer> players = new HashMap<>();
	Database database = Core.instance().database();

	public CustomPlayer getCustomPlayer(Player player) {
		return players.get(player);
	}

	public boolean isPlayer(Player player) {
		database.getPlayer(player);
	}

	public void addPlayer(Player player, CustomPlayer customPlayer) {
		players.put(player, customPlayer);
	}

	public void removePlayer(Player player) {
		players.remove(player);
		player.kick(Component.text(ChatUtil.color("Je bent gereset!")));
	}

	public void resetPlayers() {
		players.clear();
	}
}
