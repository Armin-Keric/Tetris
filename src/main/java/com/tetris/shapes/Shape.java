package com.tetris.shapes;

import javafx.scene.Group;
import javafx.scene.input.KeyEvent;

public abstract class Shape extends Group {

    private static final int COLS = 16;
    private static final int ROWS = 23;

    private int blocksize = 0;
    public Block[] blocks = new Block[4];

    public Shape(int blocksize, int maxWidth, int maxHeight) {
        this.blocksize = blocksize;

    }

    public Block[] getBlocks() {
        return this.blocks;
    }

    public void moveAD(Shape shape, KeyEvent keyEvent) {
        calcPos(shape, keyEvent);
    }

    private boolean isValid(Position[] positions) {
        for (int i = 0; i < positions.length; ++i) {
            int x = positions[i].getX();
            int y = positions[i].getY();
            if (x < 0 || x >= COLS || y < 0 || y >= ROWS) return false;
        }
        return true;
    }

    private void apply(Block[] blocks1, Position[] positions) {
        if (!isValid(positions)) return;
        for (int i = 0; i < blocks1.length; ++i) {
            blocks1[i].setPos(positions[i]);
        }
    }

    public void rotateRight(Shape shape) {
        Block[] blocks1 = shape.getBlocks();
        if (shape instanceof O) return;


        for (int i = 0; i < blocks1.length; ++i) {
            if (blocks1[i].getPos().getY() >= ROWS) return;
        }

        int zentrumX = blocks1[1].getPos().getX();
        int zentrumY = blocks1[1].getPos().getY();

        Position[] positions = new Position[4];
        for (int i = 0; i < blocks1.length; ++i) {
            int relX = blocks1[i].getPos().getX() - zentrumX;
            int relY = blocks1[i].getPos().getY() - zentrumY;
            positions[i] = new Position(-relY + zentrumX, relX + zentrumY);
        }
        apply(blocks1, positions);
    }

    public void moveDown(Shape shape) {
        Block[] blocks1 = shape.getBlocks();

        for (int i = 0; i < blocks1.length; ++i) {
            if (blocks1[i].getPos().getY() + 1 > ROWS) return;
        }

        for (int i = 0; i < blocks1.length; ++i) {
            int x = blocks1[i].getPos().getX();
            int y = blocks1[i].getPos().getY() + 1;
            blocks1[i].setPos(new Position(x, y));
        }
    }

    public void hardDrop(Shape shape) {
        Block[] blocks1 = shape.getBlocks();
        int drop = Integer.MAX_VALUE;
        for (int i = 0; i < blocks1.length; ++i) {
            int space = ROWS - blocks1[i].getPos().getY();
            if (space < drop) drop = space;
        }

        for (int i = 0; i < blocks1.length; ++i) {
            blocks1[i].setPos(new Position(
                    blocks1[i].getPos().getX(),
                    blocks1[i].getPos().getY() + drop));
        }
    }

    public void calcPos(Shape shape, KeyEvent keyEvent) {
        Block[] blocks1 = shape.getBlocks();
        switch (keyEvent.getCode()) {
            case A: {
                Position[] p = new Position[4];
                for (int i = 0; i < blocks1.length; ++i)
                    p[i] = new Position(blocks1[i].getPos().getX() - 1, blocks1[i].getPos().getY());
                apply(blocks1, p);
                break;
            }
            case D: {
                Position[] p = new Position[4];
                for (int i = 0; i < blocks1.length; ++i)
                    p[i] = new Position(blocks1[i].getPos().getX() + 1, blocks1[i].getPos().getY());
                apply(blocks1, p);
                break;
            }
            case E:
                rotateRight(shape);
                break;
            case S:
                moveDown(shape);
                break;
            case SPACE:
                hardDrop(shape);
                break;
        }
    }
}
