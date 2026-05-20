package com.tetris.shapes;


import javafx.scene.Group;

import javax.swing.text.GapContent;

public abstract class Shape extends Group {
    private int maxWidth=0;
    private int maxHeight=0;
    private int blocksize=0;
    public Block[] blocks=new  Block[4];
    public Shape(int blocksize, int maxWidth, int maxHeight){
        this.blocksize=blocksize;
        this.maxHeight=maxHeight;
        this.maxWidth=maxWidth;
    }
    public Block[] getBlocks(){
        return this.blocks;
    }

}
