package com.game.ap_project;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Observer Design Pattern: Used to distribute the Functionalities across classes.
 */

public interface Controller extends Initializable {
    void switchScene(Parent pane) throws IOException;
}
