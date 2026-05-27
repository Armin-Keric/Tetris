package com.tetris.shapes;


import javafx.scene.Group;
import javafx.scene.input.KeyEvent;

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
    //@ToDo dont forget to change branches!
    public void moveAD(Shape shape, KeyEvent keyEvent) {

        switch (shape) {
            case I i -> calcPos(shape, keyEvent);
            case J j -> calcPos(shape, keyEvent);
            case L l -> calcPos(shape, keyEvent);
            case O o -> calcPos(shape, keyEvent);
            case S s -> calcPos(shape, keyEvent);
            case T t -> calcPos(shape, keyEvent);
            case Z z -> calcPos(shape, keyEvent);
            default -> throw new IllegalStateException("Unexpected value: " + shape);
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

    public void calcPos(Shape shape, KeyEvent keyEvent) {
        Block[] blocks1 = shape.getBlocks();

        switch (keyEvent.getCode()) {
            case A:

                for (int i = 0; i < shape.getBlocks().length; ++i) {
                    int x = blocks1[i].getPos().getX() - 1;
                    int y = blocks1[i].getPos().getY();
                    if (x < 0 || blocks1[3].getPos().getX() == 0) {
                        return;
                    } else {
                        Position pos = new Position(x, y);
                        blocks1[i].setPos(pos);
                    }

                }

                break;

            case D:

                for (int i = 0; i < shape.getBlocks().length; ++i) {
                    int x = blocks1[i].getPos().getX() + 1;
                    int y = blocks1[i].getPos().getY();
                    if (x > 16 || blocks1[3].getPos().getX() + 1 == 17 || (blocks1[3].getPos().getX() + 2 == 17 && shape instanceof T)) {
                        return;
                    } else {
                        Position pos = new Position(x, y);
                        blocks1[i].setPos(pos);
                    }

                }

                break;
        }


    }

}
