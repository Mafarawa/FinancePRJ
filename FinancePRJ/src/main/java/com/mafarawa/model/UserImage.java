package com.mafarawa.model;

public enum UserImage {
    BLACK_USER("/images/user/blackuser.png"),
    BLUE_USER("/images/user/blueuser.png"),
    GREEN_USER("/images/user/greenuser.png"),
    RED_USER("/images/user/reduser.png"),
    WHITE_USER("/images/user/whiteuser.png"),
    YELLOW_USER("/images/user/yellowuser.png");

    private String image;

    UserImage(String image) { this.image = image; }

    public String getImage() { return image; }
}
