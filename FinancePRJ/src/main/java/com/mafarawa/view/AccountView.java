package com.mafarawa.view;

import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class AccountView {
	private Label accountName;
	private Label accountNameValue;
	private Label accountType;
	private Label accountTypeValue;
	private Label accountBalance;
	private Label accountBalanceValue;
	private ListView<String> accountList;
	private SplitPane splitPane;
	private Button addAccount;
	private Button removeAccount;
	private Button editAccount;
	private Scene scene;

	public AccountView() {
		accountName = new Label("Название счета:");
		accountNameValue = new Label();
		accountType = new Label("Тип счета:");
		accountTypeValue = new Label();
		accountBalance = new Label("Остаток на счету:");
		accountBalanceValue = new Label();
		accountList = new ListView<>();
		splitPane = new SplitPane();
		addAccount = new Button("Добавить");
		removeAccount = new Button("Удалить");
		editAccount = new Button("Изменить");

		HBox buttonLayout = new HBox(10);
		buttonLayout.setAlignment(Pos.BOTTOM_CENTER);
		buttonLayout.getChildren().addAll(addAccount, removeAccount, editAccount);

		GridPane infoLayout = new GridPane();
		infoLayout.setHgap(20);
		infoLayout.setVgap(20);
		infoLayout.setAlignment(Pos.TOP_CENTER);
		infoLayout.add(accountName, 0, 0);
		infoLayout.add(accountNameValue, 1, 0);
		infoLayout.add(accountType, 0, 1);
		infoLayout.add(accountTypeValue, 1, 1);
		infoLayout.add(accountBalance, 0, 2);
		infoLayout.add(accountBalanceValue, 1, 2);

		VBox rightLayout = new VBox(400);
		rightLayout.getChildren().addAll(infoLayout, buttonLayout);

		splitPane.getItems().addAll(accountList, rightLayout);

		scene = new Scene(splitPane, 1000, 600);
	}

	public Scene getScene() { return this.scene; }
	public SplitPane getLayout() { return this.splitPane; }
}