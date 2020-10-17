package com.mafarawa;

import com.mafarawa.controller.authreg.RegistrationController;
import com.mafarawa.controller.authreg.SelectUserController;
import com.mafarawa.model.SelectScene;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class App extends Application {
    private static Map<SelectScene, Scene> scenes;
    private static Logger logger;
    static { logger = Logger.getLogger(App.class.getName()); }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        logger.info("===========================|STARTING PROGRAMM...|===========================");
        SelectUserController suc = new SelectUserController(stage);
        RegistrationController ruc = new RegistrationController(stage);

        scenes = new HashMap<>();
        scenes.put(SelectScene.SELECT_USER_SCENE, suc.getScene());
        scenes.put(SelectScene.REGISTRATION_SCENE, ruc.getScene());

        stage.setScene(scenes.get(SelectScene.SELECT_USER_SCENE));
        stage.setTitle("FinancePRJ");
        stage.show();
    }

    public static Scene selectScene(SelectScene s) {
        return scenes.get(s);
    }
}
