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
import com.mafarawa.view.main.AddAccountView;

public class AddAccountController extends AddAccountView {
	private static DBGate dbGate;
    private static Logger logger;

    static {
    	dbGate = DBGate.getInstance();
    	logger = Logger.getLogger(AddAccountController.class.getName());
    }

	public AddAccountController(Stage stage, String name) {
		super(stage);

        super.cancelButton.setOnAction(e -> {
        	super.accountNameInput.clear();
        	super.accountBalanceInput.clear();
        	super.childStage.close();
        });

        super.doneButton.setOnAction(e -> addNewAccount(name));
	}

	// This method used to add new account
	private void addNewAccount(String name) {
		int userfp_id = 0;
		String accountName = super.accountNameInput.getText();
		String accountType = super.accountTypeBox.getValue();
		String accountBalance = super.accountBalanceInput.getText();
		DBGate dbGate = DBGate.getInstance();

		if(accountName.isEmpty() || accountBalance.isEmpty()) {
			super.checkLabel.setText("Заполните все поля!");
		} else {		
			try {
				ResultSet rs = dbGate.executeData("SELECT userfp.id FROM userfp WHERE userfp.name='" + name + "';");
				rs.next();
				userfp_id = rs.getInt("id");

				dbGate.insertData("INSERT INTO account(account_id, name, type_id, balance) VALUES(" + 
					userfp_id + ", '" + accountName + "', " + AccountType.getIdByType(accountType) + ", " + accountBalance + ");");

				super.accountNameInput.clear();
				super.accountBalanceInput.clear();
	        	super.childStage.fireEvent(new WindowEvent(super.childStage, WindowEvent.WINDOW_CLOSE_REQUEST));			
			} catch(SQLException e) {
				super.checkLabel.setText("Счет с именем '" + accountName + "' уже существует");
				logger.error("Exception: ", e);
			}

		}
	}

	public Scene getScene() { return this.scene; }
	public Stage getStage() { return this.childStage; }
}