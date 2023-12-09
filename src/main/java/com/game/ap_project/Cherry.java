package com.game.ap_project;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.Random;

public class Cherry {

    private static int count = 0;
    private static int y = 0;
    private static boolean isBelow = false;

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

    public static void addCount(GameController controller){
        count++;
        controller.getCherryCounter().setText(String.valueOf(count));
    }

    public static void removeCount(GameController controller){
        count -= 3;
        controller.getCherryCounter().setText(String.valueOf(count));
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count, GameController controller) {
        Cherry.count = count;
        controller.getCherryCounter().setText(String.valueOf(count));
    }

    public static void placeCherry(GameController controller, int prev, int cherry){
        Rectangle pillar = controller.getPillar(prev);
        Rectangle nextPillar = controller.getPillar(prev + 1);
        int gap = (int) (nextPillar.getX() - pillar.getX() - pillar.getWidth()) - (int)controller.getCherry(cherry).getFitWidth();
        if(isCherry()) {
            Random random = new Random();
            Cherry.getY(controller, cherry);
            int x = random.nextInt(gap) + (int)pillar.getX() + (int)pillar.getWidth();
            controller.getCherry(cherry).setY(y);
            controller.getCherry(cherry).setX(x);
        }
    }

    public static boolean isCollision(GameController controller){
        ImageView cherry = controller.getCherry(0);
        if(cherry.isVisible()){
            ImageView hero = controller.getHero();
            if(hero.getX() <= cherry.getX() + cherry.getFitWidth() && hero.getX() + hero.getFitWidth() >= cherry.getX()){
                System.out.println(isBelow);
                return cherry.getY() > 490 == StickFigure.flipCheck();
            }
        }
        return false;
    }
}
