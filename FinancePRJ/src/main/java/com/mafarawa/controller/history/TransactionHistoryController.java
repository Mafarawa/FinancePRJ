package com.mafarawa.controller.history;

import com.mafarawa.connect.DBGate;
import com.mafarawa.view.history.TransactionHistoryView;
import com.mafarawa.model.TransactionModel;

import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import java.sql.*;
import org.apache.log4j.Logger;

public class TransactionHistoryController extends TransactionHistoryView {
	private ObservableList<TransactionModel> transactionData;

	private static Logger logger;
	static { logger = Logger.getLogger(TransactionHistoryController.class.getName()); }

	public TransactionHistoryController(Stage stage, String name) {
		super();

		transactionData = FXCollections.observableArrayList();
		getTransactionData(name);
		super.tableView.setItems(transactionData);
	}

	private void getTransactionData(String name) {
		DBGate dbGate = DBGate.getInstance();

		try {
			ResultSet rs = dbGate.executeData("SELECT userfp.transactions " + 
											  "FROM userfp " + 
											  "WHERE userfp.name = '" + name + "';");
			rs.next();
			int transactionId = rs.getInt("transactions");

			// Executing account data
			rs = dbGate.executeData("SELECT transactions.from_point, transaction_actions.action_name, transactions.amount, transactions.to_point " + 
									" FROM transactions " + 
									" JOIN transaction_actions " + 
									" ON transactions.action = transaction_actions.id " + 
									" WHERE transactions.transaction_id = " + transactionId);
			while(rs.next()) {
				// Insert accounts to observable list
				transactionData.add(new TransactionModel(rs.getString("from_point"),
														 rs.getString("action_name"),
														 rs.getInt("amount"), 
														 rs.getString("to_point")));
			}

		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}
}