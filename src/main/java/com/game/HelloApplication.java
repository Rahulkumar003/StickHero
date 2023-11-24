package com.game;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loadingScreenLoader = new FXMLLoader(HelloApplication.class.getResource("loading-screen.fxml"));
        Parent loadingScreenRoot = loadingScreenLoader.load();
        Scene loadingScreenScene = new Scene(loadingScreenRoot, 335, 600);

        stage.setTitle("Stick Hero Game - Loading Screen");
        stage.setScene(loadingScreenScene);
        stage.show();

        // 4-second delay before  main menu
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> loadMainMenu(stage));
        delay.play();
    }

    private void loadMainMenu(Stage stage) {
        try {
            FXMLLoader mainMenuLoader = new FXMLLoader(HelloApplication.class.getResource("main-menu.fxml"));
            Parent mainMenuRoot = mainMenuLoader.load();
            Scene mainMenuScene = new Scene(mainMenuRoot, 335, 600);

            stage.setTitle("Stick Hero Game - Main Menu");
            stage.setScene(mainMenuScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
