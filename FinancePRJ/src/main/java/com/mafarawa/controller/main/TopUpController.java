package com.mafarawa.controller.main;

import com.mafarawa.view.main.TopUpView;
import com.mafarawa.connect.DBGate;

import javafx.stage.WindowEvent;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import java.sql.*;

public class TopUpController extends TopUpView {
	private int id;
	private static DBGate dbGate;
    private static Logger logger;

    static {
    	dbGate = DBGate.getInstance();
    	logger = Logger.getLogger(TopUpController.class.getName());
    }

	public TopUpController(Stage stage, String accountName, String username) {
		super(stage);

    	try {
    		ResultSet rs = dbGate.executeData("SELECT userfp.id FROM userfp WHERE userfp.name='" + username +"';");
    		rs.next();
    		this.id = rs.getInt("id");
    	} catch(Exception e) {
    		logger.error("Exception: ", e);
    	}

		getAccountList(accountName);
		getIncomeList();

		super.childStage.setTitle("Пополнить счет " + accountName);
		super.incomeDoneButton.setOnAction(e -> transactionFromIncome(accountName));
		super.accountDoneButton.setOnAction(e -> transactionFromAccount(accountName));
	}

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

	private void getIncomeList() {
		try {
			ResultSet rs = dbGate.executeData("SELECT income.category " +
											  "FROM income " + 
											  "WHERE income.income_id=" + id);
			while(rs.next()) {
				super.incomeList.getItems().add(rs.getString("category"));
			}
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}

	private void transactionFromAccount(String accountName) {
		String target = super.accountList.getSelectionModel().getSelectedItem();
		int sum = Integer.parseInt(super.accountInput.getText());
		String transaction = "BEGIN;" +
							 "UPDATE account " + 
							 "SET balance = balance + " + sum + " " +
							 "WHERE name='" + accountName + "';" +
							 "UPDATE account " + 
							 "SET balance = balance - " + sum + " WHERE name='" + target + "';" +
							 "INSERT INTO transactions (transaction_id, from_point, action, amount, to_point) " + 
							 "VALUES(" + this.id +", '" + accountName + "', " + 3 + ", " + sum + ", '" + target + "');" +
							 "COMMIT;";

		try {
			dbGate.transaction(transaction);
	    	super.childStage.fireEvent(new WindowEvent(super.childStage, WindowEvent.WINDOW_CLOSE_REQUEST));			
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}

	private void transactionFromIncome(String accountName) {
		String income = super.incomeList.getSelectionModel().getSelectedItem();
		int sum = Integer.parseInt(super.incomeInput.getText());
		String transaction = "BEGIN;" +
							 "UPDATE account SET balance = balance + " + sum + " WHERE name='" + accountName + "';" +
							 "INSERT INTO transactions (transaction_id, from_point, action, amount, to_point) " + 
							 "VALUES(" + this.id +", '" + income + "', " + 1 + ", " + sum + ", '" + accountName + "');" +							 
							 "COMMIT;";

		try {
			dbGate.transaction(transaction);
	    	super.childStage.fireEvent(new WindowEvent(super.childStage, WindowEvent.WINDOW_CLOSE_REQUEST));			
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}
}