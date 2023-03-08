package com.cedricverlinden.bazandpoort.listeners;

import com.cedricverlinden.bazandpoort.Core;
import com.cedricverlinden.bazandpoort.managers.PlayerManager;
import com.cedricverlinden.bazandpoort.utils.ChatUtil;
import de.netzkronehd.wgregionevents.events.RegionEnteredEvent;
import de.netzkronehd.wgregionevents.events.RegionLeftEvent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Set;

public class RegionListener implements Listener {

	YamlConfiguration data = Core.instance().getLectures().getEditableFile();
	PlayerManager playerManager;

	@EventHandler
	public void onRegionEntered(RegionEnteredEvent event) {
		Player player = event.getPlayer();

		playerManager = PlayerManager.getPlayer(player);
		if (playerManager == null) {
			return;
		}

		ConfigurationSection lecturesSection = data.getConfigurationSection("lectures");
		if (lecturesSection == null) {
			return;
		}

		Set<String> lectures = lecturesSection.getKeys(false);
		String lectureName = null;

		for (String lecture : lectures) {
			ConfigurationSection lectureId = data.getConfigurationSection("lectures." + lecture);
			if (event.getRegion().getId().equals(lectureId.get("region"))) {
				lectureName = lectureId.getString("name");
				break;
			}
		}

		if (lectureName == null) {
			player.sendMessage(ChatUtil.color("This region is not used by any lecture."));
			return;
		}

		playerManager.setCurrentRegion(event.getRegion().getId());
		player.sendMessage(ChatUtil.color("&aYou have entered the &2" + lectureName + " &aclass."));
	}

	@EventHandler
	public void onRegionLeave(RegionLeftEvent event) {
		Player player = event.getPlayer();

		playerManager = PlayerManager.getPlayer(player);
		if (playerManager == null) {
			return;
		}

		ConfigurationSection lecturesSection = data.getConfigurationSection("lectures");
		if (lecturesSection == null) {
			return;
		}

		Set<String> lectures = lecturesSection.getKeys(false);
		String lectureName = null;

		for (String lecture : lectures) {
			ConfigurationSection lectureId = data.getConfigurationSection("lectures." + lecture);
			if (event.getRegion().getId().equals(lectureId.get("region"))) {
				lectureName = lectureId.getString("name");
				break;
			}
		}

		if (lectureName == null) {
			player.sendMessage(ChatUtil.color("This region is not used by any lecture."));
			return;
		}

		playerManager.setCurrentRegion("NULL");
		player.sendMessage(ChatUtil.color("&cYou have left the &4" + lectureName + " &cclass."));
	}
}
