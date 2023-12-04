package com.game.ap_project;

public class CheckCollision implements Runnable{
    GameController controller;
    public CheckCollision(GameController controller){
        this.controller = controller;
    }
    @Override
    public void run() {
        while(true){
            if(Cherry.isCollision(controller)){
                Cherry.addCount();
                controller.getCherry(0).setVisible(false);
                System.out.println("Collision");
            }
        }
    }
}
