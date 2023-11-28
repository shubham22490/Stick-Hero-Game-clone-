package com.game.ap_project;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.security.Key;

public class Controller {

    @FXML
    private Rectangle currentPillar;

    @FXML
    private ImageView hero;

    @FXML
    private Rectangle nextPillar;

    @FXML
    private AnchorPane scene;

    @FXML
    private Rectangle stick;

    @FXML
    private Rectangle upcomingPillar;

    public void init(){

        Towers.setSceneHeight((int)scene.getPrefHeight());
        Towers.setSceneWidth((int)scene.getPrefWidth());
        Towers.setBasePillar(currentPillar);
        Towers.setTower(nextPillar, (int)currentPillar.getX(), (int)currentPillar.getWidth());
        stick.setX(95);
        stick.setY(5);
        hero.setX(50);
    }


    public void stickFall(Rotate rotate){
        stick.getTransforms().add(rotate);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(rotate.angleProperty(), 90)));
        timeline.play();
    }

    public void pillarAnimation(){
        int delx = (int)nextPillar.getX() + (int)nextPillar.getWidth() - 100;
        System.out.println(currentPillar.getX() + " " + nextPillar.getX() + " " + upcomingPillar.getX());
        Timeline timeline = new Timeline();
        pillarTransition(currentPillar, delx, timeline);
        pillarTransition(nextPillar, delx, timeline);
        pillarTransition(upcomingPillar, delx, timeline);
        upcomingPillar.setVisible(true);

        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Rectangle temp = currentPillar;
                currentPillar = nextPillar;
                nextPillar = upcomingPillar;
                upcomingPillar = temp;
                upcomingPillar.setVisible(false);
            }
        });

        timeline.play();

        System.out.println(currentPillar.getX() + " " + nextPillar.getX() + " " + upcomingPillar.getX());
    }

    private void pillarTransition(Rectangle r, double x, Timeline timeline){
        KeyValue keyValue = new KeyValue(r.xProperty(), r.getX() - x);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.3), keyValue);
        timeline.getKeyFrames().add(keyFrame);
    }
//
    public void setRectangle(){
        Towers.setTower(upcomingPillar, (int)nextPillar.getX(), (int)nextPillar.getWidth());
    }


    public void setStickHeight(int length) {
        stick.setHeight(length);
    }
}
