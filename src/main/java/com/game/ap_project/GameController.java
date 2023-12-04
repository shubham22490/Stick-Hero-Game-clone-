package com.game.ap_project;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;


public class GameController implements Controller {

    @FXML
    private Rectangle currentPillar;

    @FXML
    private Rectangle currentMid;

    @FXML
    private ImageView hero;

    @FXML
    private Rectangle nextPillar;

    @FXML
    private Rectangle nextMid;

    @FXML
    private AnchorPane scene;

    @FXML
    private Rectangle stick;
    @FXML
    private ImageView currentCherry;
    @FXML
    private ImageView nextCherry;

    @FXML
    private Text score;
    @FXML
    private Rectangle upcomingPillar;

    @FXML
    private Rectangle upcomingMid;

    private MediaPlayer player;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private final String hittingGround = getClass().getResource("fallSound2.mp3").toString();
    private final String stickGrow = getClass().getResource("stop.mp3").toString();
    private final String swap = getClass().getResource("swa.mp3").toString();

    private final String heroFall = getClass().getResource("heroFall.mp3").toString();

    public void init(){
        getCherry(0).setVisible(false);
        getCherry(1).setVisible(false);
        Towers.setSceneHeight((int)scene.getPrefHeight());
        Towers.setSceneWidth((int)scene.getPrefWidth());
        Towers.setBasePillar(currentPillar, currentMid);
        Towers.setTower(this, -1);
        getCherry(0).setVisible(true);
//        stickInit();
        stick.setY(490);
        stick.setX(95);
        stick.setWidth(7);
        stick.setHeight(0);
        hero.setX(50);
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

    public void stickInit(){
        Rotate rotate = new Rotate();
        rotate.setPivotY(490);
        rotate.setPivotX(95);
        rotate.setAngle(270);
        stick.getTransforms().add(rotate);
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
        return hero;
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
        stick.getTransforms().add(rotate);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(rotate.angleProperty(), 90)));
        setSound(hittingGround);
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                player.play();
            }
        });
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
        keyFrame = new KeyFrame(Duration.seconds(0.5), new KeyValue(hero.xProperty(), hero.getX()-delx));
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

        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
                Points.addGamePoints(controller, 1);
            }
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
        int dely = Towers.getHeight();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(hero.yProperty(), hero.getY())),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(hero.yProperty(), hero.getY()-40)),
                new KeyFrame(Duration.seconds(0.8), new KeyValue(hero.yProperty(), hero.getY() + dely + hero.getFitHeight())));
        this.setSound(heroFall);
        player.play();
        timeline.setOnFinished(e -> {
            hero.setVisible(false);
            stickInit();
        });
        timeline.play();

    }

    public void moveHero(int x, boolean val){
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), new KeyValue(hero.xProperty(), hero.getX())),
                new KeyFrame(Duration.seconds(1.5), new KeyValue(hero.xProperty(), x))
        );

        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(val) {
                    try {
                        setSound(swap);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                    moveNext();
                } else {
                    try {
                        heroFall();
                        Rotate rotate = new Rotate();
                        rotate.setPivotX(stick.getX());
                        rotate.setPivotY(stick.getY() + stick.getHeight());
                        rotate.setAngle(90);
                        stickFall(rotate, false);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
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

//    public void playPerfect(){
//        setSound();
//    }



    public void setSound(String path) throws URISyntaxException {
        player = new MediaPlayer(new Media(path));
        player.seek(Duration.seconds(0));
    }


}
