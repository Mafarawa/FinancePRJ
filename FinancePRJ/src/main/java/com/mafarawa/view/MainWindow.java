package com.mafarawa.view.accounts;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;

import com.mafarawa.controller.accounts.AccountController;

public class MainWindow {
	protected Stage stage;
	protected Scene scene;
	protected TabPane tabPane;
	protected Tab accountTab;
	protected Tab sada;
	protected Tab gasf;
	protected static String name;

	public MainWindow(Stage stage, String name) {
		this.stage = stage;
		this.name = name;

		accountTab = new Tab("Счета", new AccountController(stage, name).getLayout());
		sada = new Tab("Расходы");
		gasf = new Tab("Операции");

		tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		tabPane.getTabs().add(accountTab);
		tabPane.getTabs().add(sada);
		tabPane.getTabs().add(gasf);

		scene = new Scene(tabPane, 1000, 600);		
	}

	public Stage getStage() { return this.stage; }
	public Scene getScene() { return this.scene; }
}