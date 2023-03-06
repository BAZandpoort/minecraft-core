package com.cedricverlinden.bazandpoort.database;

import com.cedricverlinden.bazandpoort.Core;
import com.cedricverlinden.bazandpoort.managers.PlayerManager;
import com.cedricverlinden.bazandpoort.utils.ChatUtil;
import com.cedricverlinden.bazandpoort.utils.ErrorUtil;
import com.cedricverlinden.bazandpoort.utils.LoggerUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;

/**
 * Everything for database management
 */
public class Database {

	private final Connection connection;
	PlayerManager playerManager = Core.instance().playerManager();

	/**
	 * Constructor for connecting to the database
	 */
	public Database() {
		try {
			String HOST = "lin-17544-10111-mysql-primary.servers.linodedb.net";
			String DATABASE = "bazandpoort";
			String USERNAME = "linroot";
			String PASSWORD = "2l395YaLc8l!bqLy";
			connection = DriverManager.getConnection(
					"jdbc:mysql://" + HOST + ":3306/" + DATABASE + "?useSSL=true", USERNAME, PASSWORD);

			LoggerUtil.log("Successfully connected to the database.");
		} catch (SQLException exception) {
			ErrorUtil.handleError("Could not connect to the database.", exception);
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Tries to disconnect from the connected database
	 */
	public void disconnect() {
		if (!(connection == null)) {
			try {
				connection.close();
			} catch (SQLException exception) {
				ErrorUtil.handleError("Could not disconnect from the database.", exception);
				throw new RuntimeException(exception);
			}
		}
	}

	/**
	 *
	 * @param query the query that needs to be run
	 * @return a prepared statement from the query
	 */
	public PreparedStatement run(String query) {
		try {
			return connection.prepareStatement(query);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public PlayerManager getPlayer(Player player) {
		String name = player.getName();
		try {
			PreparedStatement preparedStatement = this.run("SELECT * FROM players WHERE playername=?");
			preparedStatement.setString(1, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String customName = resultSet.getString("name");
				int age = resultSet.getInt("age");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

	public void resetPlayer(Player player) {
		try {
			PreparedStatement preparedStatement = this.run("DELETE FROM players WHERE playername=?;");
			preparedStatement.setString(1, player.getName());
			preparedStatement.executeUpdate();
			Core.instance().playerManager().removePlayer(player);
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void resetPlayers() {
		try {
			this.run("TRUNCATE TABLE players;").executeUpdate();
			Core.instance().playerManager().resetPlayers();
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!(player.isOp())) {
					player.kick(Component.text(ChatUtil.color("De server is gereset!")));
				}
			}
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}
}
