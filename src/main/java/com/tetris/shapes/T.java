package com.tetris.shapes;

import com.tetris.GameLogic;
import javafx.scene.paint.Color;

public class T extends Shape {
    public T(int blockSize, int maxWidth, int maxHeight, GameLogic gameLogic) {
        super(blockSize, maxWidth, maxHeight,gameLogic);
        this.blocks[0] = new Block(blockSize, Color.PURPLE);
        this.blocks[0].setPos(new Position(maxWidth / 2 - 1, 0));
        this.blocks[1] = new Block(blockSize, Color.PURPLE);
        this.blocks[1].setPos(new Position(maxWidth / 2, 0));
        this.blocks[2] = new Block(blockSize, Color.PURPLE);
        this.blocks[2].setPos(new Position(maxWidth / 2 + 1, 0));
        this.blocks[3] = new Block(blockSize, Color.PURPLE);
        this.blocks[3].setPos(new Position(maxWidth / 2, 1));

        for (Block block : blocks) {
            this.getChildren().add(block);
        }
    }
}
