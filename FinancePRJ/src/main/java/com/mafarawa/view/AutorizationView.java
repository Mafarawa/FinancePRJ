package com.mafarawa.view;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AutorizationView {
    protected Button userAvatar;
    protected Button doneButton;
    protected PasswordField passwordInput;
    protected VBox inputLayout;

    private final Scene scene;
    private Stage childStage;

    public AutorizationView(Stage stage) {
        doneButton = new Button("Войти");

        passwordInput = new PasswordField();
        passwordInput.setPromptText("Пароль");

        inputLayout = new VBox(20);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.getChildren().addAll(passwordInput, doneButton);

        FlowPane rootLayout = new FlowPane(Orientation.VERTICAL, 20, 10);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.getChildren().addAll(inputLayout);

        scene = new Scene(rootLayout, 250, 200);

        this.childStage = new Stage();
        this.childStage.initOwner(stage);
        this.childStage.initModality(Modality.WINDOW_MODAL);
        this.childStage.setTitle("Авторизация");
        this.childStage.setResizable(false);
        this.childStage.setScene(scene);

    }

    public Scene getScene() { return this.scene; }
    public Stage getStage() { return this.childStage; }
}