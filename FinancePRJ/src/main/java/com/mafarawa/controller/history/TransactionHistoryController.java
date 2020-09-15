package com.mafarawa.controller.history;

import com.mafarawa.connect.DBGate;
import com.mafarawa.view.history.TransactionHistoryView;
import com.mafarawa.model.TransactionModel;
import com.mafarawa.model.AccountModel;

import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import java.sql.*;
import org.apache.log4j.Logger;

public class TransactionHistoryController extends TransactionHistoryView {
	private int userfpId;
	private ObservableList<TransactionModel> transactionData;
	private ObservableList<AccountModel> accountData;
	private ObservableList<String> categoryData;

	private static Logger logger;
	static { logger = Logger.getLogger(TransactionHistoryController.class.getName()); }

	public TransactionHistoryController(Stage stage, String name) {
		super();

		DBGate dbGate = DBGate.getInstance();
		try {
			ResultSet rs = dbGate.executeData("SELECT userfp.transactions " + 
											  "FROM userfp " + 
											  "WHERE userfp.name = '" + name + "';");
			rs.next();
			this.userfpId = rs.getInt("transactions");			
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}

		transactionData = FXCollections.observableArrayList();
		getTransactionData(name);
		super.tableView.setItems(transactionData);

		accountData = FXCollections.observableArrayList();
		getAccountData(name);
		super.accountList.setItems(accountData);

		categoryData = FXCollections.observableArrayList();
		getCategoryData(name);
		super.categoryList.setItems(categoryData);
	}

	private void getTransactionData(String name) {
		super.tableView.getItems().clear();

		DBGate dbGate = DBGate.getInstance();
		try {
			// Executing account data
			ResultSet rs = dbGate.executeData("SELECT transactions.from_point, transaction_actions.action_name, transactions.amount, transactions.to_point, transactions.transaction_date " + 
									" FROM transactions " + 
									" JOIN transaction_actions " + 
									" ON transactions.action = transaction_actions.id " + 
									" WHERE transactions.transaction_id = " + userfpId);
			while(rs.next()) {
				// Insert accounts to observable list
				transactionData.add(new TransactionModel(rs.getString("from_point"),
														 rs.getString("action_name"),
														 rs.getInt("amount"), 
														 rs.getString("to_point"),
														 rs.getString("transaction_date")));
			}

		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}

	// This method used to execute user accounts from database 
	private void getAccountData(String name) {
		super.accountList.getItems().clear();

		DBGate dbGate = DBGate.getInstance();
		try {
			// Executing account data
			ResultSet rs = dbGate.executeData("SELECT account.name, account_type.type, account.balance " + 
											  "FROM account JOIN account_type " + 
											  "ON account_type.id = account.type_id " + 
											  "WHERE account.account_id = " + userfpId + ";");
			while(rs.next()) {
				// Insert accounts to observable list
				accountData.add(new AccountModel(rs.getString("name"), rs.getString("type"), rs.getInt("balance")));
			}

		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}

	private void getCategoryData(String name) {
		super.categoryList.getItems().clear();

		DBGate dbGate = DBGate.getInstance();
		try {
			ResultSet rs = dbGate.executeData("SELECT expance.category FROM expance WHERE expance.expance_id = " + userfpId);
			while(rs.next()) { categoryData.add(rs.getString("category")); }

			rs = dbGate.executeData("SELECT income.category FROM income WHERE income.income_id = " + userfpId);
			while(rs.next()) { categoryData.add(rs.getString("category")); }
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}
}