package com.cedricverlinden.bazandpoort.utils;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class ChatUtil {

	/**
	 * Turns string into a colorized string
	 * @param message message to colorize
	 * @return colorized message
	 */
	public static @NotNull String color(@NotNull String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
}
