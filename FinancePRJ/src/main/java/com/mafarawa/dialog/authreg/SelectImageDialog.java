package com.mafarawa.dialog.authreg;

import com.mafarawa.model.UserImage;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SelectImageDialog {
    private Stage childStage;
    private Button blackUserButton;
    private Button blueUserButton;
    private Button greenUserButton;
    private Button redUserButton;
    private Button whiteUserButton;
    private Button yellowUserButton;
    private Scene scene;
    private int userImageValue;

    public SelectImageDialog(Stage stage, Button button) {
        blackUserButton = new Button("", new ImageView(
                new Image(getClass().getResourceAsStream(UserImage.getImageById(1)), 100, 100, false, false)
        ));

        blueUserButton = new Button("", new ImageView(
                new Image(getClass().getResourceAsStream(UserImage.getImageById(2)), 100, 100, false, false)
        ));

        greenUserButton = new Button("", new ImageView(
                new Image(getClass().getResourceAsStream(UserImage.getImageById(3)), 100, 100, false, false)
        ));

        redUserButton = new Button("", new ImageView(
                new Image(getClass().getResourceAsStream(UserImage.getImageById(4)), 100, 100, false, false)
        ));

        whiteUserButton = new Button("", new ImageView(
                new Image(getClass().getResourceAsStream(UserImage.getImageById(5)), 100, 100, false, false)
        ));
        
        yellowUserButton = new Button("", new ImageView(
                new Image(getClass().getResourceAsStream(UserImage.getImageById(6)), 100, 100, false, false)
        ));

        GridPane selectImageLayout = new GridPane();
        selectImageLayout.setHgap(20);
        selectImageLayout.setVgap(20);
        selectImageLayout.setAlignment(Pos.CENTER);
        selectImageLayout.add(blackUserButton, 0, 0);
        selectImageLayout.add(blueUserButton, 1, 0);
        selectImageLayout.add(greenUserButton, 0, 2);
        selectImageLayout.add(redUserButton, 1, 2);
        selectImageLayout.add(whiteUserButton, 0, 3);
        selectImageLayout.add(yellowUserButton, 1, 3);

        scene = new Scene(selectImageLayout, 300, 400);

        this.childStage = new Stage();
        this.childStage.initOwner(stage);
        this.childStage.initModality(Modality.WINDOW_MODAL);
        this.childStage.setTitle("Выберите аватарку");
        this.childStage.setResizable(false);
        this.childStage.setScene(scene);

        userImageValue = 1;

        blackUserButton.setOnAction(e -> {
            setUserImageValue(UserImage.getImageById(1));
            Image image = new Image(getClass().getResourceAsStream(UserImage.getImageById(1)), 100, 100, false, false);
            button.setGraphic(new ImageView(image));
            childStage.close();
        });

        blueUserButton.setOnAction(e -> {
            setUserImageValue(UserImage.getImageById(2));
            Image image = new Image(getClass().getResourceAsStream(UserImage.getImageById(2)), 100, 100, false, false);
            button.setGraphic(new ImageView(image));
            childStage.close();
        });

        greenUserButton.setOnAction(e -> {
            setUserImageValue(UserImage.getImageById(3));
            Image image = new Image(getClass().getResourceAsStream(UserImage.getImageById(3)), 100, 100, false, false);
            button.setGraphic(new ImageView(image));
            childStage.close();
        });

        redUserButton.setOnAction(e -> {
            setUserImageValue(UserImage.getImageById(4));
            Image image = new Image(getClass().getResourceAsStream(UserImage.getImageById(4)), 100, 100, false, false);
            button.setGraphic(new ImageView(image));
            childStage.close();
        });

        whiteUserButton.setOnAction(e -> {
            setUserImageValue(UserImage.getImageById(5));
            Image image = new Image(getClass().getResourceAsStream(UserImage.getImageById(5)), 100, 100, false, false);
            button.setGraphic(new ImageView(image));
            childStage.close();
        });

        yellowUserButton.setOnAction(e -> {
            setUserImageValue(UserImage.getImageById(6));
            Image image = new Image(getClass().getResourceAsStream(UserImage.getImageById(6)), 100, 100, false, false);
            button.setGraphic(new ImageView(image));
            childStage.close();
        });
    }

    private void setUserImageValue(String selectedImage) {
        userImageValue = UserImage.getIdByImage(selectedImage);
    }

    public int getUserImageValue() { return this.userImageValue; }
    public Scene getScene() { return this.scene; }
    public Stage getStage() { return this.childStage; }
}