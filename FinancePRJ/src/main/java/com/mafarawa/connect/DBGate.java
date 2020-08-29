package com.mafarawa.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

		return instance;
	}

	// Connecting to database
	public Connection getDatabase() {
		if(database == null) {
			try {
				database = DriverManager.getConnection(
						"jdbc:postgresql://localhost/financeprj_db",
						"gosha",
						"mafarawa228"
				);
				database.setAutoCommit(false);

				logger.info("Database connected");
			} catch(Exception e) {
				logger.error("Exception", e);
			}
		}

		return database;
	}
	
	// Execute data
	public ResultSet executeData(String query) throws SQLException {
		if(database == null) {
			getDatabase();
		}

		Statement statement = database.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = statement.executeQuery(query);
		// logger.debug("ResultSet executed this query: " + query);

		return rs;
	}

	// Insert data
	public void insertData(PreparedStatement ps) throws SQLException {
		if(database == null) {
			getDatabase();
		}

		ps.executeUpdate();
		// logger.info("PreparedStatement executed");
	}

	// Insert data
	public void insertData(String st) throws SQLException {
		logger.debug("Trying to execute: " + st);

		if(database == null) {
			getDatabase();
		}

		Statement statement = database.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		statement.execute(st);
		// logger.info("Statement executed");
	}
}