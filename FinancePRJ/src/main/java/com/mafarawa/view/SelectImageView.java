package com.mafarawa.view;

import com.mafarawa.model.UserImage;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class SelectImageView {
    protected Stage childStage;
    private Scene scene;
    protected Button blackUserButton;
    protected Button blueUserButton;
    protected Button greenUserButton;
    protected Button redUserButton;
    protected Button whiteUserButton;
    protected Button yellowUserButton;

    public SelectImageView(Stage stage) {
         blackUserButton = new Button("", new ImageView(new Image(UserImage.BLACK_USER.getImage(), 100, 100, false, false)));
         blueUserButton = new Button("", new ImageView(new Image(UserImage.BLUE_USER.getImage(), 100, 100, false, false)));
         greenUserButton = new Button("", new ImageView(new Image(UserImage.GREEN_USER.getImage(), 100, 100, false, false)));
         redUserButton = new Button("", new ImageView(new Image(UserImage.RED_USER.getImage(), 100, 100, false, false)));
         whiteUserButton = new Button("", new ImageView(new Image(UserImage.WHITE_USER.getImage(), 100, 100, false, false)));
         yellowUserButton = new Button("", new ImageView(new Image(UserImage.YELLOW_USER.getImage(), 100, 100, false, false)));

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
    }

    public Scene getScene() { return this.scene; }

    public Stage getStage() { return this.childStage; }
}