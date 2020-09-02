package com.mafarawa.controller.accounts;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;

import java.sql.*;
import org.apache.log4j.Logger;

import com.mafarawa.model.AccountType;
import com.mafarawa.connect.DBGate;
import com.mafarawa.view.accounts.EditAccountView;

public class EditAccountController extends EditAccountView {
	private String accountName;
	private int accountType;
	private int accountBalance;
	private int account_id;

	private static DBGate dbGate;
    private static Logger logger;

    static {
    	dbGate = DBGate.getInstance();
    	logger = Logger.getLogger(EditAccountController.class.getName());
    }

	public EditAccountController(Stage stage, String accountName) {
		super(stage);

		DBGate dbGate = DBGate.getInstance();

		try {
			ResultSet rs = dbGate.executeData("SELECT account.account_id FROM account WHERE account.name = '" + accountName + "';");
			rs.next();
			account_id = rs.getInt("account_id");
		} catch(SQLException sqle) {
			logger.error("Exception: ", sqle);
		}


		this.accountName = accountName;
		getSelectedAccount(accountName);

        super.cancelButton.setOnAction(e -> {
        	super.accountNameInput.clear();
        	super.accountBalanceInput.clear();
        	super.childStage.close();
        });

        super.editAccount.setOnAction(e -> editAccount(accountName));
	}

	// This method used to execute account data in order to edit them
	private void getSelectedAccount(String accountName) {
		try {
			ResultSet rs = dbGate.executeData("SELECT account.type_id, account.balance FROM account WHERE account.name='" + accountName + "';");
			while(rs.next()) {
				accountType = rs.getInt("type_id");
				accountBalance = rs.getInt("balance");

				super.accountNameInput.setText(accountName);
				super.accountTypeBox.setValue(AccountType.getTypeById(accountType));
				super.accountBalanceInput.setText(Integer.toString(accountBalance));
			}
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}	

	// This method used to edit selected account
	private void editAccount(String accountName) {
		if(checkInputs()) {
			String editedName = super.accountNameInput.getText();
			int editedType = AccountType.getIdByType(super.accountTypeBox.getValue());
			int editedBalance = Integer.parseInt(super.accountBalanceInput.getText());			

			try {
				PreparedStatement updateAccountName = dbGate.getDatabase().prepareStatement("UPDATE account " +
																							"SET name = REPLACE(name, ?, ?) " +
																							"WHERE account_id = " + account_id);
				updateAccountName.setString(1, accountName);
				updateAccountName.setString(2, editedName);
				dbGate.insertData(updateAccountName);

				PreparedStatement updateAccountTransactions = dbGate.getDatabase().prepareStatement("UPDATE transactions " + 
																									"SET from_point = REPLACE(from_point, ?, ?), " +
																									"to_point = REPLACE(to_point, ?, ?) " +
																									"WHERE transaction_id = " + account_id);
				updateAccountTransactions.setString(1, accountName);
				updateAccountTransactions.setString(2, editedName);
				updateAccountTransactions.setString(3, accountName);
				updateAccountTransactions.setString(4, editedName);
				dbGate.insertData(updateAccountTransactions);

				super.accountNameInput.clear();
				super.accountBalanceInput.clear();
	        	super.childStage.fireEvent(new WindowEvent(super.childStage, WindowEvent.WINDOW_CLOSE_REQUEST));			

	        	try { dbGate.getDatabase().commit(); } catch(SQLException sqle) { logger.error("Exception: ", sqle); }
			} catch(SQLException e) {
				try { dbGate.getDatabase().rollback(); } catch(SQLException sqle) { logger.error("Exception: ", sqle); }
				logger.error("Exception: ", e);
			}
		}
	}

	// This method checking can this input be inserted into the database
	private boolean checkInputs() {
		String accountName = super.accountNameInput.getText();
		String accountBalance = super.accountBalanceInput.getText();

		// Check if inputs are empty
		if(accountName == null || accountName.trim().isEmpty() ||
		   accountBalance == null || accountBalance.trim().isEmpty()) {
			super.checkLabel.setText("Заполните все поля!");
			return false;
		}		

		// Checking if accountBalance has letters
		for(int i = 0; i < accountBalance.length(); i++) {
			if(!(Character.isDigit(accountBalance.charAt(i)) == true)) {
				super.checkLabel.setText("Поле для ввода баланса должо содержать только цифры!");
				return false;
			}
		}

		return true;		
	}

	public Scene getScene() { return this.scene; }
	public Stage getStage() { return this.childStage; }
}