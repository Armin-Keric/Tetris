package com.tetris.shapes;


import javafx.scene.Group;

import javax.swing.text.GapContent;

public abstract class Shape extends Group {
    private int maxWidth = 0;
    private int maxHeight = 0;
    private int blocksize = 0;
    public Block[] blocks = new Block[4];

    public Shape(int blocksize, int maxWidth, int maxHeight) {
        this.blocksize = blocksize;
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
    }

    public Block[] getBlocks() {
        return this.blocks;
    }


    //A
    public void moveLeft(Shape shape) {

        if (shape instanceof I) {

        } else if (shape instanceof J) {

        } else if (shape instanceof L) {

        } else if (shape instanceof O) {

        } else if (shape instanceof S) {
            
        } else if (shape instanceof  T) {
            
        } else if (shape instanceof Z) {
            
        }

    }

    public void rotateLeft(Shape shape) {

    }

    //D
    public void moveRight(Shape shape) {

    }

    public void rotateRight(Shape shape) {

    }

    //This is the SoftDrop too...
    public void moveDown(Shape shape) {

    }

    //SPACE
    public void hardDrop(Shape shape) {

    }

}
