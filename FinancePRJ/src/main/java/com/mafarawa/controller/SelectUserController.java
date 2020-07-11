package com.mafarawa.controller;

import com.mafarawa.App;
import com.mafarawa.connect.DBGate;
import com.mafarawa.model.SelectScene;
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

	private void copy(File target, File dest) throws IOException {
		InputStream in = new BufferedInputStream(new FileInputStream(target));
		OutputStream out = new BufferedOutputStream(new FileOutputStream(dest));

		byte[] buffer = new byte[1024];
		int length;
		while ((length = in.read(buffer)) > 0) {
			out.write(buffer, 0, length);
			out.flush();
		}

		in.close();
		out.close();
	}

	private void getIconForUser() {
		DBGate dbgate = DBGate.getInstance();
		ResultSet rs = null;

		try {
			rs = dbgate.executeData("SELECT icon.dir, icon.name FROM icon;");
			while(rs.next()) {
				String imagePath = rs.getString(1);
				String imageName = rs.getString(2);
				System.out.println("ICON DATA SELECTED");

				File image = new File(imagePath + imageName);
				File dest = new File("/home/valera/Рабочий стол/FinancePRJ/avatars/Пенисита.jpeg");

				try {
					copy(image, dest);
					System.out.println("ICON COPIED");
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				assert rs != null;
				rs.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void getUsersToDisplay() {
		getIconForUser();

		DBGate dbgate = DBGate.getInstance();

		try {
			ResultSet rs = dbgate.executeData("SELECT userfp.name, userfp.email, userfp.password, icon.dir, icon.name FROM userfp JOIN icon ON userfp.userfp_icon_id=icon.icon_id;");
			while(rs.next()) {
				String name = rs.getString(1);
				String email = rs.getString(2);
				String password = rs.getString(3);
				String imagePath = rs.getString(4);
				String imageName = rs.getString(5);
				System.out.println("USER DATA SELECTED");

				System.out.println("name: " + name);
				System.out.println("email: " + email);
				System.out.println("password: " + password);
				System.out.println("image: " + imagePath + imageName);

				users.add(new UserModel(name, email, password, "/home/valera/Рабочий Стол/FinancePRJ/avatars/" + imageName));
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