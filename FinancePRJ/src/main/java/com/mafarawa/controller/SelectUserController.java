package com.mafarawa.controller;

import com.mafarawa.App;
import com.mafarawa.connect.DBGate;
import com.mafarawa.model.SelectScene;
import com.mafarawa.model.UserModel;
import com.mafarawa.view.SelectUserView;

import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.util.ArrayList;

public class SelectUserController extends SelectUserView {
	private ArrayList<UserModel> users;

	public SelectUserController(Stage stage) {
		super();

		users = new ArrayList<>();
		getUsersToDisplay();

		super.registrationButton.setOnAction(e -> stage.setScene(App.selectScene(SelectScene.REGISTRATION_SCENE)));
		for(int i = 0; i < users.size(); i++) {
			int i_ = i;
			super.userButtons.get(i).setOnAction(e -> new AutorizationController(stage, users.get(i_)).getStage().show());
		}
	}

	private void displayUsers(UserModel user) {
		super.userButtons.add(user.getAvatar());

		FlowPane userLayout = new FlowPane(100, 50);
		userLayout.setAlignment(Pos.CENTER);
		userLayout.getChildren().add(user.getAvatar());

		super.rootLayout.getChildren().add(userLayout);
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

				UserModel user = new UserModel(name, email, password, image);
				users.add(user);
				displayUsers(user);

				System.out.println("USER DISPLAYED");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}