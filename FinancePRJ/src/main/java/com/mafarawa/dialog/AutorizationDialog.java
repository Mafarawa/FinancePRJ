package com.mafarawa.dialog;

import com.mafarawa.connect.DBGate;
import com.mafarawa.model.UserModel;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.ResultSet;
import org.apache.log4j.Logger;


public class AutorizationDialog {
    private Button userAvatar;
    private Button doneButton;
    private PasswordField passwordInput;
    private VBox inputLayout;
    private Stage childStage;
    private Scene scene;
    private static Logger logger;
    static { logger = Logger.getLogger(AutorizationDialog.class.getName()); }

    public AutorizationDialog(Stage stage, UserModel user) {
        doneButton = new Button("Войти");

        passwordInput = new PasswordField();
        passwordInput.setPromptText("Пароль");

        inputLayout = new VBox(20);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.getChildren().addAll(passwordInput, doneButton);

        FlowPane rootLayout = new FlowPane(Orientation.VERTICAL, 20, 10);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.getChildren().addAll(inputLayout);

        scene = new Scene(rootLayout, 250, 250);

        this.childStage = new Stage();
        this.childStage.initOwner(stage);
        this.childStage.initModality(Modality.WINDOW_MODAL);
        this.childStage.setTitle("Авторизация");
        this.childStage.setResizable(false);
        this.childStage.setScene(scene);

        setUserAvatar(user.cloneUserAvatar());
        doneButton.setOnAction(e -> autorizeUser(user.getName()));
    }

    private void setUserAvatar(Button userAvatar) {
        userAvatar = userAvatar;
        userAvatar.setDisable(true);
        inputLayout.getChildren().add(0, userAvatar);
    }

    private void autorizeUser(String username) {
        DBGate dbGate = DBGate.getInstance();
        String passwordInputValue = Integer.toHexString(passwordInput.getText().hashCode());
        System.out.println(passwordInputValue);

        try {
            ResultSet rs = dbGate.executeData("SELECT userfp.password FROM userfp WHERE userfp.name='" + username + "';");
            while (rs.next()) {
                if(passwordInputValue.equals(rs.getString(1))) {
                    logger.info("CORRECT PASSWORD");
                } else {
                    logger.info("WRONG PASSWORD");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Scene getScene() { return this.scene; }
    public Stage getStage() { return this.childStage; }
}