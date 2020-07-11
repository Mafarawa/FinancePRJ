package com.mafarawa.controller;

import com.mafarawa.App;
import com.mafarawa.connect.DBGate;
import com.mafarawa.model.SelectScene;
import com.mafarawa.view.RegistrationView;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegistrationController extends RegistrationView {
	private Stage stage;
	private File imageFile;
	private FileChooser fileChooser;

	public RegistrationController(Stage stage) {
		super();

		fileChooser = new FileChooser();

		super.selectImageButton.setOnAction(e -> imageFile = fileChooser.showOpenDialog(stage));
		super.doneButton.setOnAction(e -> registerUser());
		super.cancelButton.setOnAction(e -> stage.setScene(App.selectScene(SelectScene.SELECT_USER_SCENE)));
	}

	private void registerUser() {
		System.out.println("REGISTERING...");
		String username = super.usernameInput.getText();
		String email = super.emailInput.getText();
		String password = super.passwordInput.getText();
		String imagePath = imageFile.getParent() + "/";
		String imageName = imageFile.getName();

		DBGate dbGate = DBGate.getInstance();
		System.out.println("INSTANCCE ACHIEVED");

		try {
			PreparedStatement statement = dbGate.getDatabase().prepareStatement("INSERT INTO icon (dir, name) VALUES(?, ?)");
			statement.setString(1, imagePath);
			statement.setString(2, imageName);
			dbGate.insertData(statement);
			System.out.println("ICON INSERTED");

			int icon_id = 0;
			ResultSet rs = dbGate.executeData("SELECT icon.icon_id FROM icon;");
			while(rs.next()) {
				icon_id = rs.getInt(1);
			}
			System.out.println("ICON SELECTED");

			statement = dbGate.getDatabase().prepareStatement("INSERT INTO userfp (name, email, password, userfp_icon_id) VALUES(?, ?, ?, ?)");
			statement.setString(1, username);
			statement.setString(2, email);
			statement.setString(3, password);
			statement.setInt(4, icon_id);
			dbGate.insertData(statement);
			System.out.println("USER INSERTED");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}