package com.cedricverlinden.bazandpoort.managers;

import com.cedricverlinden.bazandpoort.Core;
import com.cedricverlinden.bazandpoort.database.Database;
import com.cedricverlinden.bazandpoort.utils.ErrorUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class CustomPlayer {

	private final Player player;
	private String customName;
	private int age;

	private final Database database = Core.instance().database();
	PlayerManager playerManager = Core.instance().playerManager();

	public CustomPlayer(Player player, String customName, int age) {
		this.player = player;
		this.customName = customName;
		this.age = age;

		try (PreparedStatement playerSelectStatement = database.run("SELECT * FROM players WHERE playername=?;")) {
			playerSelectStatement.setString(1, player.getName());
			ResultSet resultSet = playerSelectStatement.executeQuery();

			if (!(resultSet.next())) {
				PreparedStatement playerUpdateStatement = database
						.run("INSERT INTO players (playername, name, age) VALUES (?,?,?);");

				playerUpdateStatement.setString(1, player.getName());
				playerUpdateStatement.setString(2, customName);
				playerUpdateStatement.setInt(3, age);

				playerUpdateStatement.executeUpdate();

				playerManager.addPlayer(player, this);
				return;
			}

			this.customName = resultSet.getString("name");
			this.age = resultSet.getInt("age");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// Setters
	/**
	 *
	 * @param customName new custom name
	 */
	public void setCustomName(Player player, String customName) {
		this.customName = customName;

		player.playerListName(Component.text(customName));
		player.displayName(Component.text(customName));

		CompletableFuture.runAsync(() -> {
			try (PreparedStatement statement = database.run("UPDATE players SET name=? WHERE playername=?")) {
				statement.setString(1, customName);
				statement.setString(2, player.getName());
				statement.executeUpdate();
			} catch (SQLException exception) {
				ErrorUtil.handleError("Could not set new custom name for " + player.getName() + ".", exception);
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
				statement.setString(2, player.getName());
				statement.executeUpdate();
			} catch (SQLException exception) {
				ErrorUtil.handleError("Could not set new age for " + player.getName() + ".", exception);
				throw new RuntimeException(exception);
			}
		});
	}


	// Getters
	/**
	 *
	 * @return player
	 */
	public Player getPlayer() {
		return player;
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
