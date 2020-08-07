package com.mafarawa.view.authreg;

import com.mafarawa.model.UserImage;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SelectImageView {
    protected Stage childStage;
    protected Button blackUserButton;
    protected Button blueUserButton;
    protected Button greenUserButton;
    protected Button redUserButton;
    protected Button whiteUserButton;
    protected Button yellowUserButton;
    protected Scene scene;

    public SelectImageView(Stage stage) {
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
        selectImageLayout.add(greenUserButton, 0, 1);
        selectImageLayout.add(redUserButton, 1, 1);
        selectImageLayout.add(whiteUserButton, 0, 2);
        selectImageLayout.add(yellowUserButton, 1, 2);

        scene = new Scene(selectImageLayout, 300, 400);

        childStage = new Stage();
        childStage.initOwner(stage);
        childStage.initModality(Modality.WINDOW_MODAL);
        childStage.setTitle("Выберите аватарку");
        childStage.setResizable(false);
        childStage.setScene(scene);
    }

    public Scene getScene() { return this.scene; }
    public Stage getStage() { return this.childStage; }
}