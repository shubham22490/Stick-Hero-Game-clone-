package com.game.ap_project;

public class Points {
    private static int gamePoints = 0;     // Regular game points
    private int cherryPoints;   // Points earned from cherries
    private static int highScore = 0;      // High score

    // Constructor to initialize points
    public Points() {
        this.cherryPoints = 0;
    }

    // Method to add regular game points
    public static void addGamePoints(GameController controller, int points) {
        gamePoints += points;
        controller.getScore().setText(String.valueOf(gamePoints));
    }

    public static void setHighScore(int points){
        if(points > highScore){
            highScore = points;
        }
    }

    public static void setGamePoints(int gamePoints) {
        Points.gamePoints = gamePoints;
    }

    public static int getHighScore(){
        return highScore;
    }

    // Method to add points earned from cherries
    public void addCherryPoints(int cherryCount) {
        cherryPoints += cherryCount * 10; // Assuming each cherry is worth 10 points
    }

    // Method to get the current total game points
    public static int getGamePoints() {
        return gamePoints;
    }

    // Method to get the current total cherry points
    public int getCherryPoints() {
        return cherryPoints;
    }


}
