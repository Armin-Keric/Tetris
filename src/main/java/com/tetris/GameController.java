package com.tetris;

import com.tetris.shapes.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.util.Duration;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

import java.net.URL;
import java.util.*;


public class GameController implements Initializable {
    public Pane gamePane;
    public Pane nextBlockPane;
    public Label currentScoreLabel;
    public Label highscoreLabel;
    public Label currentLevelLabel;
    public Label linesLabel;
    public ScrollBar volumeScrollBar;
    public ComboBox<String> songChoiceComboBox;
    public MenuButton settingsMenuButton;
    private Media media;
    private MediaPlayer mediaPlayer;
    private boolean scoreSaved = false;

    private final Random random = new Random();
    private final ArrayList<Shape> arrayList = new ArrayList<>(Arrays.asList(
            new I(15, 10, 20),
            new J(15, 10, 20),
            new L(15, 10, 20),
            new O(15, 10, 20),
            new S(15, 10, 20),
            new T(15, 10, 20),
            new Z(15, 10, 20)));

    // Everything inherits from Shape so we can just use Shape here
    private final Shape form = arrayList.get(random.nextInt(arrayList.size()));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gamePane.setFocusTraversable(true);
        gamePane.requestFocus();
        gamePane.getChildren().add((form));

        Timeline gameLoop = new Timeline(new KeyFrame(Duration.millis(500), e -> form.moveDown(form)));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();

        initializeScoreLabels();
        refreshHighscoreLabel();
        initVolumeSlider();
        addMusicToBox();
        songChosenComboBox();

        settingsMenuButton.showingProperty().addListener((obs, wasShowing, isShowing) -> {
            if (!isShowing) {
                Platform.runLater(() -> gamePane.requestFocus());
            }
        });
    }


    public void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case E -> form.rotateRight(form);
            case S -> form.moveDown(form);
            case A, D -> form.moveAD(form, keyEvent);
            case SPACE -> form.hardDrop(form);
        }
    }

    private void initializeScoreLabels() {
        if ("Label".equals(currentScoreLabel.getText())) {
            currentScoreLabel.setText("0");
        }
        if ("Label".equals(highscoreLabel.getText())) {
            highscoreLabel.setText("0");
        }
        if ("Label".equals(currentLevelLabel.getText())) {
            currentLevelLabel.setText("1");
        }
        if ("Label".equals(linesLabel.getText())) {
            linesLabel.setText("0");
        }
    }

    private void refreshHighscoreLabel() {
        String top = HighscoreManager.getInstance().getTopScore().map(entry -> String.valueOf(entry.getScore())).orElse("0");
        highscoreLabel.setText(top);
    }

    // Aufrufen, sobald bestehende Logik "Game Over" ausloest.
    public void onGameOverSaveScore() {
        if (scoreSaved) {
            return;
        }
        int score = parseScore(currentScoreLabel.getText());
        if (score <= 0) {
            return;
        }
        HighscoreManager.getInstance().addScore(score, "Spieler");
        scoreSaved = true;
    }

    private int parseScore(String text) {
        if (text == null || text.isBlank()) {
            return 0;
        }
        String digits = text.replaceAll("[^0-9]", "");
        if (digits.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(digits);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void songChosenComboBox() {
        Thread musicThread = new Thread(() -> {
            if (mediaPlayer != null) {
                mediaPlayer.volumeProperty().unbind();
                mediaPlayer.stop();
                mediaPlayer.dispose();
            }

            String songName = songChoiceComboBox.getSelectionModel().getSelectedItem();
            String musicFile = "./src/main/resources/com/tetris/assets/audio/" + songName + ".mp3";
            media = new Media(new File(musicFile).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.play();
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.volumeProperty().bind(volumeScrollBar.valueProperty());
        });
        musicThread.start();
    }

    public void addMusicToBox() {
        songChoiceComboBox.getItems().addAll("Theme", "Sneaky Snitch", "Hidden Agenda", "Samuel-Remix");

        songChoiceComboBox.setValue(songChoiceComboBox.getItems().get(0));
    }

    public void initVolumeSlider() {
        volumeScrollBar.setMin(0.0);
        volumeScrollBar.setMax(1.0);
        volumeScrollBar.setValue(0.1);
    }
}