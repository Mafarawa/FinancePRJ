package com.mafarawa.view.authreg;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class SelectUserView {
	protected Button registrationButton;
	protected ArrayList<Button> userButtons;
	protected VBox rootLayout;
	protected HBox userLayout;
	private final Scene scene;

	public SelectUserView() {
		userButtons = new ArrayList<>();

		userLayout = new HBox(50);
		userLayout.setAlignment(Pos.CENTER);

		registrationButton = new Button("Зарегистрироватся");

		rootLayout = new VBox(50);
		rootLayout.setAlignment(Pos.CENTER);
		rootLayout.getChildren().add(0, registrationButton);
		rootLayout.getChildren().add(userLayout);

		scene = new Scene(rootLayout, 1000, 600);
	}

	public Scene getScene() { return this.scene; }
}