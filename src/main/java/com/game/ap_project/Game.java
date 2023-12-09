package com.game.ap_project;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Game extends Application {
    private static final Stick myStick = new Stick();
    private final static StickFigure myHero = StickFigure.getInstance();
    private static EventHandler<KeyEvent> keyHandler;
    private static EventHandler<KeyEvent> keyReleased;
    private static EventHandler<KeyEvent> keyFlip;

    private static final Lock lock = new ReentrantLock();

    private static boolean gameStatus = true;

    private static Scene scene;

    private static Thread t1;
    private static Thread t2;

    private static final String filePath = "data.json";


    private static StackPane mainRoot;
    private static AnchorPane home;

    private static final Gson gson = new Gson();
    private static Data data = new Data();

    public static void addFlipHandler(){
        removeFlipHandler();
        scene.addEventHandler(KeyEvent.KEY_RELEASED, keyFlip);
    }

    public static boolean isGameStatus() {
        return gameStatus;
    }

    public static Lock getLock() {
        return lock;
    }

    public static void setGameStatus(boolean gameStatus) {
        Game.gameStatus = gameStatus;
    }

    public static StackPane getMainRoot() {
        return mainRoot;
    }

    public static AnchorPane getHome() {
        return home;
    }

    public static void removeFlipHandler() {
            scene.removeEventHandler(KeyEvent.KEY_RELEASED, keyFlip);
    }

    public static void addStickHandler() {
        removeStickHandler();
        if (scene != null && keyReleased != null && keyHandler != null) {
            scene.addEventHandler(KeyEvent.KEY_RELEASED, keyReleased);
            scene.addEventHandler(KeyEvent.KEY_PRESSED, keyHandler);
        }
    }

    public static void removeStickHandler() {
        scene.removeEventHandler(KeyEvent.KEY_RELEASED, keyReleased);
        scene.removeEventHandler(KeyEvent.KEY_PRESSED, keyHandler);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            String jsonString = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));

            // Convert the JSON to a Person object
            data = gson.fromJson(jsonString, Data.class);

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
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
                Cherry.setCount(data.getCherries(), controller2);
                Points.setHighScore(data.getHighScore());
                t1 = new Thread(new CheckCollisionCherry(controller2));
                t1.setDaemon(true);
                t1.start();
                controller.switchScene(scene2);
                myStick.setDifficulty(controller.getDifficulty());
            } catch (IOException | InterruptedException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        stage.setScene(scene);
        stage.setTitle("Stick Hero!");
        stage.show();
    }

    public static void gameScene(GameController controller) throws IOException, InterruptedException, URISyntaxException {
        controller.init();
        setGameStatus(true);

        Task<Void> collisionTower = new CheckCollisionTower(controller);

        collisionTower.setOnSucceeded(e -> {
            System.out.println("Collision of tower and figure.");
            lock.lock();
            setGameStatus(false);
            lock.unlock();
            Platform.runLater(() -> {
                try {
                    removeStickHandler();
                    removeFlipHandler();
                    gameEnd(controller);
                } catch (URISyntaxException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        });

        t2 = new Thread(collisionTower);
        t2.setDaemon(true);
        t2.start();

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
            if(event.getCode() == KeyCode.SPACE){
                StickFigure.flip(controller);
            }
        };

        addStickHandler();
    }

    public static void game(GameController controller) throws InterruptedException, URISyntaxException {
        removeStickHandler();

        int points = myStick.getScore(controller);
        myStick.fall(controller);
        StickFigure.move(controller, points > 0);
        System.out.println(points);
        if(points > 0){
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
        Points.setHighScore(Points.getGamePoints());
        data.setHighScore(Points.getHighScore());
        data.setCherries(Cherry.getCount());
        String json = gson.toJson(data);
        try (java.io.FileWriter writer = new java.io.FileWriter(filePath)) {
            writer.write(json);
            System.out.println("JSON data has been saved to " + filePath);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
