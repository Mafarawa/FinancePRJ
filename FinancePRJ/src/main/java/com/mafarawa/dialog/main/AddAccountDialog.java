package com.mafarawa.dialog.main;

import javafx.stage.Stage;
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

import com.mafarawa.model.AccountType;

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

	public AddAccountDialog(Stage stage) {
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

		this.childStage = new Stage();
        this.childStage.initOwner(stage);
        this.childStage.initModality(Modality.WINDOW_MODAL);
        this.childStage.setTitle("Добавить счет");
        this.childStage.setResizable(false);
        this.childStage.setScene(scene);
	}

	public Scene getScene() { return this.scene; }
	public Stage getStage() { return this.childStage; }
}