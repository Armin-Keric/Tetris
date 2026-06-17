package com.tetris;

/**
 * Tracks the score, level and cleared-line count of a running game.
 *
 * Scoring follows the classic NES rules: a single line clear is worth
 * 40 points, a double 100, a triple 300 and a Tetris (four lines) 1200.
 * That base value is multiplied by (level + 1), so the same clear is worth
 * more on higher levels. The level rises by one for every 10 cleared lines.
 */
public class ScoreManager {

    // Base points indexed by the number of lines cleared at once (0..4).
    private static final int[] LINE_POINTS = {0, 40, 100, 300, 1200};
    private static final int LINES_PER_LEVEL = 10;

    private int score = 0;
    private int level = 0;
    private int totalLines = 0;

    /** Apply the score for clearing {@code rows} lines with a single piece. */
    public void addClearedLines(int rows) {
        if (rows <= 0) {
            return;
        }
        int base = (rows < LINE_POINTS.length) ? LINE_POINTS[rows] : LINE_POINTS[LINE_POINTS.length - 1];
        score += base * (level + 1);
        totalLines += rows;
        level = totalLines / LINES_PER_LEVEL;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public int getTotalLines() {
        return totalLines;
    }
}
