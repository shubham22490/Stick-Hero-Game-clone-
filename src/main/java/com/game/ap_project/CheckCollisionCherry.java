package com.game.ap_project;

public class CheckCollisionCherry implements Runnable{
    GameController controller;
    public CheckCollisionCherry(GameController controller){
        this.controller = controller;
    }
    @Override
    public void run() {
        while(true){
            if(Cherry.isCollision(controller)){

                Cherry.addCount(controller);

                controller.getCherry(0).setVisible(false);

                System.out.println("Collision");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
