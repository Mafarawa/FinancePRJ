package com.mafarawa.controller;

import com.mafarawa.App;
import com.mafarawa.connect.DBGate;
import com.mafarawa.model.SelectScene;
import com.mafarawa.model.UserModel;
import com.mafarawa.view.RegistrationView;
import com.mafarawa.dialog.SelectImageDialog;

import javafx.stage.Stage;
import java.sql.PreparedStatement;
import org.apache.log4j.Logger;

public class RegistrationController extends RegistrationView {
	private SelectImageDialog sic;
	private static Logger logger;
	static { logger = Logger.getLogger(RegistrationController.class.getName()); }

	public RegistrationController(Stage stage) {
		super();
		sic = new SelectImageDialog(stage, super.selectImageButton);

		super.selectImageButton.setOnAction(e -> sic.getStage().show());
		super.doneButton.setOnAction(e -> registerUser());
		super.cancelButton.setOnAction(e -> stage.setScene(App.selectScene(SelectScene.SELECT_USER_SCENE)));
	}

	private void registerUser() {
		String username = super.usernameInput.getText();
		String email = super.emailInput.getText();
		String password = Integer.toHexString(super.passwordInput.getText().hashCode());
		String userImage = sic.getUserImageValue();

		if(username.isEmpty() || email.isEmpty() || password.isEmpty()) {
			checkLabel.setText("Заполните все поля");
			super.inputLayout.getChildren().add(checkLabel);
		} else {
			register(new UserModel(username, email, password, userImage));
		}
	}

	private void register(UserModel user) {
		DBGate dbGate = DBGate.getInstance();

		try {
			PreparedStatement statement = dbGate.getDatabase().prepareStatement("INSERT INTO userfp (name, email, password, image, shukher_code) VALUES(?, ?, ?, ?, ?)");
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getImageName());
			statement.setLong(5, user.getShukherCode());
			dbGate.insertData(statement);

			logger.info("user inserted: " + "\n" + user.toString());
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}

		super.checkLabel.setText("Проверте свою Эл. почту");
	}
}