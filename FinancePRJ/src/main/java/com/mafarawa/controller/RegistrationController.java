package com.mafarawa.controller;

import com.mafarawa.App;
import com.mafarawa.connect.DBGate;
import com.mafarawa.model.SelectScene;
import com.mafarawa.view.RegistrationView;

import javafx.stage.Stage;

import java.sql.PreparedStatement;

public class RegistrationController extends RegistrationView {
	private SelectImageController sic;

	public RegistrationController(Stage stage) {
		super();
		sic = new SelectImageController(stage, super.selectImageButton);

		super.selectImageButton.setOnAction(e -> sic.getStage().show());
		super.doneButton.setOnAction(e -> registerUser());
		super.cancelButton.setOnAction(e -> stage.setScene(App.selectScene(SelectScene.SELECT_USER_SCENE)));
	}

	private void registerUser() {
		System.out.println("REGISTERING...");
		String username = super.usernameInput.getText();
		String email = super.emailInput.getText();
		String password = Integer.toHexString(super.passwordInput.getText().hashCode());
		String userImage = sic.getUserImageValue();

		DBGate dbGate = DBGate.getInstance();
		System.out.println("INSTANCE ACHIEVED");

		try {
			PreparedStatement statement = dbGate.getDatabase().prepareStatement("INSERT INTO userfp (name, email, password, image) VALUES(?, ?, ?, ?)");
			statement.setString(1, username);
			statement.setString(2, email);
			statement.setString(3, password);
			statement.setString(4, userImage);
			dbGate.insertData(statement);
			System.out.println("USER INSERTED");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}