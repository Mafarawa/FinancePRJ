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

        for(int i = super.buttons.size() - 1; i > 0; i--) {
            int i_ = i;
            super.buttons.get(i).setOnAction(e -> {
                setUserImageValue(UserImage.getImageById(i_));
                Image image = new Image(getClass().getResourceAsStream(UserImage.getImageById(i_)), 100, 100, false, false);
                button.setGraphic(new ImageView(image));
                childStage.close();
            });
        }
	}

    // This method used to remember selected image
    private void setUserImageValue(String selectedImage) {
        userImageValue = UserImage.getIdByImage(selectedImage);
    }

    public int getUserImageValue() { return this.userImageValue; }    
}