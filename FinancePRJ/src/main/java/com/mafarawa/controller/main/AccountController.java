package com.mafarawa.controller.main;

import com.mafarawa.view.main.AccountView;
import com.mafarawa.model.AccountModel;
import com.mafarawa.connect.DBGate;
import com.mafarawa.controller.main.TopUpController;
import com.mafarawa.controller.main.AddAccountController;
import com.mafarawa.controller.main.DebitAccountController;
import com.mafarawa.controller.main.RemoveAccountController;
import com.mafarawa.controller.main.EditAccountController;

import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
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
		
		AddAccountController aac = new AddAccountController(stage, name);
		aac.getStage().setOnCloseRequest(e -> getUserAccountsList(name));

		super.accountList.getSelectionModel().select(0);

		super.accountList.setOnMouseClicked(e -> {
			getSelectedAccount(super.accountList.getSelectionModel().getSelectedItem());

			RemoveAccountController rac = new RemoveAccountController(stage, super.accountList.getSelectionModel().getSelectedItem());
			rac.getStage().setOnCloseRequest(r -> {
				getUserAccountsList(name);
				super.accountList.getSelectionModel().select(0);								
			});

			super.removeAccount.setOnAction(r -> rac.getStage().show());

			EditAccountController eac = new EditAccountController(stage, super.accountList.getSelectionModel().getSelectedItem());
			eac.getStage().setOnCloseRequest(r -> {
				getUserAccountsList(name);
				super.accountList.getSelectionModel().select(0);								
			});
			
			super.editAccount.setOnAction(r -> eac.getStage().show());

			TopUpController tuc = new TopUpController(stage, super.accountList.getSelectionModel().getSelectedItem(), name);
			tuc.getStage().setOnCloseRequest(r -> {
				getUserAccountsList(name);
				super.accountList.getSelectionModel().select(0);								
			});

			super.topUpButton.setOnAction(r -> tuc.getStage().show());

			DebitAccountController dac = new DebitAccountController(stage, super.accountList.getSelectionModel().getSelectedItem(), name);
			dac.getStage().setOnCloseRequest(r -> {
				getUserAccountsList(name);
				super.accountList.getSelectionModel().select(0);								
			});
			super.debitFromButton.setOnAction(r -> dac.getStage().show());
		});

		super.addAccount.setOnAction(e -> aac.getStage().show());
	}

	private void getSelectedAccount(String accountName) {
		DBGate dbGate = DBGate.getInstance();

		try {
			ResultSet rs = dbGate.executeData("SELECT account_type.type, account.balance " + 
											  "FROM account JOIN account_type " + 
											  "ON account_type.id = account.type_id " + 
											  "WHERE name = '" + accountName + "';");
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
			ResultSet rs = dbGate.executeData("SELECT userfp.accounts FROM userfp WHERE userfp.name=" + "'" + name + "';");
			rs.next();
			this.account_id = rs.getInt("accounts");

			rs = dbGate.executeData("SELECT account.name FROM account WHERE account.account_id=" + this.account_id + ";");
			while(rs.next()) {
				super.accountList.getItems().add(rs.getString("name"));
			}

		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}
}