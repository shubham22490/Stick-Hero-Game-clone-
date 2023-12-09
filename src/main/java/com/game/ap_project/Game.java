package com.game.ap_project;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class Game extends Application {
    private static final Stick myStick = new Stick();
    private final static StickFigure myHero = new StickFigure();
    private static EventHandler<KeyEvent> keyHandler;
    private static EventHandler<KeyEvent> keyReleased;
    private static EventHandler<KeyEvent> keyFlip;

    private static Scene scene;

    private static Thread t1;
    private static Thread t2;

    private static StackPane mainRoot;
    private static AnchorPane home;

    public static void addFlipHandler(){
        scene.addEventHandler(KeyEvent.KEY_RELEASED, keyFlip);
    }

    public static StackPane getMainRoot() {
        return mainRoot;
    }

    public static AnchorPane getHome() {
        return home;
    }

    public static StickFigure getMyHero() {
        return myHero;
    }

    public static void removeFlipHandler(){
        scene.removeEventHandler(KeyEvent.KEY_RELEASED, keyFlip);
    }

    public static void addStickHandler(){
        scene.addEventHandler(KeyEvent.KEY_RELEASED, keyReleased);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyHandler);
    }

    public static void removeStickHandler(){
        scene.removeEventHandler(KeyEvent.KEY_RELEASED, keyReleased);
        scene.removeEventHandler(KeyEvent.KEY_PRESSED, keyHandler);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("FXML/start.fxml")));
        Parent root = loader.load();
        HomeController controller = loader.getController();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("CSS/home.css").toExternalForm());
        mainRoot = controller.getRoot();
        home = controller.getHomePane();

        controller.getPlayButton().setOnMouseClicked(e -> {
            try {
                FXMLLoader scene2Loader = new FXMLLoader((getClass().getResource("FXML/scene.fxml")));
                Parent scene2 = scene2Loader.load();
                GameController controller2 = scene2Loader.getController();
                gameScene(controller2);
                t1 = new Thread(new CheckCollisionCherry(controller2));
                t2 = new Thread(new CheckCollisionTower(controller2));
                t1.setDaemon(true);
                t2.setDaemon(true);
                t1.start();
                t2.start();
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

    public static void gameScene(GameController controller) throws IOException {
        controller.init();

        keyHandler = keyEvent -> {
            if(Objects.requireNonNull(keyEvent.getCode()) == KeyCode.SPACE){
                try {
                    myStick.increaseHeight(controller);
                    controller.hideText();
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        keyReleased = keyEvent -> {
            if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.SPACE) {
                try {
                    game(controller);
                } catch (InterruptedException | URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        keyFlip = event -> {
            if(event.getCode() == KeyCode.UP){
                StickFigure.flip(controller);
            }
        };

        addStickHandler();
        addFlipHandler();
    }

    public static void game(GameController controller) throws InterruptedException, URISyntaxException {
        removeStickHandler();
        int points = myStick.getScore(controller);
        myStick.fall(controller);
        StickFigure.move(controller, points > 0);
        System.out.println(points);
        if(points > 0){
            addFlipHandler();
            if(points == 2){
                Points.addGamePoints(controller, 1);
            }
            Towers.setTower(controller, 0);
        } else{
            System.out.println("Game Ends!");
        }
    }

    public static void gameEnd(GameController controller) throws URISyntaxException, IOException {
        removeFlipHandler();
        removeStickHandler();
        controller.heroFall();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
