package com.mafarawa.model;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserModel {
	private int id;
	private String name;
	private String email;
	private String password;
	private Image image;
	private Button avatar;

	public UserModel(String name, String email, String password, String image) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.image = new Image(getClass().getResourceAsStream(image), 100, 100, false, false);
		this.avatar = new Button(name, new ImageView(this.image));
		this.avatar.setContentDisplay(ContentDisplay.TOP);
	}

	public String getName() {
		return this.name;
	}

	public String getEmail() {
		return this.email;
	}

	public Image getImage() { return this.image; }

	public Button getAvatar() { return this.avatar; }
}