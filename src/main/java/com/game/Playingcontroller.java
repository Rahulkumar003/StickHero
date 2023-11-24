package com.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Playingcontroller {
    private boolean isExtended = false;
    public Rectangle nextplatform;
    public Rectangle platform;

    @FXML
    private Pane pane;

    @FXML
    private Rectangle stick;

    private Timeline extendTimeline;

    @FXML
    public void initialize() {
        // Run later to ensure that the scene is set
        pane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                setupKeyHandlers(newScene);
            }
        });

        // Set focus on the pane to receive key events
        pane.setFocusTraversable(true);
        pane.requestFocus();

        // Create Timeline for extending the stick
        double stickSpeed = 3.0;
        extendTimeline = createTimeline(stickSpeed);
    }

    private void setupKeyHandlers(Scene scene) {
        // Set up key event handlers
        scene.setOnKeyPressed(this::keypressed);
        scene.setOnKeyReleased(this::keyReleased);
    }

    @FXML
    private void keypressed(KeyEvent event) {
        if (event.getCode() == KeyCode.CONTROL) {
            extendTimeline.play();
            System.out.println("Extending...");
            isExtended=true;
        }
    }

    @FXML
    private void keyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.CONTROL) {
            extendTimeline.stop();
            System.out.println(stick.getHeight());
            System.out.println("Stop extending");
            isExtended=true;
        }
    }

    private Timeline createTimeline(double delta) {
        Timeline timeline=new Timeline(
                new KeyFrame(Duration.millis( 10), e -> extendStick(delta))
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely until stopped
        return timeline;
    }

    private void extendStick(double delta) {
        stick.setY(stick.getY() - delta);
        stick.setHeight(stick.getHeight() + delta);
    }
}
