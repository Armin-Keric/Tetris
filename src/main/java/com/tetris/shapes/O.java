package com.tetris.shapes;


public class O extends Shape {
    public O(int blockSize, int maxWidth, int maxHeight) {
        super(blockSize, maxWidth, maxHeight);
        this.blocks[0] = new Block(blockSize);
        this.blocks[0].setPos(new Position(maxWidth / 2 - 1, 0));
        this.blocks[1] = new Block(blockSize);
        this.blocks[1].setPos(new Position(maxWidth / 2, 0));
        this.blocks[2] = new Block(blockSize);
        this.blocks[2].setPos(new Position(maxWidth / 2 - 1, 1));
        this.blocks[3] = new Block(blockSize);
        this.blocks[3].setPos(new Position(maxWidth / 2, 1));

        for (Block block : blocks) {
            this.getChildren().add(block);
        }
    }
}
