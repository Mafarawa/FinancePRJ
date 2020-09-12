package com.mafarawa.view.history;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

import com.mafarawa.model.TransactionModel;

public class TransactionHistoryView {
	protected Button performChangesButton;
	protected Button deleteTransactionButton;
	protected Button cancelButton;
	protected ListView<String> accountList;
	protected ListView<String> categoryList;
	protected TableView<TransactionModel> tableView;
	protected TableColumn<TransactionModel, String> fromPointColumn;
	protected TableColumn<TransactionModel, String> actionColumn;
	protected TableColumn<TransactionModel, Integer> amountColumn;
	protected TableColumn<TransactionModel, String> toPointColumn;
	protected SplitPane splitPane;
	protected Scene scene;

	public TransactionHistoryView() {
		performChangesButton = new Button("Применить изминения");
		deleteTransactionButton = new Button("Удалить транзакцию");
		cancelButton = new Button("Отменить изминения");

		accountList = new ListView<>();
		categoryList = new ListView<>();

		tableView = new TableView<>();

		fromPointColumn = new TableColumn<>("От");
		actionColumn = new TableColumn<>("Действие");
		amountColumn = new TableColumn<>("Сумма");
		toPointColumn = new TableColumn<>("На");

		fromPointColumn.setCellValueFactory(new PropertyValueFactory<TransactionModel, String>("fromPoint"));
		actionColumn.setCellValueFactory(new PropertyValueFactory<TransactionModel, String>("action"));
		amountColumn.setCellValueFactory(new PropertyValueFactory<TransactionModel, Integer>("amount"));
		toPointColumn.setCellValueFactory(new PropertyValueFactory<TransactionModel, String>("toPoint"));

		tableView.getColumns().addAll(fromPointColumn, actionColumn, amountColumn, toPointColumn);

		VBox listLayout = new VBox(10);
		listLayout.getChildren().addAll(accountList, categoryList);

		HBox buttonLayout = new HBox(10);
		buttonLayout.getChildren().addAll(performChangesButton, deleteTransactionButton, cancelButton);
		buttonLayout.setAlignment(Pos.CENTER);

		VBox tableLayout = new VBox(100);
		tableLayout.getChildren().addAll(tableView, buttonLayout);

		splitPane = new SplitPane(listLayout, tableLayout);

		scene = new Scene(splitPane, 1000, 600);
	}

	public SplitPane getLayout() { return this.splitPane; }
	public Scene getScene() { return this.scene; }
}