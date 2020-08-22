package com.mafarawa.controller.main;

import com.mafarawa.view.main.DebitAccountView;
import com.mafarawa.connect.DBGate;

import javafx.stage.WindowEvent;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import java.sql.*;

public class DebitAccountController extends DebitAccountView {
	private int id;

	private static DBGate dbGate;
    private static Logger logger;

    static {
    	dbGate = DBGate.getInstance();
    	logger = Logger.getLogger(DebitAccountController.class.getName());
    }

	public DebitAccountController(Stage stage, String accountName, String username) {
		super(stage);

		logger.debug("Debit account: " + accountName);

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
		String target = super.accountList.getSelectionModel().getSelectedItem();
		int sum = Integer.parseInt(super.accountInput.getText());
		String transaction = "BEGIN;" +
							 "UPDATE account SET balance = balance - " + sum + " WHERE name='" + accountName + "';" +
							 "UPDATE account SET balance = balance + " + sum + " WHERE name='" + target + "';" +
							 "COMMIT;";

		try {
			dbGate.transaction(transaction);
	    	super.childStage.fireEvent(new WindowEvent(super.childStage, WindowEvent.WINDOW_CLOSE_REQUEST));			
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}

	// This method used to transfer money from the account to the selected expance category
	private void transactionFromExpance(String accountName) {
		int sum = Integer.parseInt(super.expanceInput.getText());
		String transaction = "BEGIN;" +
							 "UPDATE account SET balance = balance - " + sum + " WHERE name='" + accountName + "';" +
							 "COMMIT;";

		try {
			dbGate.transaction(transaction);
	    	super.childStage.fireEvent(new WindowEvent(super.childStage, WindowEvent.WINDOW_CLOSE_REQUEST));			
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}
}