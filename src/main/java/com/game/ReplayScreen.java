package com.game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ReplayScreen {
    @FXML
    private Rectangle platform1;

    @FXML
    private ImageView stickman;
    @FXML
    private ImageView homebutton;

    @FXML
    private ImageView replaybutton;


    @FXML
    void gohome(MouseEvent event) throws IOException {
        Stage stage = (Stage) homebutton.getScene().getWindow();
        Parent root2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-menu.fxml")));
        Scene playingscene = new Scene(root2, 335, 600);
        stage.setScene(playingscene);
    }

    @FXML
    void replaygame(MouseEvent event) throws IOException {
        Stage stage = (Stage) replaybutton.getScene().getWindow();
        Parent root2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("playing-game.fxml")));
        Scene playingscene = new Scene(root2, 335, 600);
        stage.setScene(playingscene);

    }
}
