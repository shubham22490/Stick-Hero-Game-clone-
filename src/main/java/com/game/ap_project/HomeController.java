package com.game.ap_project;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.Math.exp;

public class HomeController implements Controller {

    @FXML
    private ComboBox<String> difficulty;

    @FXML
    private AnchorPane homePane;

    @FXML
    private ImageView musicButton;

    @FXML
    private ImageView playButton;

    @FXML
    private VBox musicController;

    @FXML
    private Slider musicSlider;

    @FXML
    private StackPane root;

    private final String bgSound = Objects.requireNonNull(getClass().getResource("sounds/bg.mp3")).toString();
    private MediaPlayer bg;
    private static int volume = 25;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playBg();
        System.out.println(bg.getVolume());
        difficulty.getItems().addAll("Easy", "Medium", "Hard");
        difficulty.setValue("Easy");
        musicSlider.setValue(volume);
        musicController.setVisible(false);

        musicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            setVolume(newValue.intValue());

        });
    }

    @FXML
    void switchController(MouseEvent event) {
        System.out.println();
        musicController.setVisible(!musicController.isVisible());
    }

    @FXML
    void gameStart(MouseEvent event) throws IOException {

    }

    public ImageView getPlayButton() {
        return playButton;
    }

    public String getDifficulty() { return difficulty.getValue();}


    public void switchScene(Parent pane) throws IOException {
        root.getChildren().add(pane);
        Scene scene = playButton.getScene();

        pane.translateXProperty().set(scene.getWidth());

        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), new KeyValue(pane.translateXProperty(), 0, Interpolator.EASE_OUT));
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(e -> {
            root.getChildren().remove(homePane);
        });
        timeline.play();
    }

    public void playBg(){
        bg = new MediaPlayer(new Media(bgSound));
        bg.setCycleCount(MediaPlayer.INDEFINITE);
        setVolume(25);
        bg.play();
    }

    public void setVolume(int vol){
        volume = vol;
        bg.setVolume((float)vol/100);
    }

}
