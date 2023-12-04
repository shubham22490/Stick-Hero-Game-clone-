package com.game.ap_project;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.Random;

public class Cherry {

    private static int count = 0;
    private static int y = 0;

    // Method to check if a cherry is possible in the width
    private static boolean isCherry() {
        double probability = 0.2;
        return Math.random() < probability;
    }

    private static int getY(GameController controller, int cherry){
        Random rand = new Random();
        //True means above the platform and False means below the platform
        boolean state = rand.nextInt(2) == 1;
        if(state){
            y = 490 - (int)controller.getCherry(cherry).getFitHeight();
        } else{
            y = 490 + 20;
        }
        return y;
    }

    public static void addCount(){
        count++;
    }


    public static void placeCherry(GameController controller, int prev, int cherry){
        Rectangle pillar = controller.getPillar(prev);
        Rectangle nextPillar = controller.getPillar(prev + 1);
        int gap = (int) (nextPillar.getX() - pillar.getX() - pillar.getWidth()) - (int)controller.getCherry(cherry).getFitWidth();
//        if(isCherry(){
            Random random = new Random();
            Cherry.getY(controller, cherry);
            int x = random.nextInt(gap) + (int)pillar.getX() + (int)pillar.getWidth();
            controller.getCherry(cherry).setY(y);
            controller.getCherry(cherry).setX(x);
//            controller.getCherry(cherry).setVisible(true);
//        }
    }

    public static boolean isCollision(GameController controller){
        ImageView cherry = controller.getCherry(0);
        if(cherry.isVisible()){
            ImageView hero = controller.getHero();
            return hero.getX() <= cherry.getX() + cherry.getFitWidth() && hero.getX() + hero.getFitWidth() >= cherry.getX();
        }
        return false;
    }
}
