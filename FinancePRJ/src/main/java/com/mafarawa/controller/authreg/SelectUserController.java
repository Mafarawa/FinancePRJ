package com.mafarawa.controller.authreg;

import com.mafarawa.App;
import com.mafarawa.connect.DBGate;
import com.mafarawa.model.SelectScene;
import com.mafarawa.model.UserModel;
import com.mafarawa.view.authreg.SelectUserView;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SelectUserController extends SelectUserView {
	private ArrayList<UserModel> users;
	private ArrayList<AuthorizationController> authDialogs;
	
	private static Logger logger;
	static { logger = Logger.getLogger(SelectUserController.class.getName()); }

	public SelectUserController(Stage stage) {
		super();

		authDialogs = new ArrayList<>();
		users = new ArrayList<>();
		getUsersToDisplay();

		for(int i = 0; i < users.size(); i++) {
			authDialogs.add(new AuthorizationController(stage, users.get(i)));
		}

		super.registrationButton.setOnAction(e -> stage.setScene(App.selectScene(SelectScene.REGISTRATION_SCENE)));
		for(int i = 0; i < users.size(); i++) {
			int i_ = i;
			super.userButtons.get(i).setOnAction(e -> authDialogs.get(i_).getStage().show());
		}
	}

	// This method used to display users
	private void displayUsers(UserModel user) {
		Button avatar = user.getAvatar();

		super.userButtons.add(avatar);
		super.userLayout.getChildren().add(avatar);
		logger.info("User: " + user.getName() + " on screen");
	}

	// This method used to execute users in order to display them
	private void getUsersToDisplay() {
		DBGate dbGate = DBGate.getInstance();

		try {
			ResultSet rs = dbGate.executeData("SELECT userfp.name, userfp.email, userfp.password, user_image.image_path FROM userfp JOIN user_image ON user_image.id = userfp.image;");
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