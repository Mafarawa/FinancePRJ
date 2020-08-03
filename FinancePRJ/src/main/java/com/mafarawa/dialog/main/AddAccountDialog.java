package com.mafarawa.dialog.main;

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

public class AddAccountDialog {
	private Label accountNameLabel;
	private Label accountTypeLabel;
	private Label accountBalanceLabel;
	private TextField accountNameInput;
	private TextField accountBalanceInput;
	private ComboBox<String> accountTypeBox;
	private Button cancelButton;
	private Button doneButton;
	private Stage childStage;
	private Scene scene;

    private static Logger logger;
    static { logger = Logger.getLogger(AddAccountDialog.class.getName()); }

	public AddAccountDialog(Stage stage, String name) {
		accountNameLabel = new Label("Название счета:");
		accountTypeLabel = new Label("Тип счета:");
		accountBalanceLabel = new Label("Баланс на счету:");
		accountNameInput = new TextField();
		accountBalanceInput = new TextField();

		AccountType[] accountTypes = AccountType.values();
		accountTypeBox = new ComboBox<>();
		for(AccountType type : accountTypes) {
			accountTypeBox.getItems().add(type.getAccountType());
		}

		cancelButton = new Button("Отмена");
		doneButton = new Button("Добавить");

		HBox buttonLayout = new HBox(20);
		buttonLayout.setAlignment(Pos.CENTER);
		buttonLayout.getChildren().addAll(cancelButton, doneButton);

		GridPane inputLayout = new GridPane();
		inputLayout.setHgap(20);
		inputLayout.setVgap(20);
		inputLayout.setAlignment(Pos.CENTER);
		inputLayout.add(accountNameLabel, 0, 0);
		inputLayout.add(accountNameInput, 1, 0);
		inputLayout.add(accountTypeLabel, 0, 1);
		inputLayout.add(accountTypeBox, 1, 1);
		inputLayout.add(accountBalanceLabel, 0, 2);
		inputLayout.add(accountBalanceInput, 1, 2);

		VBox rootLayout = new VBox(20);
		rootLayout.setAlignment(Pos.CENTER);
		rootLayout.getChildren().addAll(inputLayout, buttonLayout);

		scene = new Scene(rootLayout, 350, 250);

		childStage = new Stage();
        childStage.initOwner(stage);
        childStage.initModality(Modality.WINDOW_MODAL);
        childStage.setTitle("Добавить счет");
        childStage.setResizable(false);
        childStage.setScene(scene);

        cancelButton.setOnAction(e -> {
        	accountNameInput.clear();
        	accountBalanceInput.clear();
        	childStage.close();
        });

        doneButton.setOnAction(e -> {
        	addNewAccount(name);
        	childStage.fireEvent(new WindowEvent(childStage, WindowEvent.WINDOW_CLOSE_REQUEST));
        });
	}

	private void addNewAccount(String name) {
		int userfp_id = 0;
		String accountName = accountNameInput.getText();
		String accountType = accountTypeBox.getValue();
		String accountBalance = accountBalanceInput.getText();
		DBGate dbGate = DBGate.getInstance();

		try {
			ResultSet rs = dbGate.executeData("SELECT userfp.id FROM userfp WHERE userfp.name='" + name + "';");
			rs.next();
			userfp_id = rs.getInt("id");

			dbGate.insertData("INSERT INTO account(account_id, name, type, balance) VALUES(" + 
				userfp_id + ", '" + accountName + "', '" + accountType + "', " + accountBalance + ");");
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}

	public Scene getScene() { return this.scene; }
	public Stage getStage() { return this.childStage; }
}