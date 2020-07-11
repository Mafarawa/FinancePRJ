package com.mafarawa.connect;

import java.sql.*;

public class DBGate {
	private static DBGate instance;
	private Connection database;

	private DBGate() {}

	public static synchronized DBGate getInstance() {
		if(instance == null) {
			instance = new DBGate();
		}

		return instance;
	}

	public Connection getDatabase() {
		if(database == null) {
			try {
				database = DriverManager.getConnection(
						"jdbc:postgresql://localhost/financeprj_db",
						"gosha",
						"mafarawa228"
				);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		return database;
	}

	public ResultSet executeData(String query) throws SQLException {
		if(database == null) {
			getDatabase();
		}

		Statement statement = database.createStatement();
		ResultSet rs = statement.executeQuery(query);

		return rs;
	}

	public void insertData(PreparedStatement ps) throws SQLException {
		if(database == null) {
			getDatabase();
		}

		ps.executeUpdate();
	}
}