package com.tetris;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {
    @FXML
    public AnchorPane highscorePane;
    @FXML
    public ListView<String> highscoreListView;

    @FXML
    public void initialize(){
        highscorePane.setVisible(false);
        refreshHighscore();
    }

    public void refreshHighscore() {
        HighscoreManager highscore = HighscoreManager.getInstance();
        highscoreListView.getItems().clear();

        highscore.getRecent(10).forEach(entry -> highscoreListView.getItems().add(entry.toString()));
    }

    public void onHighscoreButtonClicked() {
        highscorePane.setVisible(!highscorePane.isVisible());
    }

    public void onPlayGameButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("game-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) highscorePane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void onQuitButtonClicked() {
        System.exit(0);
    }
}
