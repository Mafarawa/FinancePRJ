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
	private long shukherCode;
	private Image image;
	private Button avatar;
	private static Logger logger;
	static { logger = Logger.getLogger(UserModel.class.getName()); }

	public UserModel(String name, String email, String password, String image) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.imageName = image;
		this.shukherCode = createShukherCode();
		this.image = new Image(getClass().getResourceAsStream(image), 100, 100, false, false);
		this.avatar = new Button(name, new ImageView(this.image));
		this.avatar.setContentDisplay(ContentDisplay.TOP);
	}

	public long createShukherCode() {
		int[] nameArr = new int[this.name.length()];
		for(int i = 0; i < this.name.length(); i++) {
			nameArr[i] =  this.name.charAt(i);
		}

		int[] emailArr = new int[this.email.length()];
		for(int i = 0; i < this.email.length(); i++) {
			emailArr[i] =  this.email.charAt(i);
		}

		String code = nameArr.toString() + emailArr.toString();
		String leftSide = code.substring(0, code.length() / 2);

		int[] shukherCodeArr = new int[3];
		shukherCodeArr[0] = leftSide.charAt(0);
		shukherCodeArr[1] = leftSide.charAt(leftSide.length() / 2);
		shukherCodeArr[2] = leftSide.charAt(leftSide.length() - 1);

		String toReturn = "";
		for(int i = 0; i < shukherCodeArr.length; i++) {
			toReturn += shukherCodeArr[i];
			System.out.println(shukherCodeArr[i]);
		}

		if(toReturn.length() > 6) {
			toReturn = toReturn.substring(0, 6);
		}

		return Long.parseLong(toReturn);
	}

	public Button cloneUserAvatar() {
		Button avatar = new Button(this.name, new ImageView(image));
		avatar.setContentDisplay(ContentDisplay.TOP);
		logger.info("Clone achieved");
		return avatar;
	}

	@Override
	public String toString() {
		String str = "id: " + this.id +
				 	 "name: " + this.name +
					 "email: " + this.email +
					 "password: " + this.password +
					 "image: " + this.imageName +
					 "shukherCode: " + this.shukherCode;

		return str;
	}

	public String getName() {
		return this.name;
	}

	public String getEmail() { return this.email; }

	public String getPassword() { return this.password; }

	public String getImageName() { return this.imageName; }

	public long getShukherCode() { return this.shukherCode; }

	public Button getAvatar() { return this.avatar; }
}