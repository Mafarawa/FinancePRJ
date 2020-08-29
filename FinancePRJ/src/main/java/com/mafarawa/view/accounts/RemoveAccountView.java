package com.mafarawa.view.accounts;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.geometry.Pos;

public class RemoveAccountView {
	protected Label questionLabel;
	protected Button yesButton;
	protected Button noButton;	
	protected Stage childStage;
	protected Scene scene;

	public RemoveAccountView(Stage stage, String accountName) {
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
	}

	public Scene getScene() { return this.scene; }
	public Stage getStage() { return this.childStage; }
}