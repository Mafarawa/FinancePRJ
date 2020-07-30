package com.mafarawa.dialog;

import com.mafarawa.connect.DBGate;
import com.mafarawa.model.UserModel;
import com.mafarawa.model.SelectScene;
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

public class AuthorizationDialog {
    private Label errorLabel;
    private Button userAvatar;
    private Button doneButton;
    private Button dropPasswordButton;
    private PasswordField passwordInput;
    private VBox inputLayout;
    private Stage childStage;
    private Scene scene;
    
    private static Logger logger;
    static { logger = Logger.getLogger(AuthorizationDialog.class.getName()); }

    public AuthorizationDialog(Stage stage, UserModel user) {
        errorLabel = new Label();

        doneButton = new Button("Войти");
        dropPasswordButton = new Button("Забыл пароль");

        passwordInput = new PasswordField();
        passwordInput.setPromptText("Пароль");

        HBox buttonLayout = new HBox(20);
        buttonLayout.getChildren().addAll(doneButton, dropPasswordButton);

        inputLayout = new VBox(20);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.getChildren().addAll(passwordInput, errorLabel, buttonLayout);

        FlowPane rootLayout = new FlowPane(Orientation.VERTICAL, 20, 10);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.getChildren().addAll(inputLayout);

        scene = new Scene(rootLayout, 300, 300);

        childStage = new Stage();
        childStage.initOwner(stage);
        childStage.initModality(Modality.WINDOW_MODAL);
        childStage.setTitle("Авторизация");
        childStage.setResizable(false);
        childStage.setScene(scene);

        setUserAvatar(user.cloneUserAvatar());
        doneButton.setOnAction(e -> authorizeUser(user.getName(), stage));
        dropPasswordButton.setOnAction(e -> new DropPasswordDialog(childStage, user));
    }

    private void setUserAvatar(Button userAvatar) {
        userAvatar = userAvatar;
        userAvatar.setDisable(true);
        inputLayout.getChildren().add(0, userAvatar);
    }

    private void authorizeUser(String username, Stage stage) {
        DBGate dbGate = DBGate.getInstance();
        String passwordInputValue = Integer.toHexString(passwordInput.getText().hashCode());

        logger.debug("User: " + username + " try to authorize with password: " + passwordInputValue);

        try {
            ResultSet rs = dbGate.executeData("SELECT userfp.password FROM userfp WHERE userfp.name='" + username + "';");
            while (rs.next()) {
                if(passwordInputValue.equals(rs.getString(1))) {
                    stage.setScene(App.selectScene(SelectScene.MAIN_WINDOW));
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

    public Scene getScene() { return this.scene; }
    public Stage getStage() { return this.childStage; }
}