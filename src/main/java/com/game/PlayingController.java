package com.game;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class PlayingController {
    public ImageView stickman;
    public Rectangle nextplatform1;
    private boolean isExtended = false;
    private boolean isRotated = false;
    private boolean isRotating = false;
    private boolean isPlayerMoved = false;
    private double stickEndX;
    public Rectangle nextplatform;
    public Rectangle platform;
    @FXML
    private Pane pane;
    @FXML
    private Line stick;
    private Timeline extendTimeline;
    private Timeline rotateTimeline;
    private double playerX;

    @FXML
    public void initialize() {
        pane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                setupKeyHandlers(newScene);
            }
        });
        pane.setFocusTraversable(true);
        pane.requestFocus();

        double stickSpeed = 3.0;
        extendTimeline = createTimeline(stickSpeed);
        rotateTimeline = createRotateTimeline();
    }

    private void setupKeyHandlers(Scene scene) {
        // Set up key event handlers
        scene.setOnKeyPressed(this::keypressed);
        scene.setOnKeyReleased(this::keyReleased);
    }

    @FXML
    private void keypressed(KeyEvent event) {
        if (event.getCode() == KeyCode.CONTROL && !isExtended) {
            extendTimeline.play();
            System.out.println("Extending...");
        }
    }

    @FXML
    private void keyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.CONTROL) {
            extendTimeline.stop();
//            System.out.println("stick endX: " + stick.getEndY());
            System.out.println("Stop extending");
            stickEndX = stick.getEndX();
            if (!isRotated) {
                rotateStick();
            }

            isExtended = true;
        }
    }

    private Timeline createTimeline(double delta) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(10), e -> extendStick(delta))
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely until stopped
        return timeline;
    }

    private void extendStick(double delta) {
        stick.setEndY(stick.getEndY() - delta);
    }

    private Timeline createRotateTimeline() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(10), e -> rotateStick())
        );
        timeline.setCycleCount(1); // Rotate once
        return timeline;
    }

    private void rotateStick() {
        if (!isRotating) {
            // Set the pivot point to the bottom center of the stick
            double pivotX = (stick.getStartX() + stick.getEndX()) / 2;
            double pivotY = stick.getStartY();

            double[] totalAngle = {90};

            double angleIncrement = 1; // You can adjust this value for a slower or faster rotation

            // Create a Timeline for rotating the stick
            rotateTimeline = new Timeline(
                    new KeyFrame(Duration.millis(10), e -> {
                        Rotate rotate = new Rotate(angleIncrement, pivotX, pivotY);
                        stick.getTransforms().add(rotate);
                        totalAngle[0] -= angleIncrement;

                        if (totalAngle[0] <= 0) {
                            rotateTimeline.stop();
                            isRotating = true;
                            movePlayerToStickEnd();
                        }
                    })
            );
            rotateTimeline.setCycleCount(Timeline.INDEFINITE);
            rotateTimeline.play();
        }
    }

    @FXML
    private void movePlayerToStickEnd() {
        if (!isPlayerMoved) {
            playerX = -stick.getEndY() + 6;

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), stickman);
            translateTransition.setToX(playerX);
            translateTransition.setInterpolator(Interpolator.EASE_BOTH);

            translateTransition.setOnFinished(event -> {
                isPlayerMoved = true;
                checkLanding();
            });

            translateTransition.play();
        }
    }

    private void checkLanding() {
        double stickmanEndX = stickman.getTranslateX()+platform.getWidth() ;

        double nextPlatformStartX = nextplatform.getLayoutX();
        double nextPlatformEndX = nextPlatformStartX + nextplatform.getWidth();
//
//        System.out.println("stickmanEndX: " + stickmanEndX);
//        System.out.println("nextPlatformEndX: " + nextPlatformEndX);
//        System.out.println("nextPlatformStartX: " + nextPlatformStartX);

        if (stickmanEndX > nextPlatformStartX && stickmanEndX< nextPlatformEndX) {
            System.out.println("Stickman is between the next platform, move to the next platform's end");
            playerLandOnNext();
        } else {
            System.out.println("Stickman is not between the next platform, it falls");
            playerFalls();
        }
    }

    private void playerFalls() {
        double fallDuration = 1000; // Adjust the duration as needed
        double paneHeight = pane.getHeight();

        // Create TranslateTransition for smoothly translating the player in Y-axis
        TranslateTransition translateTransitionY = new TranslateTransition(Duration.millis(fallDuration), stickman);
        translateTransitionY.setToY(paneHeight);
        // Set interpolator for smooth Y-axis transition
        translateTransitionY.setInterpolator(Interpolator.EASE_BOTH);
        // Play the translation transition
        translateTransitionY.play();
    }

    private void playerLandOnNext() {
        double moveDuration = 100; // Adjust the duration as needed
        double nextPlatformEndX = nextplatform.getLayoutX() + nextplatform.getWidth();
        TranslateTransition translateTransitionX = new TranslateTransition(Duration.millis(moveDuration), stickman);
        translateTransitionX.setToX(nextPlatformEndX - stickman.getFitWidth());

        // Set interpolator for smooth X-axis transition
        translateTransitionX.setInterpolator(Interpolator.EASE_BOTH);

        // Play the translation transition
        translateTransitionX.play();
    }
//    public void transitionall(double dist,Runnable aftertransition){
//        setscore();
//        imhandle.update();
//        for(Node i:pane.getChildren()){
//            if(i.getClass().equals(Rectangle.class)){
//                TranslateTransition t=new TranslateTransition(Duration.millis(1000), i);
//                t.setByX(-dist);
//                t.play();
//                t.setOnFinished(e->{
//                    if(transitioning){
//                        transitioning=false;
//                        aftertransition.run();
//                    }
//
//
//                });
//                if(i!=s.getRect()){ //check if object is of platform class
//                    Rectangle rect=(Rectangle)i;
//                    // System.out.println("platform moved\n\n");
//                    t.setOnFinished(event -> {
//                        rect.setX(rect.getX() + rect.getTranslateX());
//                        rect.setY(rect.getY() + rect.getTranslateY());
//                        rect.setTranslateX(0);
//                        rect.setTranslateY(0);
//                    });
//                }
//            }
//
//
//
//        }
//
//
//    }
}
