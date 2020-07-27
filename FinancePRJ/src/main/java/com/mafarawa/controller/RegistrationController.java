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
		long shukherCode = UserModel.createShukherCode(username, email, password);

		if(username.isEmpty() || email.isEmpty() || password.isEmpty()) {
			checkLabel.setText("Заполните все поля");
			super.inputLayout.getChildren().add(checkLabel);
		} else {
			DBGate dbGate = DBGate.getInstance();

			try {
				PreparedStatement statement = dbGate.getDatabase().prepareStatement("INSERT INTO userfp (name, email, password, image, shukher_code) VALUES(?, ?, ?, ?, ?)");
				statement.setString(1, username);
				statement.setString(2, email);
				statement.setString(3, password);
				statement.setString(4, userImage);
				statement.setLong(5, shukherCode);
				dbGate.insertData(statement);

				logger.info("user inserted: " + username + ", " + email + ", " + 
							password + ", " + userImage + ", " + shukherCode);
			} catch(Exception e) {
				logger.error("Exception: ", e);
			}

			super.checkLabel.setText("Проверте свою Эл. почту");
		}
	}
}