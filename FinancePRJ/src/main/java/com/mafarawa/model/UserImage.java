package com.mafarawa.model;

public enum UserImage {
    BLACK_USER(0, "/images/user/blackuser.png"),
    BLUE_USER(1, "/images/user/blueuser.png"),
    GREEN_USER(2, "/images/user/greenuser.png"),
    RED_USER(3, "/images/user/reduser.png"),
    WHITE_USER(4, "/images/user/whiteuser.png"),
    YELLOW_USER(5, "/images/user/yellowuser.png");

    private int userImageId;
    private String userImage;

    UserImage(int userImageId, String userImage) {
    	this.userImageId = userImageId;
    	this.userImage = userImage;
    }

    public static int getIdByImage(String image) {
    	for(UserImage temp : values()) {
    		if(temp.userImage == image) return temp.getId();
    	}

        return 0;
    }

    public static String getImageById(int id) {
    	for(UserImage temp : values()) {
    		if(temp.userImageId == id) return temp.getImage();
    	}

        return "";
    }

    public int getId() { return userImageId; }
    public String getImage() { return userImage; }
}
