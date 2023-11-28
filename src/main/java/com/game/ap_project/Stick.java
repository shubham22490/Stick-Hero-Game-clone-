package com.game.ap_project;

//import java.awt.*;

import javafx.animation.RotateTransition;
import javafx.scene.transform.Rotate;

public class Stick {
    private static int length = 0;    // Length of the stick
    private int angle;     // Angle of rotation
    private int xEnd;      // X-coordinate of the stick's end
    private int yEnd;      // Y-coordinate of the stick's end
    private boolean falling;  // Flag to indicate if the stick is falling

    private final int MULTIPLIER = 7;

    // Constructor to initialize stick properties
    public Stick() {
        this.length = 0;
        this.angle = 0;
        this.xEnd = 0;
        this.yEnd = 0;
        this.falling = false;
    }

    // Method to increase the height of the stick
    public void increaseHeight(Controller controller) {
        if(length < 400 - MULTIPLIER) length += MULTIPLIER;
        controller.setStickHeight(length);
    }

    public void fall(Controller controller){
        Rotate rotate = new Rotate();
        rotate.setPivotX(95);
        rotate.setPivotY(this.length);
        rotate.setAngle(90);

        controller.stickFall(rotate);
    }



    // Method to rotate and fall the stick on the pillar (animation)
    public void rotateAndFall() {
        if (!falling) {
            falling = true;
            for (int i = 0; i <= 90; i += 5) {
                angle = i;
                try {
                    Thread.sleep(20);  // Add a slight delay for animation effect
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Method to create the stick
    public void createStick(int xStart, int yStart) {
        xEnd = xStart + length * (int) Math.cos(Math.toRadians(angle));
        yEnd = yStart - length * (int) Math.sin(Math.toRadians(angle));
    }

    // Method to check if the length is correct for landing on the tower
    public boolean isCorrect(int towerWidth, int gap) {
        return length <= towerWidth + gap && length >= gap;
    }

    // Method to check if the length is perfect for an extra score
    public boolean isPerfect(int perfectLength) {
        return length == perfectLength;
    }

    // Getter method for the x-coordinate of the stick's end
    public int getXEnd() {
        return xEnd;
    }

    // Getter method for the y-coordinate of the stick's end
    public int getYEnd() {
        return yEnd;
    }

    // Getter method for the falling flag
    public boolean isFalling() {
        return falling;
    }
}
