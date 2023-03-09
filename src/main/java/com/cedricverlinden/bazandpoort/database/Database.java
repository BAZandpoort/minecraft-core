package com.cedricverlinden.bazandpoort.database;

import com.cedricverlinden.bazandpoort.utils.ErrorUtil;
import com.cedricverlinden.bazandpoort.utils.LoggerUtil;

import java.sql.*;

/**
 * Everything for database management
 */
public class Database {

	private final Connection connection;

	/**
	 * Constructor for connecting to the database
	 */
	public Database() {
		try {
			String HOST = "lin-17544-10111-mysql-primary.servers.linodedb.net"; // 127.0.0.1
			String DATABASE = "bazandpoort"; // minecraft
			String USERNAME = "linroot"; // root
			String PASSWORD = "2l395YaLc8l!bqLy"; // Fruitsla!123
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

	/**
	 * Reset all player data
	 */
	public void resetAll() {
		String sql = "TRUNCATE TABLE players";

		try (PreparedStatement statement = run(sql)) {
			statement.executeUpdate();
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void playerJoin() {
		String sql = "UPDATE server SET online=online+?;";

		try (PreparedStatement statement = run(sql)) {
			statement.setInt(1, 1);
			statement.executeUpdate();
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void playerLeave() {
		String sql = "UPDATE server SET online=online-?;";

		try (PreparedStatement statement = run(sql)) {
			statement.setInt(1, 1);
			statement.executeUpdate();
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}



	public void resetOnlinePlayers() {
		String sql = "UPDATE server SET online=?;";

		try (PreparedStatement statement = run(sql)) {
			statement.setInt(1, 0);
			statement.executeUpdate();
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}
}
