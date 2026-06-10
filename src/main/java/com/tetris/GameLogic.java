package com.tetris;

import com.tetris.shapes.Block;
import com.tetris.shapes.Position;
import javafx.scene.layout.Pane;

public class GameLogic extends Pane {

    private static final int COLS = 16;
    private static final int ROWS = 23;
    protected Block[][] blockOnCoordinate = new Block[ROWS - 1][COLS - 1];

    public void clearRows() {
        for (int x = ROWS - 1; x >= 0; --x) {


            if (isRowFull(x)) {
                cleanRows(x);
                shiftRowsDown(x);
            }

        }
    }

    private boolean isRowFull(int row) {
        for (int c = 0; c < COLS; c++) {
            if (blockOnCoordinate[row][c] == null) {
                return false;
            }
        }
        return true;
    }

    private void cleanRows(int x) {
        for (int y = 0; y < 16; ++y) {

            if (blockOnCoordinate[x][y] != null) {

                Block block = blockOnCoordinate[x][y];
                //maybe here is an error? can't test it out rn
                //this is supposed to be the gamefield
                this.getChildren().remove(block);
                //do i need this?
                blockOnCoordinate[x][y] = null;
            }
        }
    }

    private void shiftRowsDown(int startRow) {
        for (int r = startRow; r > 0; --r) {
            for (int c = 0; c <= 16; ++c) {

                blockOnCoordinate[r][c] =  blockOnCoordinate[r - 1][c];

                if(blockOnCoordinate[r][c] != null){
                    blockOnCoordinate[r][c].setPos(new Position(r, c));
                }

            }
        }

        //upper row should be empty
        for (int c = 0; c < COLS; c++) {
            blockOnCoordinate[0][c] = null;
        }

    }
}
