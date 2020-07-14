package com.mafarawa.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class SelectUserView {
	protected Button registrationButton;
	protected ArrayList<Button> userButtons;

	private final Scene scene;
	protected VBox rootLayout;

	public SelectUserView() {
		userButtons = new ArrayList<>();

		registrationButton = new Button("Зарегистрироватся");

		rootLayout = new VBox(50);
		rootLayout.setAlignment(Pos.CENTER);
		rootLayout.getChildren().add(registrationButton);

		scene = new Scene(rootLayout, 1000, 600);
	}

	public Scene getScene() {
		return this.scene;
	}
}