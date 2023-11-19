package com.game.ap_project;

public class Points {
    private int gamePoints;     // Regular game points
    private int cherryPoints;   // Points earned from cherries
    private int highScore;      // High score

    // Constructor to initialize points
    public Points() {
        this.gamePoints = 0;
        this.cherryPoints = 0;
        this.highScore = 0;
    }

    // Method to add regular game points
    public void addGamePoints(int points) {
        gamePoints += points;
    }

    // Method to add points earned from cherries
    public void addCherryPoints(int cherryCount) {
        cherryPoints += cherryCount * 10; // Assuming each cherry is worth 10 points
    }

    // Method to get the current total game points
    public int getGamePoints() {
        return gamePoints;
    }

    // Method to get the current total cherry points
    public int getCherryPoints() {
        return cherryPoints;
    }

    // Method to set the high score
    public void setHighScore() {
        if (gamePoints > highScore) {
            highScore = gamePoints;
        }
    }

    // Method to get the high score
    public int getHighScore() {
        return highScore;
    }
}
