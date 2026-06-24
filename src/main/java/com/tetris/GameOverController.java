package com.tetris;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOverController extends Pane {

    @FXML
    private Label finalScoreLabel;
    @FXML
    private TextField nameField;
    @FXML
    private Button saveButton;
    @FXML
    private ListView<String> highscoreListView;

    private int finalScore;
    private boolean saved = false;

    public void initialize() {
        refreshHighscore();
    }

    /** Called by the GameController with the score reached in the finished game. */
    public void setFinalScore(int score) {
        this.finalScore = score;
        if (finalScoreLabel != null) {
            finalScoreLabel.setText("Score: " + score);
        }
    }

    @FXML
    public void onSaveButtonClicked() {
        if (saved) {
            return;
        }

        String name = nameField.getText();
        if (name == null || name.isBlank()) {
            name = "Player";
        }
        name = name.trim();

        HighscoreManager.getInstance().addScore(finalScore, name);
        saved = true;

        // Lock the inputs so the same score can't be saved twice.
        nameField.setDisable(true);
        saveButton.setDisable(true);

        refreshHighscore();
    }

    private void refreshHighscore() {
        if (highscoreListView == null) {
            return;
        }
        highscoreListView.getItems().clear();
        HighscoreManager.getInstance()
                .getTop(10)
                .forEach(entry -> highscoreListView.getItems().add(entry.toString()));
    }

    @FXML
    public void onRetryButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("game-view.fxml"));
        Scene scene = new Scene(loader.load());

        Node button = (Node) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    public void onQuitButtonClicked() {
        System.exit(0);
    }
}
