package com.game.ap_project;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
//import javafx.scene.media.MediaView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class Game extends Application {
    Stick myStick = new Stick();
    StickFigure myHero = new StickFigure();
    private EventHandler<KeyEvent> keyHandler;
    private EventHandler<KeyEvent> keyReleased;

    Thread t1;

    private Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("start.fxml")));
        Parent root = loader.load();
        HomeController controller = loader.getController();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("CSS/home.css").toExternalForm());

        controller.getPlayButton().setOnMouseClicked(e -> {
            try {
                FXMLLoader scene2Loader = new FXMLLoader((getClass().getResource("scene.fxml")));
                Parent scene2 = scene2Loader.load();
                GameController controller2 = scene2Loader.getController();
                gameScene(controller2);
                controller.switchScene(scene2);
                myStick.setDifficulty(controller.getDifficulty());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        stage.setScene(scene);
        stage.setTitle("Stick Hero!");
        stage.show();
    }

    public void gameScene(GameController controller) throws IOException {
        controller.init();

        t1 = new Thread(new CheckCollision(controller));
        t1.setDaemon(true);
        t1.start();

        keyHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(Objects.requireNonNull(keyEvent.getCode()) == KeyCode.SPACE){
                    try {
                        myStick.increaseHeight(controller);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        keyReleased = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.SPACE) {
                    try {
                        game(controller);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }
//                if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.R){
//                    Towers.setTower(controller, 0);
//                    controller.moveNext();
//                }
            }
        };

        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyHandler);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, keyReleased);
    }

    public void game(GameController controller) throws InterruptedException, URISyntaxException {
        int points = myStick.getScore(controller);
        myStick.fall(controller);
        myHero.move(controller, points > 0);
        System.out.println(points);
        if(points > 0){
            if(points == 2){
                Points.addGamePoints(controller, 1);
            }
            Towers.setTower(controller, 0);
        } else{
            scene.removeEventHandler(KeyEvent.KEY_PRESSED, keyHandler);
            scene.removeEventHandler(KeyEvent.KEY_RELEASED, keyReleased);
            System.out.println("Game Ends!");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
