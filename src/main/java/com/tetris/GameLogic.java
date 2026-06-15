package com.tetris;

import com.tetris.shapes.Block;
import com.tetris.shapes.Position;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class GameLogic extends Pane {

    // Field size in cells (matches gamePane: 150x300 px at 15 px blocks -> 10 x 20).
    public static final int COLS = 10;
    public static final int ROWS = 20;

    // Locked blocks of already landed pieces. null = empty cell.
    private final Block[][] grid = new Block[ROWS][COLS];

    public boolean isInside(int x, int y) {
        return x >= 0 && x < COLS && y >= 0 && y < ROWS;
    }

    /** A cell the active piece may move into: inside the field and not occupied by a locked block. */
    public boolean isFree(int x, int y) {
        return isInside(x, y) && grid[y][x] == null;
    }

    public boolean isOccupied(int x, int y) {
        return isInside(x, y) && grid[y][x] != null;
    }

    /** Freeze a landed piece's blocks into the grid so future pieces collide with them.
     * Also Makes the block not movable and solid */
    public void lock(Block[] blocks) {
        for (Block block : blocks) {
            int x = block.getPos().getX();
            int y = block.getPos().getY();
            if (isInside(x, y)) {
                grid[y][x] = block;
            }
        }
    }

    /** Remove every full row, let the rows above drop down, and return how many were cleared. */
    public int clearRows() {
        int cleared = 0;
        int y = ROWS - 1;
        while (y >= 0) {
            if (isRowFull(y)) {
                removeRow(y);
                shiftDownAbove(y);
                cleared++;
                // Re-check the same row: new content has just dropped into it.
            } else {
                y--;
            }
        }
        return cleared;
    }

    private boolean isRowFull(int row) {
        for (int x = 0; x < COLS; x++) {
            if (grid[row][x] == null) {
                return false;
            }
        }
        return true;
    }

    private void removeRow(int row) {
        for (int x = 0; x < COLS; x++) {
            Block block = grid[row][x];
            if (block != null) {
                Parent parent = block.getParent();
                if (parent instanceof Group) {
                    ((Group) parent).getChildren().remove(block);
                } else if (parent instanceof Pane) {
                    ((Pane) parent).getChildren().remove(block);
                }
                grid[row][x] = null;
            }
        }
    }

    private void shiftDownAbove(int clearedRow) {
        for (int r = clearedRow; r > 0; --r) {
            for (int x = 0; x < COLS; x++) {
                Block block = grid[r - 1][x];
                grid[r][x] = block;
                if (block != null) {
                    block.setPos(new Position(x, r));
                }
            }
        }
        for (int x = 0; x < COLS; x++) {
            grid[0][x] = null;
        }
    }
}
