package com.mafarawa.view.main;

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

import com.mafarawa.model.AccountType;

public class AddAccountView {
	protected Label accountNameLabel;
	protected Label accountTypeLabel;
	protected Label accountBalanceLabel;
	protected Label checkLabel;
	protected TextField accountNameInput;
	protected TextField accountBalanceInput;
	protected ComboBox<String> accountTypeBox;
	protected Button cancelButton;
	protected Button doneButton;
	protected Stage childStage;
	protected Scene scene;

	public AddAccountView(Stage stage) {
		accountNameLabel = new Label("Название счета:");
		accountTypeLabel = new Label("Тип счета:");
		accountBalanceLabel = new Label("Баланс на счету:");

		checkLabel = new Label();
		checkLabel.setAlignment(Pos.CENTER);

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
		rootLayout.getChildren().addAll(inputLayout, checkLabel, buttonLayout);

		scene = new Scene(rootLayout, 350, 250);

		childStage = new Stage();
        childStage.initOwner(stage);
        childStage.initModality(Modality.WINDOW_MODAL);
        childStage.setTitle("Добавить счет");
        childStage.setResizable(false);
        childStage.setScene(scene);
	}

	public Scene getScene() { return this.scene; }
	public Stage getStage() { return this.childStage; }
}