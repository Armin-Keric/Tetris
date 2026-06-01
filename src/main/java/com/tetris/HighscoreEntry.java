package com.tetris;

public class HighscoreEntry implements Comparable<HighscoreEntry> {
    private final long time;
    private final int score;
    private final String name;

    public HighscoreEntry(long time, int score, String name) {
        this.time = time;
        this.score = score;
        this.name = name;
    }

    @Override
    public int compareTo(HighscoreEntry other) {
        int compare = Integer.compare(other.score, this.score);
        if (compare != 0) {
            return compare;
        }
        return Long.compare(other.time, this.time);
    }

    @Override
    public String toString() {
        return name + " - " + score + " (" + new java.util.Date(time) + ")";
    }

    public long getTime() {
        return time;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
}
