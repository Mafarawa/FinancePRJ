package com.mafarawa.model;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.apache.log4j.Logger;

public class UserModel implements Cloneable {
	private int id;
	private String name;
	private String email;
	private String password;
	private String imageName;
	private Image image;
	private Button avatar;
	private static Logger logger;
	static { logger = Logger.getLogger(UserModel.class.getName()); }

	public UserModel(String name, String email, String password, String image) {
		this.name = name;
		this.email = email;
		this.imageName = image;
		this.image = new Image(getClass().getResourceAsStream(image), 100, 100, false, false);
		this.avatar = new Button(name, new ImageView(this.image));
		this.avatar.setContentDisplay(ContentDisplay.TOP);
	}

	public Button cloneUserAvatar() {
		Button avatar = new Button(this.name, new ImageView(image));
		avatar.setContentDisplay(ContentDisplay.TOP);
		logger.info("Clone achieved");
		return avatar;
	}

	@Override
	public String toString() {
		String str = "id: " + this.id + "\n" +
				 	 "name: " + this.name + "\n" +
					 "email: " + this.email + "\n" +
					 "password: " + this.password + "\n" +
					 "image: " + this.imageName;

		return str;
	}

	public String getName() {
		return this.name;
	}

	public Button getAvatar() { return this.avatar; }
}