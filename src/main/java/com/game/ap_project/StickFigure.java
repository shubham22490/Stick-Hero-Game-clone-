package com.game.ap_project;

import java.awt.*;

public class StickFigure {
    private int x;          // X-coordinate of the stick figure
    private int y;          // Y-coordinate of the stick figure
    private int height;     // Height of the stick figure
    private boolean flipped; // Flag to indicate if the stick figure is flipped

    // Constructor to initialize stick figure properties
    public StickFigure(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.flipped = false;
    }

    // Method to move the stick figure horizontally
    public void move(int deltaX) {
        x += deltaX;
    }

    // Method to make the stick figure fall (move vertically down)
    public void fall(int deltaY) {
        y += deltaY;
    }

    // Method to flip the stick figure
    public void flip() {
        flipped = true;
    }

    // Method to check if the stick figure is flipped
    public boolean flipCheck() {
        return flipped;
    }

    // Method to draw the stick figure on the screen
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        if (!flipped) {
            g.drawLine(x, y, x, y - height); // Body
            g.drawLine(x, y - height, x - height, y - height * 2); // Left leg
            g.drawLine(x, y - height, x + height, y - height * 2); // Right leg
        } else {
            g.drawLine(x, y, x, y + height); // Flipped body
            g.drawLine(x, y + height, x - height, y + height * 2); // Flipped left leg
            g.drawLine(x, y + height, x + height, y + height * 2); // Flipped right leg
        }
    }

    // Getter method for the x-coordinate of the stick figure
    public int getX() {
        return x;
    }

    // Getter method for the y-coordinate of the stick figure
    public int getY() {
        return y;
    }
}
