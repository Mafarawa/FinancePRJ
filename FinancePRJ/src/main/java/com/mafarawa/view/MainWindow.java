package com.mafarawa.view;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;

import com.mafarawa.controller.accounts.AccountController;
import com.mafarawa.controller.history.TransactionHistoryController;

public class MainWindow {
	protected Stage stage;
	protected Scene scene;
	protected TabPane tabPane;
	protected Tab accountTab;
	protected Tab expancesTab;
	protected Tab transactionsTab;
	protected static String name;

	public MainWindow(Stage stage, String name) {
		this.stage = stage;
		this.name = name;

		accountTab = new Tab("Счета", new AccountController(stage, name).getLayout());
		transactionsTab = new Tab("Операции", new TransactionHistoryController(stage, name).getLayout());
		expancesTab = new Tab("Расходы");

		tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		tabPane.getTabs().add(accountTab);
		tabPane.getTabs().add(expancesTab);
		tabPane.getTabs().add(transactionsTab);

		scene = new Scene(tabPane, 1000, 600);		
	}

	public Stage getStage() { return this.stage; }
	public Scene getScene() { return this.scene; }
}