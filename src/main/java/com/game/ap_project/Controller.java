package com.game.ap_project;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Controller {

    private static int stickHeight = 0;

    @FXML
    private Rectangle centerBox;

    @FXML
    private Rectangle currentBox;

    @FXML
    private ImageView hero;

    @FXML
    private Rectangle nextBox;

    @FXML
    private Rectangle stick;

    public void increaseHeight() {
        System.out.println("Function Called.");
        stickHeight += 7;
        stick.setHeight(stickHeight);
    }

}
