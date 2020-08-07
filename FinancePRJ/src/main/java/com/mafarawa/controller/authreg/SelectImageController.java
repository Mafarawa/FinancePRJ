package com.mafarawa.controller.authreg;

import com.mafarawa.model.UserImage;
import com.mafarawa.view.authreg.SelectImageView;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SelectImageController extends SelectImageView {
    private int userImageValue;

	public SelectImageController(Stage stage, Button button) {
		super(stage);

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
}