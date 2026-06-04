package com.tetris.shapes;

import com.tetris.GameLogic;

public class S extends Shape {
    public S(int blockSize, int maxWidth, int maxHeight, GameLogic gameLogic) {
        super(blockSize, maxWidth, maxHeight,gameLogic);
        this.blocks[0] = new Block(blockSize);
        this.blocks[0].setPos(new Position(maxWidth / 2 + 1, 0));
        this.blocks[1] = new Block(blockSize);
        this.blocks[1].setPos(new Position(maxWidth / 2, 0));
        this.blocks[2] = new Block(blockSize);
        this.blocks[2].setPos(new Position(maxWidth / 2, 1));
        this.blocks[3] = new Block(blockSize);
        this.blocks[3].setPos(new Position(maxWidth / 2 - 1, 1));

        for (Block block : blocks) {
            this.getChildren().add(block);
        }
    }
}
