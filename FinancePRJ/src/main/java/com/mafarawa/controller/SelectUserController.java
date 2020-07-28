package com.mafarawa.controller;

import com.mafarawa.App;
import com.mafarawa.connect.DBGate;
import com.mafarawa.dialog.AutorizationDialog;
import com.mafarawa.model.SelectScene;
import com.mafarawa.model.UserModel;
import com.mafarawa.view.SelectUserView;

import java.sql.ResultSet;
import java.util.ArrayList;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.apache.log4j.Logger;

public class SelectUserController extends SelectUserView {
	private ArrayList<UserModel> users;
	
	private static Logger logger;
	static { logger = Logger.getLogger(SelectUserController.class.getName()); }

	public SelectUserController(Stage stage) {
		super();

		users = new ArrayList<>();
		getUsersToDisplay();

		super.registrationButton.setOnAction(e -> stage.setScene(App.selectScene(SelectScene.REGISTRATION_SCENE)));
		for(int i = 0; i < users.size(); i++) {
			int i_ = i;
			super.userButtons.get(i).setOnAction(e -> new AutorizationDialog(stage, users.get(i_)).getStage().show());
		}
	}

	private void displayUsers(UserModel user) {
		Button avatar = user.getAvatar();

		super.userButtons.add(avatar);
		super.userLayout.getChildren().add(avatar);
		logger.info("User: " + user.getName() + " on screen");
	}

	private void getUsersToDisplay() {
		DBGate dbgate = DBGate.getInstance();

		try {
			ResultSet rs = dbgate.executeData("SELECT userfp.name, userfp.email, userfp.password, userfp.image FROM userfp;");
			while(rs.next()) {
				String name = rs.getString(1);
				String email = rs.getString(2);
				String password = rs.getString(3);
				String image = rs.getString(4);

				UserModel user = new UserModel(name, email, password, image);
				users.add(user);
				displayUsers(user);
			}
		} catch(Exception e) {
			logger.error("Exception: ", e);
		}
	}
}