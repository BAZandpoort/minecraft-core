package com.cedricverlinden.bazandpoort.managers;

import com.cedricverlinden.bazandpoort.Core;
import com.cedricverlinden.bazandpoort.database.Database;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerManager {

	private final Database database = Core.instance().database();

	private static final List<PlayerManager> playerList = new ArrayList<>();

	private final Player player;
	private final String playerName;
	private String customName;
	private int age;

	private String currentRegion;
	private String currentLecture;

	public PlayerManager(Player player, String customName, int age) {

		this.player = player;

		this.playerName = player.getName();
		this.customName = customName;
		this.age = age;

		this.currentRegion = "Hallways";
		this.currentLecture = "Exploring";
		ResultSet resultSet = getPlayerData();

		try {
			if (resultSet.next()) {
				this.customName = resultSet.getString("customname");
				this.age = resultSet.getInt("age");
				this.currentRegion = resultSet.getString("currentregion");
				this.currentLecture = resultSet.getString("currentlecture");
			} else {
				createPlayer();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		playerList.add(this);
	}

	// Getter
	public Player getPlayer() {
		return player;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getCustomName() {
		return customName;
	}

	public int getAge() {
		return age;
	}

	public String getCurrentRegion() {
		return currentRegion;
	}

	public String getCurrentLecture() {
		return currentLecture;
	}

	public static PlayerManager getPlayer(Player player) {
		for (PlayerManager playerManager : playerList) {
			if (playerManager.getPlayerName().equals(player.getName())) {
				return playerManager;
			}
		}

		return null;
	}

	private ResultSet getPlayerData() {
		String sql = "SELECT * FROM players WHERE playername=?;";

		PreparedStatement statement = database.run(sql);
		try {
			statement.setString(1, playerName);
			return statement.executeQuery();
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}


	// Setter
	public void setCustomName(String customName) {
		this.customName = customName;

		String sql = "UPDATE players SET customname=? WHERE playername=?;";
		try (PreparedStatement statement = database.run(sql)) {
			statement.setString(1, customName);
			statement.setString(2, playerName);
			statement.executeUpdate();
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setAge(int age) {
		this.age = age;

		String sql = "UPDATE players SET age=? WHERE playername=?;";
		try (PreparedStatement statement = database.run(sql)) {
			statement.setInt(1, age);
			statement.setString(2, playerName);
			statement.executeUpdate();
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setCurrentRegion(String currentRegion) {
		this.currentRegion = currentRegion;

		String sql = "UPDATE players SET currentregion=? WHERE playername=?;";
		try (PreparedStatement statement = database.run(sql)) {
			statement.setString(1, (currentRegion.equals("NULL")) ? "Hallways" : currentRegion);
			statement.setString(2, playerName);
			statement.executeUpdate();
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setCurrentLecture(String currentLecture) {
		this.currentLecture = currentLecture;

		String sql = "UPDATE players SET currentlecture=? WHERE playername=?;";
		try (PreparedStatement statement = database.run(sql)) {
			statement.setString(1, currentLecture);
			statement.setString(2, playerName);
			statement.executeUpdate();
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}

	private void createPlayer() {
		String sql = "INSERT INTO players(id, playername, customname, age, currentregion, currentlecture) " +
				"VALUES (default,?,?,?,?,?);";
		try (PreparedStatement statement = database.run(sql)) {
			statement.setString(1, playerName);
			statement.setString(2, customName);
			statement.setInt(3, age);
			statement.setString(4, currentRegion);
			statement.setString(5, currentLecture);
			statement.executeUpdate();
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}

	// Other
	public void resetPlayer() {
		String sql = "DELETE FROM players WHERE playername=?;";

		try (PreparedStatement statement = database.run(sql)) {
			statement.setString(1, playerName);
			statement.executeUpdate();
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}
}