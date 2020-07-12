package com.mafarawa.controller;

import com.mafarawa.App;
import com.mafarawa.connect.DBGate;
import com.mafarawa.model.SelectScene;
import com.mafarawa.model.UserImage;
import com.mafarawa.model.UserModel;
import com.mafarawa.view.SelectUserView;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SelectUserController extends SelectUserView {
	private ArrayList<UserModel> users;

	public SelectUserController(Stage stage) {
		super();
		super.registrationButton.setOnAction(e -> stage.setScene(App.selectScene(SelectScene.REGISTRATION_SCENE)));

		users = new ArrayList<>();

		getUsersToDisplay();
		super.displayUsers();
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
				System.out.println("USER DATA SELECTED");

				System.out.println("name: " + name);
				System.out.println("email: " + email);
				System.out.println("password: " + password);
				System.out.println("image: " + image);

				UserModel user = new UserModel(name, email, password);
				switch(image) {
					case "/images/blackuser.png" 	: user.setImage(UserImage.BLACK_USER.getImage()); break;
					case "/images/blueuser.png" 	: user.setImage(UserImage.BLUE_USER.getImage()); break;
					case "/images/greenuser.png" 	: user.setImage(UserImage.GREEN_USER.getImage()); break;
					case "/images/reduser.png" 		: user.setImage(UserImage.RED_USER.getImage()); break;
					case "/images/whiteuser.png"	: user.setImage(UserImage.WHITE_USER.getImage()); break;
					case "/images/yellowuser.png" 	: user.setImage(UserImage.YELLOW_USER.getImage()); break;
					default							: user.setImage(UserImage.WHITE_USER.getImage()); break;
				}

				users.add(user);
				System.out.println("USER DISPLAYED");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		for(UserModel user : users) {
			super.userButtons.add(new Button(user.getName(), new ImageView(user.getImage())));
		}
	}
}