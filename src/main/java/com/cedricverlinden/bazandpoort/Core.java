package com.cedricverlinden.bazandpoort;

import com.cedricverlinden.bazandpoort.commands.admin.RegionCommand;
import com.cedricverlinden.bazandpoort.commands.player.LectureCommand;
import com.cedricverlinden.bazandpoort.listeners.PlayerListener;
import com.cedricverlinden.bazandpoort.listeners.RegionListener;
import com.cedricverlinden.bazandpoort.listeners.ServerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for everything to work
 */
public class Core extends JavaPlugin {

	private static Core core;
	private static Start instance;

	private static final PluginManager listenerManager = Bukkit.getPluginManager();

	/**
	 * Called when starting the plugin
	 */
	@Override
	public void onEnable() {
		core = this;
		instance = new Start();

		loadCommands();
		loadListeners();
	}

	/**
	 * Called when shutting down the plugin
	 */
	@Override
	public void onDisable() {
		new End();
	}

	/**
	 * Registers all commands through Bukkit's getPluginCommand
	 */
	private void loadCommands() {
		getCommand("region").setExecutor(new RegionCommand());
		getCommand("lecture").setExecutor(new LectureCommand());
	}

	/**
	 * Registers all listeners through Bukkit's PluginManager
	 */
	private void loadListeners() {
		listenerManager.registerEvents(new PlayerListener(), Core.core());
		listenerManager.registerEvents(new RegionListener(), Core.core());
		listenerManager.registerEvents(new ServerListener(), Core.core());
	}

	/**
	 *
	 * @return instance of {@link Core}
	 */
	public static Core core() {
		return core;
	}

	/**
	 *
	 * @return instance of {@link Start}
	 */
	public static Start instance() {
		return instance;
	}
}
