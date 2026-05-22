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

    public void moveAD(Shape shape, KeyEvent keyEvent) {
        calcPos(shape, keyEvent);
    }

    public void rotateRight(Shape shape) {
        Block[] blocks1 = shape.getBlocks();

        if (shape instanceof O) return;

        int zentrumX = blocks1[1].getPos().getX();
        int zentrumY = blocks1[1].getPos().getY();

        Position[] positions = new Position[4];

        //rotation calculations
        for (int i = 0; i < blocks1.length; ++i) {
            int relX = blocks1[i].getPos().getX() - zentrumX;
            int relY = blocks1[i].getPos().getY() - zentrumY;

            int rotiertX = -relY;
            int rotiertY = relX;

            int finalX = rotiertX + zentrumX;
            int finalY = rotiertY + zentrumY;

            positions[i] = new Position(finalX, finalY);
        }

        for (int i = 0; i < positions.length; ++i) {
            int x = positions[i].getX();
            int y = positions[i].getY();

            if (x < 0 || x >= 16 || y < 0 || y >= 23) {
                return;
            }
        }

        for (int i = 0; i < blocks1.length; ++i) {
            blocks1[i].setPos(positions[i]);
        }
    }

    //This is the SoftDrop too...
    //@ToDo
    public void moveDown(Shape shape) {

    }

    //@ToDo
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

            case E:

                rotateRight(shape);
                break;

            //@ToDo
            //entweder extra methode oder hier aber extra methode schoener
            case S:
                //@ToDo

                break;

            case SPACE:

                break;
        }
    }

}
