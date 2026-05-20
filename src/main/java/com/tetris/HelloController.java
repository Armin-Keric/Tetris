package com.tetris;

import com.tetris.shapes.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ResourceBundle;


public class HelloController implements Initializable {
    public Pane gamePane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gamePane.setFocusTraversable(true);
        gamePane.requestFocus();
        S form=new S(15, 10,20);
        gamePane.getChildren().add(form);
    }

}