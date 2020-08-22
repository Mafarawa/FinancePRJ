package com.mafarawa.controller.authreg;

import com.mafarawa.view.authreg.AuthorizationView;
import com.mafarawa.controller.authreg.DropPasswordController;
import com.mafarawa.connect.DBGate;
import com.mafarawa.model.UserModel;
import com.mafarawa.model.SelectScene;
import com.mafarawa.view.main.MainWindow;
import com.mafarawa.App;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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

    private void setUserAvatar(Button userAvatar) {
        super.userAvatar = userAvatar;
        super.userAvatar.setDisable(true);
        super.inputLayout.getChildren().add(0, super.userAvatar);
    }

    private void authorizeUser(String username, Stage stage) {
        DBGate dbGate = DBGate.getInstance();
        String passwordInputValue = Integer.toHexString(super.passwordInput.getText().hashCode());

        logger.debug("User: " + username + " try to authorize with password: " + passwordInputValue);

        try {
            ResultSet rs = dbGate.executeData("SELECT userfp.password FROM userfp WHERE userfp.name='" + username + "';");
            while (rs.next()) {
                if(passwordInputValue.equals(rs.getString(1))) {
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