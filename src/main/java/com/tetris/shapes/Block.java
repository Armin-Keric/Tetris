package com.tetris.shapes;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Group {
    private Position pos;
    private int size = 0;

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
        this.setTranslateX(pos.getX() * size);
        this.setTranslateY(pos.getY() * size);
    }

    public Block(int size, Color color) {
        pos = new Position(0, 0);
        this.size = size;

        Rectangle rectangle = new Rectangle(size, size);
        rectangle.setFill(color);
        rectangle.setStroke(Color.rgb(20, 20, 35));
        rectangle.setStrokeWidth(1);

        this.getChildren().add(rectangle);
    }
}