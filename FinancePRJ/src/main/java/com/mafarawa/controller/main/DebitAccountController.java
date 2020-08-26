package com.mafarawa.controller.main;

import com.mafarawa.view.main.DebitAccountView;
import com.mafarawa.connect.DBGate;

import javafx.stage.WindowEvent;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import java.sql.*;

public class DebitAccountController extends DebitAccountView {
	private int id;

    private static Logger logger;
    static { logger = Logger.getLogger(DebitAccountController.class.getName()); }

	public DebitAccountController(Stage stage, String accountName, String username) {
		super(stage);

		logger.debug("Debit account: " + accountName);

		DBGate dbGate = DBGate.getInstance();
    	try {
    		ResultSet rs = dbGate.executeData("SELECT userfp.id FROM userfp WHERE userfp.name='" + username +"';");
    		rs.next();
    		this.id = rs.getInt("id");
    	} catch(Exception e) {
    		logger.error("Exception: ", e);
    	}

		getAccountList(accountName);
		getExpanceList();

		super.childStage.setTitle("Списать со счета " + accountName);
		super.expanceDoneButton.setOnAction(e -> transactionFromExpance(accountName));
		super.accountDoneButton.setOnAction(e -> transactionFromAccount(accountName));
	}

	// This method used to execute accounts in order to display them
	private void getAccountList(String accountName) {
		DBGate dbGate = DBGate.getInstance();
		try {
			ResultSet rs = dbGate.executeData("SELECT account.name " + 
											  "FROM account " + 
											  "WHERE account.account_id=" + id);
			while(rs.next()) {
				if(!(rs.getString("name").equals(accountName))) {
					super.accountList.getItems().add(rs.getString("name"));
				}
			}
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}

	// This method used to execute expances in order to display them
	private void getExpanceList() {
		DBGate dbGate = DBGate.getInstance();
		try {
			ResultSet rs = dbGate.executeData("SELECT expance.category " +
											  "FROM expance " + 
											  "WHERE expance.expance_id=" + id);
			while(rs.next()) {
				super.expanceList.getItems().add(rs.getString("category"));
			}
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}

	}

	// This method used to transfer money from the first account to the second account
	private void transactionFromAccount(String accountName) {
		DBGate dbGate = DBGate.getInstance();

		String target = super.accountList.getSelectionModel().getSelectedItem();
		int sum = Integer.parseInt(super.accountInput.getText());

		try {
			// Increase balance of selected account
			PreparedStatement accountAddition = dbGate.getDatabase().prepareStatement("UPDATE account " +
																					  "SET balance = balance - ? " +
																					  "WHERE name = ?;");
			accountAddition.setInt(1, sum);
			accountAddition.setString(2, accountName);
			dbGate.insertData(accountAddition);

			// Decrease balance of selected account
			PreparedStatement accountSubstraction = dbGate.getDatabase().prepareStatement("UPDATE account " + 
																					  	  "SET balance = balance + ? " +
																					   	  "WHERE name = ?;");
			accountSubstraction.setInt(1, sum);
			accountSubstraction.setString(2, target);
			dbGate.insertData(accountSubstraction);

			// Document transaction
			PreparedStatement writeTransaction = dbGate.getDatabase().prepareStatement("INSERT INTO transactions (transaction_id, from_point, action, amount, to_point, transaction_date) " +
																					   "VALUES (?, ?, ?, ?, ?, ?);");
			writeTransaction.setInt(1, this.id);
			writeTransaction.setString(2, accountName);
			writeTransaction.setInt(3, 2);
			writeTransaction.setInt(4, sum);
			writeTransaction.setString(5, target);
			writeTransaction.setObject(6, super.accountDatePicker.getValue());
			dbGate.insertData(writeTransaction);

		} catch(Exception e) {
			try { dbGate.getDatabase().rollback(); } catch(Exception r) { logger.error("Exception: ", r); }
			logger.error("Exception: ", e);
		}

		try { dbGate.getDatabase().commit(); } catch(Exception e) { logger.error("Exception: ", e); }
    	super.childStage.fireEvent(new WindowEvent(super.childStage, WindowEvent.WINDOW_CLOSE_REQUEST));			

    	logger.info("Transaction completed");
	}

	// This method used to transfer money from the account to the selected expance category
	private void transactionFromExpance(String accountName) {
		DBGate dbGate = DBGate.getInstance();

		String expance = super.expanceList.getSelectionModel().getSelectedItem();
		int sum = Integer.parseInt(super.expanceInput.getText());

		try {
			PreparedStatement accountAddition = dbGate.getDatabase().prepareStatement("UPDATE account " + 
																					  "SET balance = balance - ? " + 
																					  "WHERE name = ?;");
			accountAddition.setInt(1, sum);
			accountAddition.setString(2, accountName);
			dbGate.insertData(accountAddition);

			PreparedStatement writeTransaction = dbGate.getDatabase().prepareStatement("INSERT INTO transactions (transaction_id, from_point, action, amount, to_point, transaction_date) " +
																					   "VALUES (?, ?, ?, ?, ?, ?);");
			writeTransaction.setInt(1, this.id);
			writeTransaction.setString(2, expance);
			writeTransaction.setInt(3, 2);
			writeTransaction.setInt(4, sum);
			writeTransaction.setString(5, accountName);
			writeTransaction.setObject(6, super.expanceDatePicker.getValue());
			dbGate.insertData(writeTransaction);

		} catch(Exception e) {
			try { dbGate.getDatabase().rollback(); } catch(Exception r) { logger.error("Exception: ", r); }
			logger.error("Exception: ", e);
		}

		try { dbGate.getDatabase().commit(); } catch(Exception e) { logger.error("Exception: ", e); }		
    	super.childStage.fireEvent(new WindowEvent(super.childStage, WindowEvent.WINDOW_CLOSE_REQUEST));			

    	logger.info("Transaction completed");
	}
}