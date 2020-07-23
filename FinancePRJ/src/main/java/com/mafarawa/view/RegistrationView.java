package com.mafarawa.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RegistrationView {
    protected Label checkLabel;
    protected TextField usernameInput;
    protected TextField emailInput;
    protected PasswordField passwordInput;
    protected Button selectImageButton;
    protected Button cancelButton;
    protected Button doneButton;
    protected VBox inputLayout;
    private final Scene scene;

    public RegistrationView() {
        checkLabel = new Label();
        usernameInput = new TextField();
        emailInput = new TextField();
        passwordInput = new PasswordField();
        selectImageButton = new Button("Выбрать аватарку");
        cancelButton = new Button("Отмена");
        doneButton = new Button("Готово");

        usernameInput.setPromptText("Имя");
        emailInput.setPromptText("Эл. почта");
        passwordInput.setPromptText("Пароль");

        Image image = new Image(getClass().getResourceAsStream("/images/whiteuser.png"), 100, 100, false, false);
        selectImageButton.setGraphic(new ImageView(image));
        selectImageButton.setContentDisplay(ContentDisplay.TOP);

        HBox buttonLayout = new HBox(50);
        buttonLayout.getChildren().addAll(cancelButton, doneButton);

        inputLayout = new VBox(20);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.getChildren().addAll(
            selectImageButton,
            usernameInput,
            emailInput,
            passwordInput,
            buttonLayout
        );

        FlowPane rootLayout = new FlowPane();
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.getChildren().addAll(inputLayout);

        scene = new Scene(rootLayout, 1000, 600);
    }

    public Scene getScene() {
        return this.scene;
    }
}
