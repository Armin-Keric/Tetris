package com.tetris;

import com.tetris.shapes.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.*;


public class GameController implements Initializable {
    public Pane gamePane;
    public Pane gameFieldPane;
    public Pane nextBlockPane;
    public Label currentScoreLabel;
    public Label highscoreLabel;
    public Label currentLevelLabel;
    public Label linesLabel;

    private Random random = new Random();
    private ArrayList<Shape> arrayList = new ArrayList<>(Arrays.asList(new I(15, 10, 20),
            new J(15, 10, 20),
            new L(15, 10, 20),
            new O(15, 10, 20),
            new S(15, 10, 20),
            new T(15, 10, 20),
            new Z(15, 10, 20)));

    //Everything inherits from Shape so we can just use Shape here (is cleaner than Object too)
    private Shape form = arrayList.get(random.nextInt(arrayList.size()));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gamePane.setFocusTraversable(true);
        gamePane.requestFocus();
        gamePane.getChildren().add((form));

    }

    public void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case Q -> form.rotateLeft(form);
            case E -> form.rotateRight(form);
            case S -> form.moveDown(form);
            case A, D -> form.moveAD(form,keyEvent);
            case SPACE -> form.hardDrop(form);
        }
    }


    public ArrayList<Shape> getArrayList() {
        return arrayList;
    }
}