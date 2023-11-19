package com.game.ap_project;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Game extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        Scene mainScene = new Scene(root);

        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case SPACE:
                        controller.increaseHeight();
                }
            }
        });

        stage.setScene(mainScene);
//        stage.setTitle("Stick Hero!");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}