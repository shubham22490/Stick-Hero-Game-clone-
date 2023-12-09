package com.game.ap_project;


import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class GameController implements Controller {

    @FXML
    private Button restart;

    @FXML
    private Text cherryCounter;

    @FXML
    private ImageView currentCherry;

    @FXML
    private Rectangle currentMid;

    @FXML
    private Rectangle currentPillar;

    @FXML
    private Text finalBest;

    @FXML
    private Text finalScore;

    @FXML
    private AnchorPane gameEnd;

    @FXML
    private Text helpText;

    @FXML
    private ImageView stickHero;

    @FXML
    private Button homeButton;

    @FXML
    private ImageView nextCherry;

    @FXML
    private Rectangle nextMid;

    @FXML
    private Text perfect;

    @FXML
    private Rectangle nextPillar;

    @FXML
    private AnchorPane scene;

    @FXML
    private Text score;

    @FXML
    private Rectangle stick;

    @FXML
    private Button revive;

    @FXML
    private Rectangle upcomingMid;

    @FXML
    private Rectangle upcomingPillar;

    private MediaPlayer player;

    private StackPane root;

    private AnchorPane home;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String path = Objects.requireNonNull(getClass().getResource("CSS/pillar2.png")).toString();
        currentPillar.setFill(new ImagePattern(new Image(path)));
        nextPillar.setFill(new ImagePattern(new Image(path)));
        upcomingPillar.setFill(new ImagePattern(new Image(path)));
        stick.setFill(Color.web("#3b5367"));

        homeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            try {
                switchScene(home);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        revive.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            Cherry.removeCount(this);
            try {
                Game.gameScene(this);
            } catch (IOException | InterruptedException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        restart.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            try {
                Points.setGamePoints(0);
                Game.gameScene(this);
            } catch (IOException | InterruptedException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void setPerfect(){
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(perfect.visibleProperty(), false, Interpolator.EASE_IN)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(perfect.yProperty(), perfect.getY()+50, Interpolator.EASE_IN)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(perfect.visibleProperty(), true, Interpolator.EASE_IN)),
                new KeyFrame(Duration.seconds(0.8), new KeyValue(perfect.yProperty(), perfect.getY(), Interpolator.EASE_IN)),
                new KeyFrame(Duration.seconds(0.8), new KeyValue(perfect.visibleProperty(), true, Interpolator.EASE_IN)),
                new KeyFrame(Duration.seconds(1), new KeyValue(perfect.yProperty(), perfect.getY()-50, Interpolator.EASE_IN)),
                new KeyFrame(Duration.seconds(1), new KeyValue(perfect.visibleProperty(), false, Interpolator.EASE_OUT)));

        timeline.play();
    }

    private final String hittingGround = getClass().getResource("fallSound2.mp3").toString();
    private final String stickGrow = getClass().getResource("stop.mp3").toString();
    private final String swap = getClass().getResource("swa.mp3").toString();

    private final String heroFall = getClass().getResource("heroFall.mp3").toString();

    public void init(){
        gameEnd.setVisible(false);
        stickHero.setVisible(true);
        stickHero.setX(50);
        stickHero.setY(490 - stickHero.getFitHeight());
        score.setText(String.valueOf(Points.getGamePoints()));
        perfect.setVisible(false);
        perfect.setY(250);

        getCherry(0).setVisible(false);
        getCherry(1).setVisible(false);
        Towers.setSceneHeight((int)scene.getPrefHeight());
        Towers.setSceneWidth((int)scene.getPrefWidth());
        Towers.setBasePillar(currentPillar, currentMid);
        Towers.setTower(this, -1);
//        getPillar(-1).setX(Math.max(0, currentPillar.getWidth() - 100));
        getCherry(1).setVisible(true);
        stickInit();
        upcomingMid.setVisible(false);
        currentMid.setVisible(false);
    }



    public Text getScore(){
        return score;
    }

    public ImageView getCherry(int value) {
        if(value == 0) return currentCherry;
        return nextCherry;
    }

    public Text getCherryCounter(){
        return cherryCounter;
    }

    public void hideText(){
        helpText.setVisible(false);
    }

    public void stickInit(){
        stick.getTransforms().clear();
        Stick.resetLength();
        stick.setWidth(7);
        stick.setHeight(0);
        stick.setY(490);
        stick.setX(95);
        stick.setVisible(true);
    }

    public Rectangle getStick(){
        return stick;
    }

    public ImageView getHero(){
        return stickHero;
    }

    public void settingGameEnd() throws IOException {
        score.setVisible(true);
        finalScore.setText(String.valueOf(Points.getGamePoints()));
        Points.setHighScore(Points.getGamePoints());
        finalBest.setText(String.valueOf(Points.getHighScore()));
        gameEnd.setVisible(true);
        revive.setDisable(false);

        if(Cherry.getCount() < 3) {
            revive.setDisable(true);
        }

        root = Game.getMainRoot();
        home = Game.getHome();


    }

    public Rectangle getPillar(int value){
        if(value == -1) return currentPillar;
        if(value == 0) return nextPillar;
        if(value == 1) return upcomingPillar;
        return nextPillar;
    }

    public Rectangle getMid(int value){
        if(value == -1) return currentMid;
        if(value == 0) return nextMid;
        if(value == 1) return upcomingMid;
        return nextMid;
    }


    public void stickFall(Rotate rotate, boolean isPerfect) throws InterruptedException, URISyntaxException {
        stick.getTransforms().clear();
        stick.getTransforms().add(rotate);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(rotate.angleProperty(), 90)));
        setSound(hittingGround);
        timeline.setOnFinished(event -> {
            Game.addFlipHandler();
            if(isPerfect){
                setPerfect();
            }
            player.play();
        });
        System.out.println(stick.getTransforms());
        timeline.play();
    }

    public void moveNext(){
        int delx = (int)nextPillar.getX() + (int)nextPillar.getWidth() - 100;

        Timeline timeline = new Timeline();
        keyFrameX(currentPillar, delx, timeline);
        keyFrameX(nextPillar, delx, timeline);
        keyFrameX(upcomingPillar, delx, timeline);
        keyFrameX(currentMid, delx, timeline);
        keyFrameX(nextMid, delx, timeline);
        keyFrameX(upcomingMid, delx, timeline);
        KeyFrame keyFrame;
        keyFrame = new KeyFrame(Duration.seconds(0.5), new KeyValue(stickHero.xProperty(), stickHero.getX()-delx));
        timeline.getKeyFrames().add(keyFrame);
        keyFrame = new KeyFrame(Duration.seconds(0.5), new KeyValue(stick.yProperty(), stick.getY()+delx));
        timeline.getKeyFrames().add(keyFrame);
        keyFrame = new KeyFrame(Duration.seconds(0.5), new KeyValue(getCherry(1).xProperty(), getCherry(1).getX()-delx));
        timeline.getKeyFrames().add(keyFrame);
        keyFrame = new KeyFrame(Duration.seconds(0.1), new KeyValue(getCherry(1).visibleProperty(), true));
        timeline.getKeyFrames().add(keyFrame);
        keyFrame = new KeyFrame(Duration.seconds(0.5), new KeyValue(getCherry(0).xProperty(), getCherry(0).getX()-delx));
        timeline.getKeyFrames().add(keyFrame);
        GameController controller = this;

        timeline.setOnFinished(event -> {
            Rectangle temp = currentPillar;
            currentPillar = nextPillar;
            nextPillar = upcomingPillar;
            upcomingPillar = temp;
            upcomingPillar.setVisible(false);
            temp = currentMid;
            currentMid = nextMid;
            nextMid = upcomingMid;
            upcomingMid = temp;
            ImageView temp2 = getCherry(0);
            currentCherry = nextCherry;
            nextCherry = temp2;
            nextCherry.setVisible(false);
            currentCherry.setVisible(true);
            upcomingMid.setVisible(false);
            currentMid.setVisible(false);
            nextMid.setVisible(true);
            stickInit();
            Game.getLock().lock();
            if(Game.isGameStatus()){
                Points.addGamePoints(controller, 1);
                Game.addStickHandler();
            }
            Game.getLock().unlock();
            Game.removeFlipHandler();
        });

        timeline.play();
        player.play();
    }

    private  void  keyFrameX(Rectangle r, double x, Timeline timeline){
        KeyValue keyValue = new KeyValue(r.xProperty(), r.getX() - x);
        KeyFrame keyFrame;
        keyFrame = new KeyFrame(Duration.seconds(0.5), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        keyValue = new KeyValue(r.visibleProperty(), true);
        keyFrame = new KeyFrame(Duration.seconds(0.1), keyValue);
        timeline.getKeyFrames().add(keyFrame);
    }

    public void heroFall() throws URISyntaxException {
        Game.removeFlipHandler();
        int dely = Towers.getHeight();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(stickHero.yProperty(), stickHero.getY())),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(stickHero.yProperty(), stickHero.getY()-40)),
                new KeyFrame(Duration.seconds(0.8), new KeyValue(stickHero.yProperty(), stickHero.getY() + dely + stickHero.getFitHeight())));
        this.setSound(heroFall);
        player.play();
        timeline.setOnFinished(e -> {
            stickHero.setVisible(false);
            try {
                settingGameEnd();
            } catch (IOException ex) {
                System.out.println("Error");
                throw new RuntimeException(ex);
            }
        });
        StickFigure.setStraigth(this);
        timeline.play();
    }

    public void moveHero(int x, boolean val){
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), new KeyValue(stickHero.xProperty(), stickHero.getX())),
                new KeyFrame(Duration.seconds(1.5), new KeyValue(stickHero.xProperty(), x))
        );

        timeline.setOnFinished(event -> {
            if(val) {
                try {
                    setSound(swap);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
                moveNext();
            } else {
                try {
                    Game.gameEnd(this);
                } catch (URISyntaxException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        timeline.play();
    }


    public void setStickHeight(int length) throws URISyntaxException {
        setSound(stickGrow);
        stick.setY(490 - length);
        stick.setHeight(length);
        player.play();
    }


    public void setSound(String path) throws URISyntaxException {
        player = new MediaPlayer(new Media(path));
        player.seek(Duration.seconds(0));
    }


    @Override
    public void switchScene(Parent pane) throws IOException {
        root.getChildren().add(pane);
        pane.translateXProperty().set(scene.getWidth());

        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), new KeyValue(pane.translateXProperty(), 0, Interpolator.EASE_OUT));
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(e -> {
            root.getChildren().remove(scene);
        });
        timeline.play();
    }

    public AnchorPane getScene() {
        return scene;
    }
}
