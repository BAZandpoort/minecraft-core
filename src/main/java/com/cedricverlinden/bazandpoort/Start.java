package com.cedricverlinden.bazandpoort;

import com.cedricverlinden.bazandpoort.database.Database;
import com.cedricverlinden.bazandpoort.managers.FileManager;
import com.cedricverlinden.bazandpoort.utils.LoggerUtil;
import com.sk89q.worldguard.WorldGuard;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

/**
 * Initialize everything for the plugin to function
 */
public class Start {

	// instance
	private Start instance;
	private final Database database;

	// files
	private FileManager messages;
	private FileManager lectures;
	private FileManager motd;

	// plugin manager
	PluginManager listenerManager = Bukkit.getPluginManager();

	// API's
	private WorldGuard worldGuard;

	/**
	 * Initiliaze the class and start the plugins' features
	 */
	public Start() {
		LoggerUtil.log("&8&m----------------------------------------");
		LoggerUtil.log("&6Initializing the plugin...");
		database = new Database();
		loadFiles();
		loadApis();
		LoggerUtil.log("&a&lSuccessfully started the plugin!");
		LoggerUtil.log("&8&m----------------------------------------");
	}

	/**
	 * Registers all files through a new {@link FileManager} instance
	 */
	private void loadFiles() {
		messages = new FileManager("messages.yml");
		lectures = new FileManager("data", "lectures.yml");
		motd = new FileManager("features", "motd.yml");
	}

	/**
	 * Registers all needed API's
	 */
	private void loadApis() {
		worldGuard = WorldGuard.getInstance();
	}

	// Getters

	/**
	 *
	 * @return instance of {@link Core}
	 */
	public Start instance() {
		return instance;
	}

	/**
	 *
	 * @return instance of {@link Database}
	 */
	public Database database() {
		return database;
	}

	/**
	 *
	 * @return message {@link FileManager} instance
	 */
	public FileManager getMessages() {
		return messages;
	}

	/**
	 *
	 * @return lecture {@link FileManager} instance
	 */
	public FileManager getLectures() {
		return lectures;
	}

	/**
	 *
	 * @return motd {@link FileManager} instance
	 */
	public FileManager getMotd() {
		return motd;
	}

	/**
	 *
	 * @return worldguard instance
	 */
	public WorldGuard getWorldGuard() {
		return worldGuard;
	}
}