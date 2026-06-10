package com.tetris;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOverController extends Pane {


    public void initialize() throws IOException {}

    /*
    public void refreshHighscore() {
        HighscoreManager highscore = HighscoreManager.getInstance();
        highscoreListView.getItems().clear();

        highscore.getRecent(10).forEach(entry -> highscoreListView.getItems().add(entry.toString()));
    }
     */

    /*
    public void onHighscoreButtonClicked() {
        highscorePane.setVisible(!highscorePane.isVisible());
    }
     */

    @FXML
    public void onRetryButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("game-view.fxml"));
        Scene scene = new Scene(loader.load());

        javafx.scene.Node button = (javafx.scene.Node) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    public void onQuitButtonClicked() {
        System.exit(0);
    }
}
