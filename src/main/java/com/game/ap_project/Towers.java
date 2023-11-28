package com.game.ap_project;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Towers {
//    private int x;       // X-coordinate of the left side of the tower
    private int width;   // Width of the tower
    private int perfectPoint;  // Perfect point for scoring

    private static final int HEIGHT = 470;
    private static final int MAXWIDTH = 200;
    private static final int MINWIDTH = 20;
    private static final int MINGAP = 50;

    private static int screenHeight;
    private static int screenWidth;


    // Constructor to initialize tower properties
//    public Towers(int x, int width) {
//        this.x = x;
//        this.width = width;
//        this.perfectPoint = x + width / 2;
//    }

    public static void setSceneHeight(int h){
        screenHeight = 960;
    }

    public static void setSceneWidth(int w){
        screenWidth = 540;
    }

    // Method to draw the tower on the screen
//    public void draw(Graphics g) {
//        g.setColor(Color.BLUE);
//        g.fillRect(x, 0, width, 400); // Assuming a fixed height for the towers
//    }

    // Method to get the perfect point in the middle of the tower's width
//    public int getPerfectPoint() {
//        return perfectPoint;
//    }
//
//    // Method to get the gap between two towers
//    public int getGap(Towers nextTower) {
//        return nextTower.getX() - (x + width);
//    }
//
//    // Getter method for the width of the tower
//    public int getWidth() {
//        return width;
//    }
//
//    // Getter method for the x-coordinate of the tower
//    public int getX() {
//        return x;
//    }

    public static void setTower(Rectangle tower, int X, int prevWidth){
        Random random = new Random();
        int width = random.nextInt(MAXWIDTH - MINWIDTH) + MINWIDTH;
        int x = random.nextInt(screenWidth - width - MINGAP - prevWidth) + prevWidth + X + MINGAP;
//        Rectangle random = new Rectangle(x, screenHeight - HEIGHT, width, HEIGHT);
        tower.setX(x);
        tower.setY(screenHeight - HEIGHT);
        tower.setWidth(width);
        tower.setHeight(HEIGHT);
    }

    public static void setBasePillar(Rectangle tower){
        Towers.setTower(tower, 0, 100);
        tower.setX(0);
        tower.setWidth(100);
    }

}
