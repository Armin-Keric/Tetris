package com.tetris.shapes;

import com.tetris.GameLogic;
import com.tetris.GameOverController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Shape extends Group {

    private static final int COLS = 16;
    private static final int ROWS = 23;

    private int blocksize = 0;
    public Block[] blocks = new Block[4];
    public GameLogic gameLogic;

    private int maxWidth;
    private int maxHeight;
    private boolean onFloor=false;

    public boolean isLanded = false;

    public Shape(int blocksize, int maxWidth, int maxHeight, GameLogic gameLogic) {
        this.blocksize = blocksize;
        this.gameLogic = gameLogic;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public Block[] getBlocks() {
        return this.blocks;
    }

    public void moveAD(Shape shape, KeyEvent keyEvent) throws IOException {
        calcPos(shape, keyEvent);
    }

    private boolean isValid(Position[] positions) {
        for (int i = 0; i < positions.length; ++i) {
            int x = positions[i].getX();
            int y = positions[i].getY();
            // Out of bounds or blocked by an already landed block.
            if (!gameLogic.isFree(x, y)) return false;
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
            if (blocks1[i].getPos().getY() >= maxHeight) return;
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

        // Land if any block would hit the floor or a previously landed block.
        for (int i = 0; i < blocks1.length; ++i) {
            int x = blocks1[i].getPos().getX();
            int y = blocks1[i].getPos().getY() + 1;
            if (!gameLogic.isFree(x, y)) {
                land(blocks1);
                return;
            }
        }

        for (int i = 0; i < blocks1.length; ++i) {
            int x = blocks1[i].getPos().getX();
            int y = blocks1[i].getPos().getY() + 1;
            blocks1[i].setPos(new Position(x, y));
        }
    }

    /** Freeze the piece into the grid and clear any completed rows. */
    private void land(Block[] blocks1) {
        isLanded = true;
        setOnFloor(true);
        gameLogic.lock(blocks1);
        gameLogic.clearRows();
    }
    public void hardDrop(Shape shape) {
        Block[] blocks1 = shape.getBlocks();

        // Find how far the whole piece can drop before any block hits something.
        int drop = 0;
        while (canDropBy(blocks1, drop + 1)) {
            drop++;
        }

        for (int i = 0; i < blocks1.length; ++i) {
            blocks1[i].setPos(new Position(
                    blocks1[i].getPos().getX(),
                    blocks1[i].getPos().getY() + drop));
        }

        land(blocks1);
    }

    private boolean canDropBy(Block[] blocks1, int delta) {
        for (int i = 0; i < blocks1.length; ++i) {
            int x = blocks1[i].getPos().getX();
            int y = blocks1[i].getPos().getY() + delta;
            if (!gameLogic.isFree(x, y)) return false;
        }
        return true;
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

    public boolean isOnFloor() {
        return onFloor;
    }

    public void setOnFloor(boolean onFloor) {
        this.onFloor = onFloor;
    }
}
