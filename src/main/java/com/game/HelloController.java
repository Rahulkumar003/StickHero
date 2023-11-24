package com.game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloController {

    @FXML
    private Rectangle platform;

    @FXML
    private Button playbutton;

    @FXML
    private ImageView stickman;
    @FXML
    private ImageView exitbutton;

    @FXML
    void playgame(MouseEvent event) throws IOException {
        // Add print statements for debugging
        System.out.println("Clicked playbutton");
        Stage stage= (Stage) playbutton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("playing-game.fxml")));
        Scene playingscene=new Scene(root,335,600);
        stage.setScene(playingscene);
    }
    @FXML
    void gotoreplaymenu(MouseEvent event) throws IOException {
        Stage stage= (Stage) exitbutton.getScene().getWindow();
        Parent root2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Replay-screen.fxml")));
        Scene playingscene=new Scene(root2,335,600);
        stage.setScene(playingscene);
    }

}
