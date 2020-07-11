package com.mafarawa.view;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

import java.util.ArrayList;

public class SelectUserView {
	protected Button registrationButton;
	protected ArrayList<Button> userButtons;

	private Scene scene;
	private VBox rootLayout;

	public SelectUserView() {
		registrationButton = new Button("Зарегистрироватся");
		userButtons = new ArrayList<>();

		rootLayout = new VBox(50);
		rootLayout.setAlignment(Pos.CENTER);
		rootLayout.getChildren().add(registrationButton);

		scene = new Scene(rootLayout, 1000, 600);
	}

	public Button getRegistrationButton() {
		return this.registrationButton;
	}

	public Scene getScene() { 
		return this.scene; 
	}

	protected void displayUsers() {
		FlowPane userLayout = new FlowPane(100, 50);
		userLayout.setAlignment(Pos.CENTER);
		for(Button button : userButtons) {
			button.setContentDisplay(ContentDisplay.TOP);
			userLayout.getChildren().add(button);
		}

		rootLayout.getChildren().add(userLayout);
	}
}