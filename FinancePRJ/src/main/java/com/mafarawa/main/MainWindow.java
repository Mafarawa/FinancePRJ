package com.mafarawa.main;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;

public class MainWindow {
	private Stage stage;
	private Scene scene;
	private TabPane tabPane;
	private Tab accountTab;
	private Tab sada;
	private Tab gasf;

	public MainWindow(Stage stage) {
		this.stage = stage;

		accountTab = new Tab("Счета");
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