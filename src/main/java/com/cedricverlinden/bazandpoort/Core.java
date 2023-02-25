package com.cedricverlinden.bazandpoort;

import com.cedricverlinden.bazandpoort.commands.TestCommand;
import com.cedricverlinden.bazandpoort.listeners.PlayerListener;
import com.cedricverlinden.bazandpoort.listeners.ServerListener;
import com.cedricverlinden.bazandpoort.managers.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

	private static Core instance;

	private PluginManager pluginManager = Bukkit.getPluginManager();

	private static FileManager motd;

	@Override
	public void onEnable() {
		instance = this;
		loadFiles();
		loadCommands();
		loadListeners();
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

	private void loadFiles() {
		motd = new FileManager("features", "motd.yml");
	}

	private void loadCommands() {
		getCommand("test").setExecutor(new TestCommand());
	}

	private void loadListeners() {
		pluginManager.registerEvents(new PlayerListener(), this);
		pluginManager.registerEvents(new ServerListener(), this);
	}

	public static Core instance() {
		return instance;
	}

	public static FileManager getMotd() {
		return motd;
	}
}
