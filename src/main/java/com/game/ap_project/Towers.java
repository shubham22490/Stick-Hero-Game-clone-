package com.game.ap_project;

import java.awt.*;
public class Towers {
    private int x;       // X-coordinate of the left side of the tower
    private int width;   // Width of the tower
    private int perfectPoint;  // Perfect point for scoring

    // Constructor to initialize tower properties
    public Towers(int x, int width) {
        this.x = x;
        this.width = width;
        this.perfectPoint = x + width / 2;
    }

    // Method to draw the tower on the screen
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, 0, width, 400); // Assuming a fixed height for the towers
    }

    // Method to get the perfect point in the middle of the tower's width
    public int getPerfectPoint() {
        return perfectPoint;
    }

    // Method to get the gap between two towers
    public int getGap(Towers nextTower) {
        return nextTower.getX() - (x + width);
    }

    // Getter method for the width of the tower
    public int getWidth() {
        return width;
    }

    // Getter method for the x-coordinate of the tower
    public int getX() {
        return x;
    }
}
