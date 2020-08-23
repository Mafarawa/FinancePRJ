package com.mafarawa.controller.main;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.geometry.Pos;
import java.sql.*;
import org.apache.log4j.Logger;

import com.mafarawa.model.AccountType;
import com.mafarawa.connect.DBGate;
import com.mafarawa.view.main.EditAccountView;

public class EditAccountController extends EditAccountView {
	private String accountName;
	private int accountType;
	private int accountBalance;

	private static DBGate dbGate;
    private static Logger logger;

    static {
    	dbGate = DBGate.getInstance();
    	logger = Logger.getLogger(EditAccountController.class.getName());
    }

	public EditAccountController(Stage stage, String accountName) {
		super(stage);

		this.accountName = accountName;
		getSelectedAccount(accountName);

        super.cancelButton.setOnAction(e -> {
        	super.accountNameInput.clear();
        	super.accountBalanceInput.clear();
        	super.childStage.close();
        });

        super.editAccount.setOnAction(e -> editAccount());
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
	private void editAccount() {
		if(checkInputs()) {
			String editedName = super.accountNameInput.getText();
			int editedType = AccountType.getIdByType(super.accountTypeBox.getValue());
			int editedBalance = Integer.parseInt(super.accountBalanceInput.getText());			

			try {
				dbGate.insertData("UPDATE account SET name='" + editedName + "'" + 
								  ", type_id=" + editedType + 
								  ", balance=" + editedBalance + " WHERE name='" + this.accountName + "';");

				super.accountNameInput.clear();
				super.accountBalanceInput.clear();
	        	super.childStage.fireEvent(new WindowEvent(super.childStage, WindowEvent.WINDOW_CLOSE_REQUEST));			
			} catch(SQLException e) {
				logger.error("Exception: ", e);
			}
		}
	}

	// This method checking can this input be inserted into the database
	private boolean checkInputs() {
		String accountName = super.accountNameInput.getText();
		String accountType = super.accountTypeBox.getValue();
		String accountBalance = super.accountBalanceInput.getText();

		// Check if inputs are empty
		if(super.accountNameInput.getText() == null || 
		   super.accountNameInput.getText().trim().isEmpty() ||
		   super.accountBalanceInput.getText() == null ||
		   super.accountBalanceInput.getText().trim().isEmpty()) {
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