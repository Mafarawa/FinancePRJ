package com.mafarawa.model;

import javafx.scene.image.Image;

public class UserModel {
	private String name;
	private String email;
	private String password;
	private Image image;

	public UserModel(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public UserModel(String name, String email, String password, String image) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.image = new Image(getClass().getResourceAsStream(image), 100, 100, false, false);
	}

	public String getName() {
		return this.name;
	}

	public String getEmail() {
		return this.email;
	}

	public String getPassword() { return this.password; }

	public Image getImage() { return this.image; }

	public void setName(String name) { this.name = name; }

	public void setEmail(String email) { this.email = email; }

	public void setPassword(String password) { this.password = password; }

	public void setImage(String image) {
		this.image = new Image(getClass().getResourceAsStream(image), 100, 100, false, false);
	}
}