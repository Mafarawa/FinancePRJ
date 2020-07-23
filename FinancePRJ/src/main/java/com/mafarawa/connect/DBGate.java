package com.mafarawa.connect;

import java.sql.*;
import org.apache.log4j.Logger;

public class DBGate {
	private static Logger logger;
	private static DBGate instance;
	private Connection database;

	static { logger = Logger.getLogger(DBGate.class.getName()); }

	private DBGate() {}

	public static synchronized DBGate getInstance() {
		if(instance == null) {
			instance = new DBGate();
		}

		logger.info("Instance of DBGate achieved");
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
				logger.info("Database connected");
			} catch(Exception e) {
				logger.error("Exception", e);
			}
		}

		logger.info("Database achieved");
		return database;
	}

	public ResultSet executeData(String query) throws SQLException {
		if(database == null) {
			getDatabase();
		}

		Statement statement = database.createStatement();
		ResultSet rs = statement.executeQuery(query);
		logger.debug("ResultSet executed this query: " + query);

		return rs;
	}

	public void insertData(PreparedStatement ps) throws SQLException {
		if(database == null) {
			getDatabase();
		}

		ps.executeUpdate();
		logger.info("PreparedStatement executed");
	}
}