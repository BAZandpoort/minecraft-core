package com.cedricverlinden.bazandpoort.managers;

import com.cedricverlinden.bazandpoort.Core;
import com.cedricverlinden.bazandpoort.database.Database;
import com.cedricverlinden.bazandpoort.utils.ErrorUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class CustomPlayer {

	private final String playerName;
	private String customName;
	private int age;

	private final Database database = Core.instance().database();

	public CustomPlayer(String playerName, String customName, int age) {
		this.playerName = playerName;
		this.customName = customName;
		this.age = age;

		try (PreparedStatement playerSelectStatement = database.run("SELECT * FROM players WHERE playername=?;")) {
			playerSelectStatement.setString(1, playerName);
			ResultSet resultSet = playerSelectStatement.executeQuery();

			if (!(resultSet.next())) {
				PreparedStatement playerUpdateStatement = database
						.run("INSERT INTO players (playername, name, age) VALUES (?,?,?);");

				playerUpdateStatement.setString(1, playerName);
				playerUpdateStatement.setString(2, customName);
				playerUpdateStatement.setInt(3, age);

				playerUpdateStatement.executeUpdate();
				return;
			}

			customName = resultSet.getString("name");
			age = resultSet.getInt("age");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// Setters
	/**
	 *
	 * @param customName new custom name
	 */
	public void setCustomName(String customName) {
		this.customName = customName;

		CompletableFuture.runAsync(() -> {
			try (PreparedStatement statement = database.run("UPDATE players SET name=? WHERE playername=?")) {
				statement.setString(1, customName);
				statement.setString(2, playerName);
				statement.executeUpdate();
			} catch (SQLException exception) {
				ErrorUtil.handleError("Could not set new custom name for " + playerName + ".", exception);
				throw new RuntimeException(exception);
			}
		});
	}

	/**
	 *
	 * @param age new age
	 */
	public void setAge(int age) {
		this.age = age;

		CompletableFuture.runAsync(() -> {
			try (PreparedStatement statement = database.run("UPDATE players SET age=? WHERE playername=?")) {
				statement.setInt(1, age);
				statement.setString(2, playerName);
				statement.executeUpdate();
			} catch (SQLException exception) {
				ErrorUtil.handleError("Could not set new age for " + playerName + ".", exception);
				throw new RuntimeException(exception);
			}
		});
	}


	// Getters
	/**
	 *
	 * @return player name
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 *
	 * @return custom name
	 */
	public String getCustomName() {
		return customName;
	}

	/**
	 *
	 * @return age
	 */
	public int getAge() {
		return age;
	}
}
