package com.cedricverlinden.bazandpoort.utils;

import org.bukkit.Bukkit;

public class LoggerUtil {

	/**
	 *
	 * @param message message to log to console
	 */
	public static void log(String message) {
		Bukkit.getConsoleSender().sendMessage(ChatUtil.color(message));
	}

	/**
	 *
	 * @param message message to log to console when an error occured
	 */
	public static void logInfo(String message) {
		log("&9[INFO] &b" + message);
	}

	/**
	 *
	 * @param message message to log to console when an error occured
	 */
	public static void logError(String message) {
		log("&4[ERROR] &c" + message);
	}
}
