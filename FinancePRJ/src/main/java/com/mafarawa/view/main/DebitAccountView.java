package com.mafarawa.view.main;

import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class DebitAccountView {
	protected TabPane tabPane;
	protected Tab expanceTab;
	protected Tab accountTab;
	protected Button expanceDoneButton;
	protected Button accountDoneButton;
	protected ListView<String> expanceList;
	protected ListView<String> accountList;
	protected TextField expanceInput;
	protected TextField accountInput;
	protected Scene scene;
	protected Stage childStage;

	public DebitAccountView(Stage stage) {
		expanceList = new ListView<>();
		expanceInput = new TextField();
		expanceDoneButton = new Button("Списать");

		expanceList.setPrefHeight(150);
		expanceInput.setPromptText("Сумма");

		VBox expanceLayout = new VBox(10);
		expanceLayout.setPadding(new Insets(10, 10, 10, 10));	
		expanceLayout.setAlignment(Pos.CENTER);
		expanceLayout.getChildren().addAll(expanceList, expanceInput, expanceDoneButton);

		expanceTab = new Tab("На расходы", expanceLayout);

		accountList = new ListView<>();
		accountInput = new TextField();
		accountDoneButton = new Button("Перевести");

		accountList.setPrefHeight(150);
		accountInput.setPromptText("Сумма");

		VBox accountLayout = new VBox(10);
		accountLayout.setPadding(new Insets(10, 10, 10, 10));	
		accountLayout.setAlignment(Pos.CENTER);
		accountLayout.getChildren().addAll(accountList, accountInput, accountDoneButton);

		accountTab = new Tab("На счет", accountLayout);

		tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		tabPane.getTabs().add(expanceTab);
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