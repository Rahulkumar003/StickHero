package com.game;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.Test;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static org.testng.AssertJUnit.assertEquals;

public class PlayingController {


    @FXML
    private Label score;
    private boolean isExtended = false;
    private boolean isRotated = false;
    private boolean isRotating = false;
    private boolean isPlayerMoved = false;
    public Rectangle nextplatform;
    public Rectangle platform;
    @FXML
    private Pane pane;
    @FXML
    private Line stick;
    private Timeline extendTimeline;
    private Timeline rotateTimeline;
    private double playerX;
    private boolean isfallen;
//    private Line newStick;
    public static int scores=0;
    public ImageView stickman;
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
            System.out.println("stick endY: " + stick.getEndY());
            System.out.println("Stop extending");
//            stickEndX = stick.getEndX();
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
            playerX = -stick.getEndY() + 5;

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

        double nextPlatformStartX = nextplatform.getLayoutX()+nextplatform.getTranslateX();
        double nextPlatformEndX = nextPlatformStartX + nextplatform.getWidth();
//
        System.out.println("stickmanEndX: " + stickmanEndX);
        System.out.println("nextPlatformEndX: " + nextPlatformEndX);
        System.out.println("nextPlatformStartX: " + nextPlatformStartX);

        if (stickmanEndX > nextPlatformStartX && stickmanEndX< nextPlatformEndX) {
            System.out.println("Stickman is between the next platform, move to the next platform's end");
            scores=scores+1;
            score.setText(String.valueOf(scores));
            playerLandOnNext();
        } else {
            System.out.println("Stickman is not between the next platform, it falls");
            playerFalls();

        }
    }

    private void playerFalls() {
        double fallDuration = 1000; // Adjust the duration as needed
        double paneHeight = pane.getHeight();

        TranslateTransition translateTransitionY = new TranslateTransition(Duration.millis(fallDuration), stickman);
        translateTransitionY.setToY(paneHeight);
        translateTransitionY.setInterpolator(Interpolator.EASE_BOTH);

        // Set event handler for when the fall transition finishes
        translateTransitionY.setOnFinished(event -> {
            // Perform the scene transition after the fall animation
            navigateToReplayScreen();
        });

        // Play the translation transition
        translateTransitionY.play();
    }

    private void navigateToReplayScreen() {
        try {
            Stage stage = (Stage) pane.getScene().getWindow();
            Parent root2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Replay-screen.fxml")));
            Scene playingscene = new Scene(root2, 335, 600);
            stage.setScene(playingscene);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }


    private void playerLandOnNext() {
        double platformX = nextplatform.getLayoutX()-5;
        double stickmanX = nextplatform.getLayoutX()-5;
        stick.setStartY(0);
        stick.setStartX(0);
        stick.setEndX(0);
        stick.setEndY(0);
        stick.setStrokeWidth(0);

        // Calculate the translation distance for both stickman and platform
        double stickmanTranslation = -stickmanX;
        double platformTranslation = -platformX;


        // Create a timeline for platform translation
        TranslateTransition translateTransitionPlatform = new TranslateTransition(Duration.millis(3000), nextplatform);
        translateTransitionPlatform.setByX(platformTranslation);
        translateTransitionPlatform.setInterpolator(Interpolator.EASE_BOTH);

        // Create a timeline for stickman translation
        TranslateTransition translateTransitionStickman = new TranslateTransition(Duration.millis(3000), stickman);
        translateTransitionStickman.setByX(stickmanTranslation);
        translateTransitionStickman.setInterpolator(Interpolator.EASE_BOTH);

        // Create a timeline for platform translation
        TranslateTransition translateTransitionPlatform2 = new TranslateTransition(Duration.millis(3000), platform);
        translateTransitionPlatform2.setByX(platformTranslation);
        translateTransitionPlatform2.setInterpolator(Interpolator.EASE_BOTH);

        // Play both transitions together
        ParallelTransition parallelTransition = new ParallelTransition(
                translateTransitionStickman,
                translateTransitionPlatform,
                translateTransitionPlatform2
        );

        // Set event handler for when the transitions finish
        parallelTransition.setOnFinished(event -> {
            platform=nextplatform;
            createPlatform();
            System.out.println("Transition Finished");
        });

        parallelTransition.play();
    }

    private void createPlatform() {
        // Define minimum and maximum width for the platform
        double minWidth = 20.0;
        double maxWidth = 100.0;

        Random random = new Random();
        double r2 = random.nextDouble(20, 100);
        double r1 = random.nextDouble(30, 80);

        // Generate random width and X layout within the specified ranges
        double platformWidth = r2 + 20;

        // Create a new platform with the generated width and height
        Rectangle newPlatform = new Rectangle(platformWidth, 200); // Adjust height as needed
        newPlatform.setFill(Color.BLACK); // Adjust color as needed
        newPlatform.setLayoutX(300); // Set random X layout
        newPlatform.setLayoutY(401.0); // Adjust Y position as needed

        // Add the new platform to the game scene
        pane.getChildren().add(newPlatform);

        // Create a timeline for the platform translation
        TranslateTransition translateTransitionPlatform = new TranslateTransition(Duration.millis(500), newPlatform);
        translateTransitionPlatform.setByX(-r1); // Translate by the negative of the initial X layout
        translateTransitionPlatform.setInterpolator(Interpolator.EASE_BOTH);

        // Set event handler for when the transition finishes
        translateTransitionPlatform.setOnFinished(event -> {
            nextplatform=newPlatform;
            createStick();

        });

        // Play the transition
        translateTransitionPlatform.play();
    }
    private void createStick() {
        // Create a new stick
        Line newStick = new Line();
        newStick.setStartX(5.199974060058594);
        newStick.setStartY(0);
        newStick.setEndX(5.199974060058594);
        newStick.setEndY(0); // Adjust stick length as needed
        newStick.setStroke(Color.BLACK); // Adjust color as needed
        newStick.setStrokeWidth(4.0); // Adjust stroke width as needed

        // Calculate the initial X position for the stick based on the new platform's layout
        double initialStickX = nextplatform.getLayoutX() + newStick.getStartX()+platform.getWidth();
        newStick.setLayoutX(initialStickX);

        // Set the initial Y position for the stick
        newStick.setLayoutY(400.0); // Adjust initial Y position as needed

        // Add the new stick to the game scene
        pane.getChildren().add(newStick);

        // Calculate the translation distance for the stick
        double stickTranslation = -nextplatform.getLayoutX();

        // Create a timeline for the stick translation
        TranslateTransition translateTransitionStick = new TranslateTransition(Duration.millis(500), newStick);
        translateTransitionStick.setByX(stickTranslation);
        translateTransitionStick.setInterpolator(Interpolator.EASE_BOTH);

        // Set event handler for when the transition finishes
        translateTransitionStick.setOnFinished(event -> {
            stick = newStick;
            isExtended = false;
            isRotated = false;
            isRotating = false;
            isPlayerMoved = false;
            System.out.println("Stick updated");
        });

        // Play the transition
        translateTransitionStick.play();
    }
    @Test
    public void testExtendStick() {
        PlayingController playingController = new PlayingController();
        playingController.initialize();

        // Assuming extendStick method increments stick's endY
        playingController.extendStick(1.0);

        // Assert that the endY value has been updated
        assertEquals(playingController.stick.getEndY(), 1.0, 0.01);
    }



    public static class CherryCollector implements Runnable {
        private volatile boolean running = true;
        private int cherries;
        private final Label cherriesLabel;

        public CherryCollector(Label cherriesLabel) {
            this.cherriesLabel = cherriesLabel;
        }

        public void stop() {
            running = false;
        }

        @Override
        public void run() {
            while (running) {
                collectCherries();
                updateUI();
                sleep();
            }
        }

        private void collectCherries() {
            // Simulate some cherries being collected
            int collectedCherries = (int) (Math.random() * 5) + 1;
            cherries += collectedCherries;
        }

        private void updateUI() {
            // Update the cherries count on the UI thread
            Platform.runLater(() -> cherriesLabel.setText("Cherries: " + cherries));
        }

        private void sleep() {
            try {
                // Sleep for a while to simulate a delay between cherry calculations
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        private CherryCollector cherryCollector;

        private void startCherryCollection() {
            cherriesLabel.setText("Cherries: 0");

            cherryCollector = new CherryCollector(cherriesLabel);
            Thread cherryThread = new Thread(cherryCollector);
            cherryThread.setDaemon(true);
            cherryThread.start();
        }

        private void stopCherryCollection() {
            if (cherryCollector != null) {
                cherryCollector.stop();
            }
        }

    }






}
