package com.cedricverlinden.bazandpoort.utils;

import com.cedricverlinden.bazandpoort.Core;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

public class ErrorUtil {

	/**
	 *
	 * @param exception {@link Exception} to log to file
	 */
	public static void handleError(@NotNull Exception exception) {
		File dataFolder = Core.core().getDataFolder();

		Calendar calendar = Calendar.getInstance();
		String date = calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + " | " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);

		try (FileWriter output = new FileWriter(dataFolder + "errors.txt")) {
			output.write("----------------------------------------\n");
			output.write(date + ":\n");
			output.write("");
			output.write(exception.getMessage() + "\n\n");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 *
	 * @param message message to send to thr console when an error occured
	 * @param exception {@link Exception} to log to file
	 */
	public static void handleError(@NotNull String message, @NotNull Exception exception) {
		LoggerUtil.logError(message);

		File dataFolder = Core.core().getDataFolder();
		Calendar calendar = Calendar.getInstance();
		String date = calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH)+1 + "-" + calendar.get(Calendar.HOUR_OF_DAY) + "-" + calendar.get(Calendar.MINUTE) + "-" + calendar.get(Calendar.SECOND);
		String dateInFile = calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH)+1 + " | " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);

		try (FileWriter output = new FileWriter(dataFolder +
				"/errors/errors-" + date + ".txt")) {
			output.append("Time of error: ").append(date).append("\n\n");
			output.append("Caused by: ").append(String.valueOf(exception.getCause())).append("\n");
			output.append("Stacktrace: ").append(Arrays.toString(exception.getStackTrace())
					.replace(", ", ",\n")).append("\n");

			output.append("----------------------------------------\n\n");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
