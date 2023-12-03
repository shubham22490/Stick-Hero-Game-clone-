package com.game.ap_project;


import javafx.scene.shape.Rectangle;

public class StickFigure {
    private boolean flipped; // Flag to indicate if the stick figure is flipped

    // Constructor to initialize stick figure properties
    public StickFigure() {
        this.flipped = false;
    }



    // Method to move the stick figure horizontally
    public void move(GameController controller, boolean value) {
        int dest = 0;
        if(value){
            Rectangle tower = controller.getPillar(0);
            dest = (int)tower.getX() + (int)tower.getWidth() - 50;
        } else{
            dest = (int)controller.getStick().getHeight() + 70;
        }
        System.out.println(dest);
        controller.moveHero(dest, value);
    }

//     Method to make the stick figure fall (move vertically down)
//    public void fall() {
//        y += deltaY;
//    }

    // Method to flip the stick figure
    public void flip() {
        flipped = true;
    }

    // Method to check if the stick figure is flipped
    public boolean flipCheck() {
        return flipped;
    }

    // Method to draw the stick figure on the screen
//    public void draw(Graphics g) {
//        g.setColor(Color.BLACK);
//        if (!flipped) {
//            g.drawLine(x, y, x, y - height); // Body
//            g.drawLine(x, y - height, x - height, y - height * 2); // Left leg
//            g.drawLine(x, y - height, x + height, y - height * 2); // Right leg
//        } else {
//            g.drawLine(x, y, x, y + height); // Flipped body
//            g.drawLine(x, y + height, x - height, y + height * 2); // Flipped left leg
//            g.drawLine(x, y + height, x + height, y + height * 2); // Flipped right leg
//        }
//    }

//    // Getter method for the x-coordinate of the stick figure
//    public int getX() {
//        return x;
//    }
//
//    // Getter method for the y-coordinate of the stick figure
//    public int getY() {
//        return y;
//    }
}
