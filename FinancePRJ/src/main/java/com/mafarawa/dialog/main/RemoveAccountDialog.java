package com.mafarawa.dialog.main;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.geometry.Pos;
import java.sql.*;
import org.apache.log4j.Logger;

import com.mafarawa.model.AccountType;
import com.mafarawa.connect.DBGate;

public class RemoveAccountDialog {
	private Label questionLabel;
	private Button yesButton;
	private Button noButton;	
	private Stage childStage;
	private Scene scene;

    private static Logger logger;
    static { logger = Logger.getLogger(RemoveAccountDialog.class.getName()); }

	public RemoveAccountDialog(Stage stage, String accountName) {
		questionLabel = new Label("Вы действительно хотите удалить счет '" + accountName + "'?");
		yesButton = new Button("Да");
		noButton = new Button("Нет");

		HBox buttonLayout = new HBox(50);
		buttonLayout.setAlignment(Pos.CENTER);
		buttonLayout.getChildren().addAll(yesButton, noButton);

		VBox rootLayout = new VBox(10);
		rootLayout.setAlignment(Pos.CENTER);
		rootLayout.getChildren().addAll(questionLabel, buttonLayout);

		scene = new Scene(rootLayout, 450, 100);

		childStage = new Stage();
        childStage.initOwner(stage);
        childStage.initModality(Modality.WINDOW_MODAL);
        childStage.setTitle("Удалить счет");
        childStage.setResizable(false);
	    childStage.setScene(scene);

	    yesButton.setOnAction(e -> {
	    	deleteAccount(accountName);
	    	childStage.fireEvent(new WindowEvent(childStage, WindowEvent.WINDOW_CLOSE_REQUEST));
	    });

	    noButton.setOnAction(e -> childStage.close());
	}

	private void deleteAccount(String accountName) {
		DBGate dbGate = DBGate.getInstance();

		try {
			dbGate.insertData("DELETE FROM account WHERE name='" + accountName + "';");
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}

	public Scene getScene() { return this.scene; }
	public Stage getStage() { return this.childStage; }
}