package com.cedricverlinden.bazandpoort.listeners;

import com.cedricverlinden.bazandpoort.Core;
import com.cedricverlinden.bazandpoort.utils.ChatUtil;
import com.cedricverlinden.bazandpoort.utils.TempDataUtil;
import com.sk89q.worldguard.WorldGuard;
import de.netzkronehd.wgregionevents.events.RegionEnteredEvent;
import de.netzkronehd.wgregionevents.events.RegionLeftEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Set;

public class RegionListener implements Listener {

	WorldGuard wg = Core.instance().getWorldGuard();

//	Connection database = Core.getConnection().getConnection();
	YamlConfiguration data = Core.instance().getLectures().getEditableFile();

	String lectureNameId = null;
	@EventHandler
	public void onRegionEntered(RegionEnteredEvent event) {
		Player player = event.getPlayer();


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
				lectureNameId = lecture;
				break;
			}
		}

		if (lectureName == null) {
			player.sendMessage(ChatUtil.color("This region is not used by any lecture."));
			return;
		}

//		try {
//			PreparedStatement classes = database.prepareStatement("SELECT * FROM classes WHERE class=?");
//			classes.setString(1, lectureNameId);
//			ResultSet rs = classes.executeQuery();
//			if (!(rs.next())) {
//				PreparedStatement ps = database.prepareStatement("INSERT INTO classes(class,current,used) VALUES(?,?,?);");
//				ps.setString(1, lectureNameId);
//				ps.setInt(2, 1);
//				ps.setInt(3, 1);
//				ps.executeUpdate();
//			} else {
//				PreparedStatement ps = database.prepareStatement("UPDATE classes SET current=?,used=? WHERE class=?");
//				int current = rs.getInt("current");
//				int used = rs.getInt("used");
//				ps.setInt(1, current+1);
//				ps.setInt(2, used+1);
//				ps.setString(3, lectureNameId);
//				ps.executeUpdate();
//			}
//
//
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}

		TempDataUtil.region.put(player, event.getRegion().getId());
		player.sendMessage(ChatUtil.color("&aYou have entered the &2" + lectureName + " &aclass."));

		Bukkit.getScheduler().runTaskLater(Core.core(), () -> {
			player.sendMessage(ChatUtil.color("Type this to start..."));
		}, 20);
	}

	@EventHandler
	public void onRegionLeave(RegionLeftEvent event) {
		Player player = event.getPlayer();

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
				lectureNameId = lecture;
				break;
			}
		}

		if (lectureName == null) {
			player.sendMessage(ChatUtil.color("This region is not used by any lecture."));
			return;
		}

//		try {
//			PreparedStatement classes = database.prepareStatement("SELECT * FROM classes WHERE class=?");
//			classes.setString(1, lectureNameId);
//			ResultSet rs = classes.executeQuery();
//			if (!(rs.next())) {
//				PreparedStatement ps = database.prepareStatement("INSERT INTO classes(class,current,used) VALUES(?,?,?);");
//				ps.setString(1, lectureNameId);
//				ps.setInt(2, 0);
//				ps.setInt(3, 1);
//				ps.executeUpdate();
//			} else {
//				PreparedStatement ps = database.prepareStatement("UPDATE classes SET current=? WHERE class=?");
//				int current = rs.getInt("current");
//				int used = rs.getInt("used");
//				ps.setInt(1, current-1);
//				ps.setString(2, lectureNameId);
//				ps.executeUpdate();
//			}
//
//
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}

		TempDataUtil.region.remove(player);
	}
}
