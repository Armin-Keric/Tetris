package com.tetris.shapes;

public class Position {
    private int x;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    public Position(int x, int y){
        this.x=x;
        this.y=y;
    }
}
