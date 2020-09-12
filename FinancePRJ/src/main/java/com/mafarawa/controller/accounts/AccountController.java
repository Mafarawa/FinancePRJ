package com.mafarawa.controller.accounts;

import com.mafarawa.view.accounts.AccountView;
import com.mafarawa.model.AccountModel;
import com.mafarawa.connect.DBGate;

import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import java.sql.*;
import org.apache.log4j.Logger;

public class AccountController extends AccountView {
	private ObservableList<AccountModel> accountListData;

	private static DBGate dbGate;
	private static Logger logger;

	static {
		dbGate = DBGate.getInstance();
		logger = Logger.getLogger(AccountController.class.getName());
	}

	public AccountController(Stage stage, String name) {
		super();

		stage.setTitle("FinancePRJ | " + name);

		AddAccountController aac = new AddAccountController(stage, name);
		aac.getStage().setOnCloseRequest(e -> getUserAccountsList(name));
		super.addAccount.setOnAction(e -> aac.getStage().show());

		// Preparing accounts to display
		accountListData = FXCollections.observableArrayList();
		getUserAccountsList(name);
		super.accountList.setItems(accountListData);

		removeAccount.setDisable(true);
		editAccount.setDisable(true);
		topUpButton.setDisable(true);
		debitFromButton.setDisable(true);

		// Listener
		super.accountList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AccountModel>() {
			@Override
			public void changed(ObservableValue<? extends AccountModel> observable,
								AccountModel oldValue, AccountModel newValue) {
				try {
					removeAccount.setDisable(false);
					editAccount.setDisable(false);
					topUpButton.setDisable(false);
					debitFromButton.setDisable(false);						

					logger.debug("Selected item: " + oldValue + ", " + newValue);

					// Displaing account data
					accountNameValue.setText(newValue.getAccountName());
					accountTypeValue.setText(newValue.getAccountType());
					accountBalanceValue.setText(String.valueOf(newValue.getAccountBalance()));

					// This controller used to remove selected account
					RemoveAccountController rac = new RemoveAccountController(stage, newValue.getAccountName());
					rac.getStage().setOnCloseRequest(r -> {
						getUserAccountsList(name);
						accountNameValue.setText("");							
						accountTypeValue.setText("");							
						accountBalanceValue.setText("");							
						accountList.getSelectionModel().select(0);	
					});

					removeAccount.setOnAction(r -> rac.getStage().show());

					// This controller used to edit data of selected account 
					EditAccountController eac = new EditAccountController(stage, newValue.getAccountName());
					eac.getStage().setOnCloseRequest(r -> {
						getUserAccountsList(name);
						accountList.getSelectionModel().select(0);								
					});
					
					editAccount.setOnAction(r -> eac.getStage().show());

					// This controller used to top up balance of selected account
					TopUpController tuc = new TopUpController(stage, newValue.getAccountName(), name);
					tuc.getStage().setOnCloseRequest(r -> {
						getUserAccountsList(name);
						accountList.getSelectionModel().select(0);								
					});

					topUpButton.setOnAction(r -> tuc.getStage().show());

					// This controller used to debit balance of selected account
					DebitAccountController dac = new DebitAccountController(stage, newValue.getAccountName(), name);
					dac.getStage().setOnCloseRequest(r -> {
						getUserAccountsList(name);
						accountList.getSelectionModel().select(0);								
					});

					debitFromButton.setOnAction(r -> dac.getStage().show());

				} catch(NullPointerException npe) {}
			}
		});
	}

	// This method used to execute user accounts from database 
	private void getUserAccountsList(String name) {
		super.accountList.getItems().clear();

		try {
			ResultSet rs = dbGate.executeData("SELECT userfp.accounts " + 
											  "FROM userfp " + 
											  "WHERE userfp.name = '" + name + "';");
			rs.next();
			int userfpAccountId = rs.getInt("accounts");

			// Executing account data
			rs = dbGate.executeData("SELECT account.name, account_type.type, account.balance " + 
											  "FROM account JOIN account_type " + 
											  "ON account_type.id = account.type_id " + 
											  "WHERE account.account_id = " + userfpAccountId + ";");
			while(rs.next()) {
				// Insert accounts to observable list
				accountListData.add(new AccountModel(rs.getString("name"), rs.getString("type"), rs.getInt("balance")));
			}

		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}
}