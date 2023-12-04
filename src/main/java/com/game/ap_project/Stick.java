package com.game.ap_project;

import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.net.URISyntaxException;

public class Stick {
    private static int length = 0;    // Length of the stick
    private static int MULTIPLIER = 10;
    private final int MAXLENGTH = 450;

    // Constructor to initialize stick properties
    public Stick() {
        length = 0;
    }

    // Method to increase the height of the stick
    public void increaseHeight(GameController controller) throws URISyntaxException {
        if(length < MAXLENGTH - MULTIPLIER) length += MULTIPLIER;
        controller.setStickHeight(length);
    }

    public static void resetLength(){
        length = 0;
    }

    public void fall(GameController controller) throws InterruptedException, URISyntaxException {
        Rotate rotate = new Rotate();
        rotate.setPivotX(controller.getStick().getX());
        rotate.setPivotY(controller.getStick().getY() + controller.getStick().getHeight());
        rotate.setAngle(90);

        controller.stickFall(rotate, isPerfect(controller));
    }


    public int getScore(GameController controller){
        int score = 0;
        if(isCorrect(controller)){
            score++;
            if(isPerfect(controller)){
                score++;
            }
        }
        return score;
    }

    // Method to check if the length is correct for landing on the tower
    public boolean isCorrect(GameController controller) {
        Rectangle next = controller.getPillar(0);
        int gap = (int)next.getX() - 95;
        int towerWidth = (int)next.getWidth();
        return length <= towerWidth + gap && length >= gap;

    }

    // Method to check if the length is perfect for an extra score
    public boolean isPerfect(GameController controller) {
        Rectangle tower = controller.getPillar(0);
        int towerWidth = (int) tower.getWidth();
        System.out.println("Length: " + length);
        int gap = (int)tower.getX() - 95;
        System.out.println("Equal to: " + gap + towerWidth / 2);
        return length >= gap + (towerWidth / 2) - 5 && length <= gap + (towerWidth/2) + 5;
    }

    public void setDifficulty(String difficulty){
        switch (difficulty){
            case "Medium" -> MULTIPLIER = 15;
            case "Hard" -> MULTIPLIER = 20;
            default -> MULTIPLIER = 10;
        }

        System.out.println(MULTIPLIER);
    }

}
