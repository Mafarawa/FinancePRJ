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
		String editedName;
		int editedType;
		int editedBalance;

		if(super.accountNameInput.getText() == null || 
			super.accountNameInput.getText().trim().isEmpty() ||
			super.accountBalanceInput.getText() == null ||
			super.accountBalanceInput.getText().trim().isEmpty()) {
			super.checkLabel.setText("Заполните все поля!");
		} else {
			editedName = super.accountNameInput.getText();
			editedType = AccountType.getIdByType(super.accountTypeBox.getValue());
			editedBalance = Integer.parseInt(super.accountBalanceInput.getText());			

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

	public Scene getScene() { return this.scene; }
	public Stage getStage() { return this.childStage; }
}