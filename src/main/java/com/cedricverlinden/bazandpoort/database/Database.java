package com.cedricverlinden.bazandpoort.database;

import com.cedricverlinden.bazandpoort.utils.ErrorUtil;
import com.cedricverlinden.bazandpoort.utils.LoggerUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
			String HOST = "lin-17544-10111-mysql-primary.servers.linodedb.net";
			String DATABASE = "bazandpoort";
			String USERNAME = "linroot";
			String PASSWORD = "2l395YaLc8l!bqLy";
			connection = DriverManager.getConnection(
					"jdbc:mysql://" + HOST + ":3306/" + DATABASE + "?useSSL=true", USERNAME, PASSWORD);

			LoggerUtils.log("Successfully connected to the database.");
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
}
