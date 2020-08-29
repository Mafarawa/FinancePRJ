package com.mafarawa.controller.authreg;

import com.mafarawa.view.authreg.AuthorizationView;
import com.mafarawa.connect.DBGate;
import com.mafarawa.model.UserModel;
import com.mafarawa.view.MainWindow;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.sql.ResultSet;
import org.apache.log4j.Logger;

public class AuthorizationController extends AuthorizationView {
    private static Logger logger;
    static { logger = Logger.getLogger(AuthorizationController.class.getName()); }

    public AuthorizationController(Stage stage, UserModel user) {
    	super(stage);

        setUserAvatar(user.cloneUserAvatar());
        super.doneButton.setOnAction(e -> authorizeUser(user.getName(), stage));
        super.dropPasswordButton.setOnAction(e -> new DropPasswordController(childStage, user).getStage().show());
    }

    // This method used to display users avatar
    private void setUserAvatar(Button userAvatar) {
        super.userAvatar = userAvatar;
        super.userAvatar.setDisable(true);
        super.inputLayout.getChildren().add(0, super.userAvatar);
    }

    // This method used to check if the password that user entered is forge
    private void authorizeUser(String username, Stage stage) {
        DBGate dbGate = DBGate.getInstance();

        // Hashing password that user entered to compare it with hash which contains in database
        String passwordInputValue = Integer.toHexString(super.passwordInput.getText().hashCode());
        logger.debug("User: " + username + " try to authorize with hash: " + passwordInputValue);

        try {
            ResultSet rs = dbGate.executeData("SELECT userfp.password FROM userfp WHERE userfp.name='" + username + "';");
            while(rs.next()) {
                if(passwordInputValue.equals(rs.getString(1))) { // Comparing hashes... If true access granted to user
                    MainWindow mw = new MainWindow(stage, username);
                    stage.setScene(mw.getScene());
                    childStage.close();
                    logger.info("CORRECT PASSWORD");
                } else {
                    errorLabel.setText("Не правильный пароль!");
                    logger.info("WRONG PASSWORD");
                }
            }
        } catch (Exception e) {
            logger.error("Exception: ", e);
        }
    }

} 