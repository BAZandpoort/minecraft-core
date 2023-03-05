package com.cedricverlinden.bazandpoort.managers;

import com.cedricverlinden.bazandpoort.Core;
import com.cedricverlinden.bazandpoort.utils.ErrorUtil;
import com.cedricverlinden.bazandpoort.utils.LoggerUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class FileManager {

	private final File file;
	private final YamlConfiguration editableFile;

	private final File dataFolder = Core.core().getDataFolder();

	public FileManager(@NotNull String path, @NotNull String fileName) {
		file = new File(dataFolder + File.separator + path.replace("/", File.separator), fileName);
		boolean madeDirs = file.getParentFile().mkdirs();

		if (!(madeDirs)) {
			LoggerUtils.logInfo("Directory for " + file.getName() + " is already created.");
		}

		if (!(file.exists())) {
			Core.core().saveResource(path.replace("/", File.separator) + File.separator  + fileName, false);
		}

		editableFile = YamlConfiguration.loadConfiguration(file);
	}

	public FileManager(@NotNull String fileName) {
		file = new File(dataFolder, fileName);
		boolean madeDirs = file.getParentFile().mkdirs();

		if (!(madeDirs)) {
			LoggerUtils.logInfo("Directory for " + file.getName() + " is already created.");
		}

		if (!(file.exists())) {
			Core.core().saveResource(fileName, false);
		}

		editableFile = YamlConfiguration.loadConfiguration(file);
	}

	public YamlConfiguration getEditableFile() {
		return editableFile;
	}

	public void save() {
		try {
			editableFile.save(file);
		} catch (IOException exception) {
			ErrorUtil.handleError("&cCould not save " + file.getName(), exception);
		}
	}
}
