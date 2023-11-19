package com.game.ap_project;

import java.awt.*;
import java.util.Random;

public class Cherry {
    private boolean isVisible;  // Flag to indicate if the cherry is visible
    private int x;              // X-coordinate of the cherry
    private int y;              // Y-coordinate of the cherry

    // Constructor to initialize cherry properties
    public Cherry() {
        this.isVisible = true;  // Cherry is initially visible
        this.x = 0;
        this.y = 0;
    }

    // Method to check if a cherry is possible in the width
    public boolean isCherry(int towerWidth, double probability) {
        return Math.random() < probability && towerWidth > 0;
    }

    // Method to randomize the cherry if it's possible
    public void randomizeCherry(int towerX, int towerWidth) {
        if (isCherry(towerWidth, 0.2)) { // 0.2 is the probability of cherry appearance
            x = getCord(towerX, towerWidth);
            y = 400;  // Assuming a fixed height for the towers
        } else {
            isVisible = false; // Cherry is not visible if it's not present
        }
    }

    // Method to set the cherry as hidden
    public void setHidden() {
        isVisible = false;
    }

    // Method to hide the cherry if the hero touches it
    public void hideIfTouched(int heroX, int heroY, int heroHeight) {
        if (isVisible && heroX >= x - 10 && heroX <= x + 10 && heroY - heroHeight <= y && heroY >= y - 20) {
            isVisible = false;
        }
    }

    // Method to get random coordinates to place the cherry
    private int getCord(int towerX, int towerWidth) {
        Random random = new Random();
        return towerX + random.nextInt(towerWidth);
    }

    // Main user function to check if the cherry is placed
    public boolean getPlaced() {
        return isVisible;
    }

    // Getter method for the x-coordinate of the cherry
    public int getX() {
        return x;
    }

    // Getter method for the y-coordinate of the cherry
    public int getY() {
        return y;
    }

    // Method to draw the cherry on the screen
    public void draw(Graphics g) {
        if (isVisible) {
            g.setColor(Color.RED);
            g.fillOval(x - 10, y - 20, 20, 20);
        }
    }
}
