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

	public UserModel() {
		this("", "", "", UserImage.WHITE_USER.getImage());
	}

	public UserModel(UserModel user) {
		this(user.getName(), user.getEmail(), user.getPassword(), user.getImageName());
	}

	public UserModel(String name, String email, String password, String image) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.imageName = image;
		this.shukherCode = createShukherCode(name, email, password);
		this.image = new Image(getClass().getResourceAsStream(image), 100, 100, false, false);
		avatar = new Button(name, new ImageView(this.image));
		avatar.setContentDisplay(ContentDisplay.TOP);

		logger.debug("New UserModel: " + this.toString());
	}

	public static int createShukherCode(String name, String email, String password) {
		int[] nameArr = new int[name.length()];
		for(int i = 0; i < name.length(); i++) {
			nameArr[i] = name.charAt(i);
		}

		int[] emailArr = new int[email.length()];
		for(int i = 0; i < email.length(); i++) {
			emailArr[i] = email.charAt(i);
		}

		int[] passwordArr = new int[password.length()];
		for(int i = 0; i < password.length(); i++) {
			passwordArr[i] = password.charAt(i);
		}

		String code = nameArr.toString() + passwordArr.toString() + emailArr.toString();
		String leftSide = code.substring(0, code.length() / 2);
		String rightSide = code.substring(code.length() / 2, code.length() - 1);

		int[] shukherCodeArr = new int[6];
		shukherCodeArr[0] = leftSide.charAt(0);
		shukherCodeArr[1] = leftSide.charAt(leftSide.length() / 2);
		shukherCodeArr[2] = leftSide.charAt(leftSide.length() - 1);
		shukherCodeArr[3] = rightSide.charAt(0);
		shukherCodeArr[4] = rightSide.charAt(rightSide.length() / 2);
		shukherCodeArr[5] = rightSide.charAt(rightSide.length() - 1);

		String toReturn = "";
		for(int i = 0; i < shukherCodeArr.length; i++) {
			toReturn += shukherCodeArr[i];
		}

		int leftSideInt = Integer.parseInt(toReturn.substring(0, toReturn.length() / 2));
		int rightSideInt = Integer.parseInt(toReturn.substring(toReturn.length() / 2, toReturn.length() - 1));
		String res = String.valueOf(leftSideInt + rightSideInt);

		if(Integer.parseInt(res) > 6) {
			res = res.substring(0, 6);
		}

		logger.debug("Total ShukherCode is: " + Integer.parseInt(res));

		return Integer.parseInt(res);
	}

	public Button cloneUserAvatar() {
		Button avatar = new Button(this.name, new ImageView(image));
		avatar.setContentDisplay(ContentDisplay.TOP);
		logger.info("UserAvatar clone created");
		return avatar;
	}

	@Override
	public String toString() {
		String str = "id: " + this.id + " " +
				 	 "name: " + this.name + " " +
					 "email: " + this.email + " " +
					 "password: " + this.password + " " +
					 "image: " + this.imageName + " " +
					 "shukherCode: " + this.shukherCode;

		return str;
	}

	public String getName() { return this.name; }
	public String getEmail() { return this.email; }
	public String getPassword() { return this.password; }
	public String getImageName() { return this.imageName; }
	public long getShukherCode() { return this.shukherCode; }
	public Button getAvatar() { return this.avatar; }

	public void setName(String name) { this.name = name; }
	public void setEmail(String email) { this.name = email; }
	public void setPassword(String password) { this.password = password; }
	public void setImageName(String imageName) { this.imageName = imageName; }
	public void setShukherCode(long shukherCode) { this.shukherCode = shukherCode; }
	public void setAvatar(Button avatar) { this.avatar = avatar; }
}