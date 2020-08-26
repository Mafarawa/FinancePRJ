package com.mafarawa.view.main;

import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import java.time.LocalDate;

public class TopUpView {
	protected TabPane tabPane;
	protected Tab incomeTab;
	protected Tab accountTab;
	protected Button incomeDoneButton;
	protected Button accountDoneButton;
	protected ListView<String> incomeList;
	protected ListView<String> accountList;
	protected TextField incomeInput;
	protected TextField accountInput;
	protected DatePicker incomeDatePicker;
	protected DatePicker accountDatePicker;
	protected Scene scene;
	protected Stage childStage;

	public TopUpView(Stage stage) {
		incomeList = new ListView<>();
		incomeInput = new TextField();
		incomeDoneButton = new Button("Перевести");
		incomeDatePicker = new DatePicker(LocalDate.now());

		incomeList.setPrefHeight(150);
		incomeInput.setPromptText("Сумма");

		VBox incomeLayout = new VBox(10);
		incomeLayout.setPadding(new Insets(10, 10, 10, 10));	
		incomeLayout.setAlignment(Pos.CENTER);
		incomeLayout.getChildren().addAll(incomeList, incomeInput, incomeDatePicker, incomeDoneButton);

		incomeTab = new Tab("С Дохода", incomeLayout);

		accountList = new ListView<>();
		accountInput = new TextField();
		accountDoneButton = new Button("Перевести");
		accountDatePicker = new DatePicker(LocalDate.now());

		accountList.setPrefHeight(150);
		accountInput.setPromptText("Сумма");

		VBox accountLayout = new VBox(10);
		accountLayout.setPadding(new Insets(10, 10, 10, 10));	
		accountLayout.setAlignment(Pos.CENTER);
		accountLayout.getChildren().addAll(accountList, accountInput, accountDatePicker, accountDoneButton);

		accountTab = new Tab("Со Счета", accountLayout);

		tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		tabPane.getTabs().add(incomeTab);
		tabPane.getTabs().add(accountTab);

		scene = new Scene(tabPane);

		childStage = new Stage();
        childStage.initOwner(stage);
        childStage.initModality(Modality.WINDOW_MODAL);
        childStage.setTitle("Добавить счет");
        childStage.setResizable(false);
        childStage.setScene(scene);
	}

	public Stage getStage() { return this.childStage; }
	public Scene getScene() { return this.scene; }
}