package com.game.ap_project;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.URISyntaxException;

public class CheckCollisionTower extends Task {

    GameController controller;

    public CheckCollisionTower(GameController controller) {
        this.controller = controller;
    }

    @Override
    public Object call() throws Exception {
        boolean flag = true;
        while (flag) {
            if (controller.getPillar(0).getX() <= controller.getHero().getX()) {
                Game.removeFlipHandler();
            }
            if (Towers.isCollision(controller)) {
                System.out.println("Tower Collision");
                flag = false; // Exit the loop after detecting collision
            }
        }
        return null;
    }
}