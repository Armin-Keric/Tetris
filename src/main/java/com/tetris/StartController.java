package com.tetris;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {
    public AnchorPane highscorePane;
    public ListView highscoreListView;

    public void Initialize(){
        highscorePane.setVisible(false);
    }

    public void onHighscoreButtonClicked(MouseEvent mouseEvent) {
        if (highscorePane.isVisible()) {
            highscorePane.setVisible(false);
        } else {
            highscorePane.setVisible(true);
        }
    }

    public void onPlayGameButtonClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("game-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void onQuitButtonClicked(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
