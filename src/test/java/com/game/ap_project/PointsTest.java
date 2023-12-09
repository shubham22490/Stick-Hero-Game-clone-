package com.game.ap_project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointsTest {

    private GameController controller;


    void setUp() {
        controller = new GameController();
    }


//    @Test
//    void addGamePoints() {
//        Points.addGamePoints(controller, 10);
//        assertEquals(10, Points.getGamePoints());
//        assertEquals("10", controller.getScore().getText());
//
//        // Adding more points
//        Points.addGamePoints(controller, 20);
//        assertEquals(30, Points.getGamePoints());
//        assertEquals("30", controller.getScore().getText());
//    }

    @Test
    void setHighScore() {
        Points.setHighScore(50);
        assertEquals(50, Points.getHighScore());

        // Setting a lower score should not change the high score
        Points.setHighScore(30);
        assertEquals(50, Points.getHighScore());

        // Setting a higher score should update the high score
        Points.setHighScore(60);
        assertEquals(60, Points.getHighScore());
    }

    @Test
    void setGamePoints() {
        Points.setGamePoints(150);
        assertEquals(150, Points.getGamePoints());
    }

//    @Test
//    void getHighScore() {
//    }

    @Test
    void addCherryPoints() {
        Points points = new Points();
        points.addCherryPoints(3);
        assertEquals(30, points.getCherryPoints());

        // Adding more cherry points
        points.addCherryPoints(2);
        assertEquals(50, points.getCherryPoints());
    }

    @Test
    void getGamePoints() {
        Points.setGamePoints(150);
        assertEquals(150, Points.getGamePoints());
    }

    @Test
    void getCherryPoints() {
        Points points = new Points();
        points.addCherryPoints(5);
        assertEquals(50, points.getCherryPoints());
    }
}