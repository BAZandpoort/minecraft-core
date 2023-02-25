package com.cedricverlinden.bazandpoort.managers;

import com.cedricverlinden.bazandpoort.Core;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

	private final String path;
	private final String fileName;

	private final File file;
	private final YamlConfiguration editableFile;

	public FileManager(String path, String fileName) {
		this.path = path;
		this.fileName = fileName;

		file = new File(Core.instance().getDataFolder() + File.separator + path.replace("/", File.separator), fileName);
		file.getParentFile().mkdirs();

		if (!(file.exists())) {
			Core.instance().saveResource(path.replace("/", File.separator) + File.separator  + fileName, false);
		}

		editableFile = YamlConfiguration.loadConfiguration(file);
	}

	public FileManager(String fileName) {
		this.fileName = fileName;
		this.path = "";

		file = new File(Core.instance().getDataFolder(), fileName);
		file.getParentFile().mkdirs();

		if (!(file.exists())) {
			Core.instance().saveResource(fileName, false);
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
			exception.printStackTrace();
		}
	}
}
