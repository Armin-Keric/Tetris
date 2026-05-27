package com.tetris.shapes;

import javafx.scene.Group;
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

    public Block(int size) {
        pos = new Position(0, 0);
        this.size = size;
        this.getChildren().add(new Rectangle(size, size));

    }
}
