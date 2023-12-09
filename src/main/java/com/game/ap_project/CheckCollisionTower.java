package com.game.ap_project;

import java.io.IOException;
import java.net.URISyntaxException;

public class CheckCollisionTower implements Runnable {

    GameController controller;
    public CheckCollisionTower(GameController controller){
        this.controller = controller;
    }
    @Override
    public void run() {
        boolean flag = true;
        while(flag){
            if(controller.getPillar(0).getX() <= controller.getHero().getX()){
                Game.removeFlipHandler();
            }
            if(Towers.isCollision(controller)){
                System.out.println("Tower Collision");
                try {
                    Game.gameEnd(controller);
                    System.out.printf("GAME ENDS HERE!");
                    Thread.sleep(1000);
                } catch (URISyntaxException | IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }
}
