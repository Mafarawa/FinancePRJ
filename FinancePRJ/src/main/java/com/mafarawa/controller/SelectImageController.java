package com.mafarawa.controller;

import com.mafarawa.model.UserImage;
import com.mafarawa.view.SelectImageView;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SelectImageController extends SelectImageView {
    private String userImageValue;

    public SelectImageController(Stage stage, Button button) {
        super(stage);

        this.userImageValue = UserImage.WHITE_USER.getImage();

        super.blackUserButton.setOnAction(e -> {
            setUserImageValue(UserImage.BLACK_USER.getImage());
            Image image = new Image(getClass().getResourceAsStream(userImageValue), 100, 100, false, false);
            button.setGraphic(new ImageView(image));
            super.childStage.close();
        });
        super.blueUserButton.setOnAction(e -> {
            setUserImageValue(UserImage.BLUE_USER.getImage());
            Image image = new Image(getClass().getResourceAsStream(userImageValue), 100, 100, false, false);
            button.setGraphic(new ImageView(image));
            super.childStage.close();
        });
        super.greenUserButton.setOnAction(e -> {
            setUserImageValue(UserImage.GREEN_USER.getImage());
            Image image = new Image(getClass().getResourceAsStream(userImageValue), 100, 100, false, false);
            button.setGraphic(new ImageView(image));
            super.childStage.close();
        });
        super.redUserButton.setOnAction(e -> {
            setUserImageValue(UserImage.RED_USER.getImage());
            Image image = new Image(getClass().getResourceAsStream(userImageValue), 100, 100, false, false);
            button.setGraphic(new ImageView(image));
            super.childStage.close();
        });
        super.whiteUserButton.setOnAction(e -> {
            setUserImageValue(UserImage.WHITE_USER.getImage());
            Image image = new Image(getClass().getResourceAsStream(userImageValue), 100, 100, false, false);
            button.setGraphic(new ImageView(image));
            super.childStage.close();
        });
        super.yellowUserButton.setOnAction(e -> {
            setUserImageValue(UserImage.YELLOW_USER.getImage());
            Image image = new Image(getClass().getResourceAsStream(userImageValue), 100, 100, false, false);
            button.setGraphic(new ImageView(image));
            super.childStage.close();
        });
    }

    private void setUserImageValue(String userImageValue) {
        this.userImageValue = userImageValue;
    }

    public String getUserImageValue() {
        return this.userImageValue;
    }
}
