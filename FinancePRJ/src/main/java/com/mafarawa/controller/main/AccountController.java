package com.mafarawa.controller.main;

import com.mafarawa.view.main.AccountView;
import com.mafarawa.model.AccountModel;
import com.mafarawa.connect.DBGate;
import com.mafarawa.dialog.main.AddAccountDialog;
import com.mafarawa.dialog.main.RemoveAccountDialog;

import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.sql.*;
import org.apache.log4j.Logger;

public class AccountController extends AccountView {
	private int account_id;

	private static Logger logger;
	static { logger = Logger.getLogger(AccountController.class.getName()); }

	public AccountController(Stage stage, String name) {
		super();

		getUserAccountsList(name);
		
		AddAccountDialog addAccountDialog = new AddAccountDialog(stage, name);
		addAccountDialog.getStage().setOnCloseRequest(e -> getUserAccountsList(name));
	
		//Make that shit better
		super.accountList.setOnMouseClicked(e -> {
			getSelectedAccount(super.accountList.getSelectionModel().getSelectedItem());
			RemoveAccountDialog removeAccountDialog = new RemoveAccountDialog(stage, super.accountList.getSelectionModel().getSelectedItem());
			removeAccountDialog.getStage().setOnCloseRequest(r -> getUserAccountsList(name));
			super.removeAccount.setOnAction(z -> removeAccountDialog.getStage().show());
		});

		super.addAccount.setOnAction(e -> addAccountDialog.getStage().show());
	}

	private void getSelectedAccount(String accountName) {
		DBGate dbGate = DBGate.getInstance();

		try {
			ResultSet rs = dbGate.executeData("SELECT account.type, account.balance FROM account WHERE account.name=" + "'" + accountName + "';");
			while(rs.next()) {
				super.accountNameValue.setText(accountName);
				super.accountTypeValue.setText(rs.getString(1));
				super.accountBalanceValue.setText(String.valueOf(rs.getInt(2)));
			}
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}

	private void getUserAccountsList(String name) {
		super.accountList.getItems().clear();
		
		DBGate dbGate = DBGate.getInstance();

		try {
			ResultSet rs = dbGate.executeData("SELECT userfp.account FROM userfp WHERE userfp.name=" + "'" + name + "';");
			rs.next();
			this.account_id = rs.getInt("account");

			rs = dbGate.executeData("SELECT account.name FROM account JOIN userfp ON userfp.id=" + account_id + ";");
			while(rs.next()) {
				super.accountList.getItems().add(rs.getString("name"));
			}
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}
}