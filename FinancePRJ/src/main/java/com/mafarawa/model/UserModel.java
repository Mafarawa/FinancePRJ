package com.mafarawa.model;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Arrays;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class UserModel implements Cloneable {
	private String name;
	private String email;
	private String password;
	private String imageName;
	private long shukherCode;
	private ArrayList<AccountModel> accounts;

	private static Logger logger;
	static { logger = Logger.getLogger(UserModel.class.getName()); }

	public UserModel() {
		this("", "", "", UserImage.WHITE_USER.getImage());
	}

	public UserModel(UserModel user) {
		this(user.getName(), user.getEmail(), user.getPassword(), user.getImageName());
	}

	public UserModel(String name, String email, String password, String image) {
		this.accounts = new ArrayList<>();
		this.name = name;
		this.email = email;
		this.password = password;
		this.imageName = image;
		this.shukherCode = createShukherCode(name, email, password);
	}

	// This method used to create shukher code
	public static int createShukherCode(String name, String email, String password) {
		// Casting characters to integers...
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

		// Divide into areas
		String code = Arrays.toString(nameArr) + Arrays.toString(passwordArr) + Arrays.toString(emailArr);
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

		return Integer.parseInt(res);
	}

	public Button cloneUserAvatar() {
		Button avatar = new Button(this.name, new ImageView(new Image(getClass().getResourceAsStream(this.imageName), 100, 100, false, false)));
		avatar.setContentDisplay(ContentDisplay.TOP);
		
		return avatar;
	}

	@Override
	public String toString() {
		String str = "name: " + this.name + " " +
					 "email: " + this.email + " " +
					 "password: " + this.password + " " +
					 "image: " + this.imageName + " " +
					 "shukherCode: " + this.shukherCode;

		return str;
	}

	public Button getAvatar() {
		Image image = new Image(getClass().getResourceAsStream(this.imageName), 100, 100, false, false);
		Button avatar = new Button(name, new ImageView(image));
		avatar.setContentDisplay(ContentDisplay.TOP);

		return avatar;
	}

	public String getName() { return this.name; }
	public String getEmail() { return this.email; }
	public String getPassword() { return this.password; }
	public String getImageName() { return this.imageName; }
	public long getShukherCode() { return this.shukherCode; }
	public ArrayList<AccountModel> getAccounts() { return this.accounts; }

	public void setName(String name) { this.name = name; }
	public void setEmail(String email) { this.name = email; }
	public void setPassword(String password) { this.password = password; }
	public void setImageName(String imageName) { this.imageName = imageName; }
	public void setShukherCode(long shukherCode) { this.shukherCode = shukherCode; }
}