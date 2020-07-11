package com.mafarawa;

import com.mafarawa.controller.SelectUserController;
import com.mafarawa.controller.RegistrationController;
import com.mafarawa.model.SelectScene;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.Map;
import java.util.HashMap;

public class App extends Application {
    private SelectUserController suc;
    private RegistrationController ruc;
    private static Map<SelectScene, Scene> scenes;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        suc = new SelectUserController(stage);
        ruc = new RegistrationController(stage);

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