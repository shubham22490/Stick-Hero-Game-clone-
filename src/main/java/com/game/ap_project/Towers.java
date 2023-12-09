package com.game.ap_project;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.Random;

import static java.lang.Math.max;

public class Towers {
//    private int x;       // X-coordinate of the left side of the tower
    private int width;   // Width of the tower
    private int perfectPoint;  // Perfect point for scoring

    private static final int HEIGHT = 470;
    private static final int MAXWIDTH = 200;
    private static final int MINWIDTH = 30;
    private static final int MINGAP = 100;

    private static int screenHeight;
    private static int screenWidth;

    public static int getMinGap() {
        return MINGAP;
    }



    // Constructor to initialize tower properties
//    public Towers(int x, int width) {
//        this.x = x;
//        this.width = width;
//        this.perfectPoint = x + width / 2;
//    }

    public static void setSceneHeight(int h){
        screenHeight = h;
    }



    public static  int getHeight() { return HEIGHT; }

    public static void setSceneWidth(int w){
        screenWidth = w;
    }


    private static void setTower(Rectangle tower, double X, double prevWidth, Rectangle mid){
        Random random = new Random();
        int width = random.nextInt(MAXWIDTH - MINWIDTH) + MINWIDTH;
        int x = random.nextInt(screenWidth - width - MINGAP - max((int)prevWidth, 100)) + (int)prevWidth + (int)X + MINGAP;
        tower.setX(x);
        mid.setX(x + (double) width / 2 - 5);
        tower.setY(screenHeight - HEIGHT);
        mid.setY(screenHeight - HEIGHT);
        tower.setWidth(width);
        mid.setWidth(10);
        tower.setHeight(HEIGHT);
    }

    public static void setTower(GameController controller, int i){
        Rectangle prev = controller.getPillar(i);
        Rectangle next = controller.getPillar(i + 1);
        setTower(next, prev.getX(), prev.getWidth(), controller.getMid(i+1));
        System.out.println("Now Placing Cherries.");
        Cherry.placeCherry(controller, i, i+1);

    }

    public static void setBasePillar(Rectangle tower, Rectangle mid){
        Towers.setTower(tower, 0, 100, mid);
        tower.setX(0);
        mid.setVisible(false);
        tower.setWidth(100);
    }

    public static boolean isCollision(GameController controller){
        Rectangle pillar = controller.getPillar(0);
        ImageView hero = controller.getHero();
        if(hero.getX() <= pillar.getX() + pillar.getWidth() && hero.getX() + hero.getFitWidth() >= pillar.getX()){
            return StickFigure.flipCheck();
        }
        return false;
    }

}
