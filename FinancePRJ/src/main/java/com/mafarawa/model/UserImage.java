package com.mafarawa.model;

import javafx.scene.image.Image;

public enum UserImage {
    BLACK_USER("/images/blackuser.png"),
    BLUE_USER("/images/blueuser.png"),
    GREEN_USER("/images/greenuser.png"),
    RED_USER("/images/reduser.png"),
    WHITE_USER("/images/whiteuser.png"),
    YELLOW_USER("/images/yellowuser.png");

    private String image;

    UserImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }
}
