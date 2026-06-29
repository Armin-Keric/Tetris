package com.tetris;

import com.tetris.shapes.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    public Pane gamePane;
    public Pane gameFieldPane;
    public Pane nextBlockPane;
    public Label currentScoreLabel;
    public Label highscoreLabel;
    public Label currentLevelLabel;
    public Label linesLabel;
    public ScrollBar volumeScrollBar;
    public ComboBox<String> songChoiceComboBox;
    public MenuButton settingsMenuButton;

    private static Media media;
    private static MediaPlayer mediaPlayer;
    public Label timeLabel;

    private boolean gameOver = false;

    private Timeline gameLoop;

    public GameLogic gameLogic = new GameLogic();

    private Random random = new Random();

    private Shape form = setRandom();
    private Shape next = setRandom();
    private Shape nextPreview;

    public Shape setRandom() {
        return switch (random.nextInt(7)) {
            case 0 -> new I(15, 10, 20, gameLogic);
            case 1 -> new J(15, 10, 20, gameLogic);
            case 2 -> new L(15, 10, 20, gameLogic);
            case 3 -> new O(15, 10, 20, gameLogic);
            case 4 -> new S(15, 10, 20, gameLogic);
            case 5 -> new T(15, 10, 20, gameLogic);
            default -> new Z(15, 10, 20, gameLogic);
        };
    }

    private Shape createPreviewShape(Shape shape) {
        if (shape instanceof I) {
            return new I(15, 10, 20, gameLogic);
        }
        if (shape instanceof J) {
            return new J(15, 10, 20, gameLogic);
        }
        if (shape instanceof L) {
            return new L(15, 10, 20, gameLogic);
        }
        if (shape instanceof O) {
            return new O(15, 10, 20, gameLogic);
        }
        if (shape instanceof S) {
            return new S(15, 10, 20, gameLogic);
        }
        if (shape instanceof T) {
            return new T(15, 10, 20, gameLogic);
        }
        return new Z(15, 10, 20, gameLogic);
    }

    private void showNextPreview() {
        nextBlockPane.getChildren().clear();

        nextPreview = createPreviewShape(next);
        nextBlockPane.getChildren().add(nextPreview);

        Platform.runLater(() -> {
            nextPreview.setTranslateX(0);
            nextPreview.setTranslateY(0);
            nextPreview.setLayoutX(0);
            nextPreview.setLayoutY(0);

            Bounds bounds = nextPreview.getBoundsInParent();

            double centerX = (nextBlockPane.getWidth() - bounds.getWidth()) / 2 - bounds.getMinX();
            double centerY = (nextBlockPane.getHeight() - bounds.getHeight()) / 2 - bounds.getMinY();

            nextPreview.setTranslateX(centerX);
            nextPreview.setTranslateY(centerY);
        });
    }

    private void resetGamePosition(Shape shape) {
        shape.setTranslateX(0);
        shape.setTranslateY(0);
        shape.setLayoutX(0);
        shape.setLayoutY(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gamePane.setFocusTraversable(true);
        gamePane.requestFocus();

        resetGamePosition(form);
        gamePane.getChildren().add(form);

        showNextPreview();

        gameLoop = new Timeline(
                new KeyFrame(Duration.millis(500), e -> {
                    form.moveDown(form);
                    if (form.isOnFloor()) {
                        gameChange();
                    }
                    updateScoreLabels();
                })
        );

        gameLoop.setCycleCount(Timeline.INDEFINITE);
        final GameTimer gameTimer = new GameTimer(timeLabel);
        gameTimer.gameTimer();
        gameLoop.play();

        initializeScoreLabels();
        updateScoreLabels();
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

    public void onKeyPressed(KeyEvent keyEvent) throws IOException {

        if (gameOver || form.isOnFloor()) {
            return;
        }

        switch (keyEvent.getCode()) {
            case E -> form.rotateRight(form);
            case S -> form.moveDown(form);
            case A, D -> form.moveAD(form, keyEvent);
            case SPACE -> form.hardDrop(form);
        }


        if (form.isOnFloor()) {
            gameChange();
        }

        updateScoreLabels();
    }

    /** Mirror the running ScoreManager into the on-screen labels. */
    private void updateScoreLabels() {
        ScoreManager sm = gameLogic.getScoreManager();
        currentScoreLabel.setText(String.valueOf(sm.getScore()));
        // ScoreManager counts levels from 0; players expect level 1 upwards.
        linesLabel.setText(String.valueOf(sm.getTotalLines()));
    }

    private void initializeScoreLabels() {
        if ("Label".equals(currentScoreLabel.getText())) {
            currentScoreLabel.setText("0");
        }
        if ("Label".equals(highscoreLabel.getText())) {
            highscoreLabel.setText("0");
        }
        if ("Label".equals(linesLabel.getText())) {
            linesLabel.setText("0");
        }
    }

    private void refreshHighscoreLabel() {
        String top = HighscoreManager.getInstance()
                .getTopScore()
                .map(entry -> String.valueOf(entry.getScore()))
                .orElse("0");

        highscoreLabel.setText(top);
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

    public void gameChange() {
        if (form.isOnFloor()) {
            form = next;
            resetGamePosition(form);
            gamePane.getChildren().add(form);

            // The new piece spawns on top of locked blocks -> the stack reached
            // the top, so the game is over.
            if (isSpawnBlocked(form)) {
                endGame();
                return;
            }

            next = setRandom();
            showNextPreview();
        }
    }

    private boolean isSpawnBlocked(Shape shape) {
        for (Block block : shape.getBlocks()) {
            if (gameLogic.isOccupied(block.getPos().getX(), block.getPos().getY())) {
                return true;
            }
        }
        return false;
    }

    private void endGame() {
        if (gameOver) {
            return;
        }
        gameOver = true;

        if (gameLoop != null) {
            gameLoop.stop();
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("game-over-view.fxml"));
            Scene scene = new Scene(loader.load());

            // Hand the final score to the game-over screen, where the player
            // types a name and saves it to the highscore list.
            GameOverController controller = loader.getController();
            controller.setFinalScore(gameLogic.getScoreManager().getScore());

            Stage stage = (Stage) gamePane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}