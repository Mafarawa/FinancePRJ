package com.mafarawa.view.authreg;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AuthorizationView {
    protected Label errorLabel;
    protected Button userAvatar;
    protected Button doneButton;
    protected Button dropPasswordButton;
    protected PasswordField passwordInput;
    protected VBox inputLayout;
    protected Stage childStage;
    protected Scene scene;

	public AuthorizationView(Stage stage) {
        errorLabel = new Label();

        doneButton = new Button("Войти");
        dropPasswordButton = new Button("Забыл пароль");

        passwordInput = new PasswordField();
        passwordInput.setPromptText("Пароль");

        HBox buttonLayout = new HBox(20);
        buttonLayout.getChildren().addAll(doneButton, dropPasswordButton);

        inputLayout = new VBox(20);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.getChildren().addAll(passwordInput, errorLabel, buttonLayout);

        FlowPane rootLayout = new FlowPane(Orientation.VERTICAL, 20, 10);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.getChildren().addAll(inputLayout);

        scene = new Scene(rootLayout, 300, 300);

        childStage = new Stage();
        childStage.initOwner(stage);
        childStage.initModality(Modality.WINDOW_MODAL);
        childStage.setTitle("Авторизация");
        childStage.setResizable(false);
        childStage.setScene(scene);
	}

    public Scene getScene() { return this.scene; }
    public Stage getStage() { return this.childStage; }	
}