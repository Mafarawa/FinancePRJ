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
import java.util.ArrayList;

public class SelectImageView {
    protected ArrayList<Button> buttons;
    protected Stage childStage;
    protected Scene scene;

    public SelectImageView(Stage stage) {
        buttons = new ArrayList<>();
        buttons.add(new Button(""));
        buttons.add(new Button(""));
        buttons.add(new Button(""));
        buttons.add(new Button(""));
        buttons.add(new Button(""));
        buttons.add(new Button(""));

        GridPane selectImageLayout = new GridPane();
        selectImageLayout.setHgap(20);
        selectImageLayout.setVgap(20);
        selectImageLayout.setAlignment(Pos.CENTER);

        int i = buttons.size() - 1;
        for(int row = 0; row <= 2; row++) {
            for(int column = 0; column <= 1; column++) {
                buttons.get(i).setGraphic(new ImageView(new Image(getClass().getResourceAsStream(UserImage.getImageById(i)), 100, 100, false, false)));            
                selectImageLayout.add(buttons.get(i), column, row);
                i--;
            }
        }

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