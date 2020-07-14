package com.mafarawa.controller;

import com.mafarawa.connect.DBGate;
import com.mafarawa.model.UserModel;
import com.mafarawa.view.AutorizationView;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class AutorizationController extends AutorizationView {
    public AutorizationController(Stage stage, UserModel user) {
        super(stage);
        setUserAvatar(user.getAvatar());
        super.doneButton.setOnAction(e -> autorizeUser(user.getName()));
    }

    private void setUserAvatar(Button userAvatar) {
        super.userAvatar = userAvatar;
        super.userAvatar.setDisable(true);
        super.inputLayout.getChildren().add(0, super.userAvatar);
    }

    private void autorizeUser(String username) {
        DBGate dbGate = DBGate.getInstance();
        String passwordInputValue = Integer.toHexString(super.passwordInput.getText().hashCode());

        try {
            ResultSet rs = dbGate.executeData("SELECT userfp.password FROM userfp WHERE userfp.name='" + username + "';");
            while (rs.next()) {
                if(passwordInputValue == rs.getString(1)) {
                    System.out.println("USER AUTORIZED");
                } else {
                    System.out.println("WRONG PASSOWORD");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
