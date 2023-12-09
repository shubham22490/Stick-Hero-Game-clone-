package com.game.ap_project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.game.ap_project.GameController;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StickFigureTest {

    private GameController gameController;

    @BeforeEach
    void setUp() {
        // Set up a mock GameController for testing
        gameController = new GameController();
    }

    @Test
    void testSetStraight() {
        // Assuming GameController's moveHero method is correctly implemented
        StickFigure.setStraigth(gameController);

        // Perform assertions based on the expected behavior after setting straight
    }
}

