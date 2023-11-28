package com.game.ap_project;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Game extends Application {
    Stick myStick = new Stick();
    int i = 1;
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        Scene mainScene = new Scene(root);
        controller.init();
        controller.setRectangle();



        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(Objects.requireNonNull(keyEvent.getCode()) == KeyCode.SPACE){
                    myStick.increaseHeight(controller);
                }
            }
        });
//
//
//
        mainScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.SPACE) {
                    myStick.fall(controller);
//                    myStick.isCorrect();
                }
                if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.R){
                    controller.setRectangle();
                    controller.pillarAnimation();
                }
            }
        });

        stage.setScene(mainScene);
        stage.setTitle("Stick Hero!");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
