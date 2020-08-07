package com.mafarawa.view.authreg;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class DropPasswordView {
    protected Label label;
    protected TextField shukherCodeInput;
    protected PasswordField passwordInput;
    protected PasswordField confirmPasswordInput;
    protected Button doneButton;
    protected Scene scene;
    protected Stage childStage;

    public DropPasswordView(Stage stage) {
        label = new Label("На вашу Эл. почту пришел код сброса");
        shukherCodeInput = new TextField();
        shukherCodeInput.setPromptText("Введите код сброса");

        passwordInput = new PasswordField();
        passwordInput.setPromptText("Введите пароль");
        passwordInput.setDisable(true);

        confirmPasswordInput = new PasswordField();
        confirmPasswordInput.setPromptText("Потвердите пароль");
        confirmPasswordInput.setDisable(true);

        doneButton = new Button("Сбросить пароль");

        VBox inputLayout = new VBox(10);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.getChildren().addAll(label, shukherCodeInput, passwordInput, confirmPasswordInput, doneButton);

        FlowPane rootLayout = new FlowPane(Orientation.VERTICAL, 20, 10);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.getChildren().add(inputLayout);

        scene = new Scene(rootLayout, 300, 300);

        childStage = new Stage();
        childStage.initOwner(stage);
        childStage.initModality(Modality.WINDOW_MODAL);
        childStage.setResizable(false);
        childStage.setScene(scene);
        childStage.setTitle("Сброс пароля");
        childStage.show();
    }

    public Scene getScene() { return this.scene; }
    public Stage getStage() { return this.childStage; }    
}