package com.mafarawa.controller.main;

import com.mafarawa.view.main.AccountView;
import com.mafarawa.model.AccountModel;
import com.mafarawa.model.AccountType;
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
	private ObservableList<AccountModel> accountListData;

	private static Logger logger;
	static { logger = Logger.getLogger(AccountController.class.getName()); }

	public AccountController(Stage stage, String name) {
		super();

		AddAccountController aac = new AddAccountController(stage, name);
		aac.getStage().setOnCloseRequest(e -> getUserAccountsList(name));
		super.addAccount.setOnAction(e -> aac.getStage().show());

		accountListData = FXCollections.observableArrayList();
		getUserAccountsList(name);
		super.accountList.setItems(accountListData);
		super.accountList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AccountModel>() {
			@Override
			public void changed(ObservableValue<? extends AccountModel> observable,
								AccountModel oldValue, AccountModel newValue) {
				accountNameValue.setText(newValue.getAccountName());
				accountTypeValue.setText(newValue.getAccountType());
				accountBalanceValue.setText(String.valueOf(newValue.getAccountBalance()));

				RemoveAccountController rac = new RemoveAccountController(stage, newValue.getAccountName());
				rac.getStage().setOnCloseRequest(r -> {
					getUserAccountsList(name);
					accountList.getSelectionModel().select(0);								
				});

				removeAccount.setOnAction(r -> rac.getStage().show());

				EditAccountController eac = new EditAccountController(stage, newValue.getAccountName());
				eac.getStage().setOnCloseRequest(r -> {
					getUserAccountsList(name);
					accountList.getSelectionModel().select(0);								
				});
				
				editAccount.setOnAction(r -> eac.getStage().show());

				TopUpController tuc = new TopUpController(stage, newValue.getAccountName(), name);
				tuc.getStage().setOnCloseRequest(r -> {
					getUserAccountsList(name);
					accountList.getSelectionModel().select(0);								
				});

				topUpButton.setOnAction(r -> tuc.getStage().show());

				DebitAccountController dac = new DebitAccountController(stage, newValue.getAccountName(), name);
				dac.getStage().setOnCloseRequest(r -> {
					getUserAccountsList(name);
					accountList.getSelectionModel().select(0);								
				});

				debitFromButton.setOnAction(r -> dac.getStage().show());
			}
		});
	}

	private void getUserAccountsList(String name) {
		super.accountList.getItems().clear();

		DBGate dbGate = DBGate.getInstance();

		try {
			ResultSet rs = dbGate.executeData("SELECT userfp.accounts " + 
											  "FROM userfp " + 
											  "WHERE userfp.name = '" + name + "';");
			rs.next();
			int userfpAccountId = rs.getInt("accounts");

			rs = dbGate.executeData("SELECT account.name, account_type.type, account.balance " + 
											  "FROM account JOIN account_type " + 
											  "ON account_type.id = account.type_id " + 
											  "WHERE account.account_id = " + userfpAccountId + ";");
			while(rs.next()) {
				accountListData.add(new AccountModel(rs.getString("name"), rs.getString("type"), rs.getInt("balance")));
			}

		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}
}