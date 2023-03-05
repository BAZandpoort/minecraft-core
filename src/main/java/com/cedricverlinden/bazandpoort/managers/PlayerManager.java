package com.cedricverlinden.bazandpoort.managers;

import java.util.HashMap;

public class PlayerManager {

	private static final HashMap<String, CustomPlayer> players = new HashMap<>();

	public CustomPlayer getCustomPlayer(String name) {
		return players.get(name);
	}

	public void addCustomPlayer(String name, CustomPlayer customPlayer) {
		players.put(name, customPlayer);
	}
}
