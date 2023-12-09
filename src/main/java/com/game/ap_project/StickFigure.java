package com.game.ap_project;


import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;

public class StickFigure {
    private static boolean flipped; // Flag to indicate if the stick figure is flipped

    // Constructor to initialize stick figure properties
    public StickFigure() {
        flipped = false;
    }



    // Method to move the stick figure horizontally
    public static void move(GameController controller, boolean value) {
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

    public static void setStraigth(GameController controller){
        if(flipCheck()){
            flip(controller);
        }
    }

    // Method to flip the stick figure
    public static void flip(GameController controller) {
        ImageView hero = controller.getHero();
        if(!flipped){
//            Rotate rotate = new Rotate();
//            rotate.setAxis(Rotate.X_AXIS);
//            rotate.setAngle(180);
//            hero.getTransforms().add(rotate);
            Scale scale = new Scale(1, -1);
            hero.getTransforms().add(scale);
            System.out.println(hero.getTransforms());
//            hero.setY(hero.getY() - 2 * hero.getFitHeight());
//            System.out.println(hero.getTransforms());
//            System.out.println(hero.isVisible());

            flipped = true;
        } else {
            hero.getTransforms().clear();
//            hero.setY(hero.getY() + 2* hero.getFitHeight());
            flipped = false;
        }
    }

    // Method to check if the stick figure is flipped
    public static boolean flipCheck() {
        return flipped;
    }

}
