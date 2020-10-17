package com.mafarawa.view;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import org.apache.log4j.Logger;
import com.mafarawa.controller.accounts.AccountController;
import com.mafarawa.controller.history.TransactionHistoryController;
import com.mafarawa.controller.analysis.AnalyseExpancesController;

public class MainWindow {
	private Stage stage;
	private Scene scene;
	private TabPane tabPane;
	private Tab accountTab;
	private Tab expancesTab;
	private Tab transactionsTab;
	private static Logger logger;
	static { logger = Logger.getLogger(MainWindow.class.getName()); }

	public MainWindow(Stage stage, String name) {
		this.stage = stage;

		AccountController accountController = new AccountController(stage, name);
		TransactionHistoryController transactionHistoryController = new TransactionHistoryController(stage, name);
		AnalyseExpancesController analyseExpancesController = new AnalyseExpancesController(stage, name);

		Tab accountTab = new Tab("Счета", accountController.getLayout());
		Tab transactionsTab = new Tab("Операции", transactionHistoryController.getLayout());
		Tab analyseTab = new Tab("Расходы", analyseExpancesController.getLayout());

		tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		tabPane.getTabs().add(accountTab);
		tabPane.getTabs().add(transactionsTab);
		tabPane.getTabs().add(analyseTab);

		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
				if(newTab.equals(transactionsTab)) {
					transactionHistoryController.getAccountData();
					transactionHistoryController.getTransactionData();
				}
			}
		});

		scene = new Scene(tabPane, 1000, 600);		
	}

	public Stage getStage() { return this.stage; }
	public Scene getScene() { return this.scene; }
}