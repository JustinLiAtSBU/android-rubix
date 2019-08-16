package com.example.rubix;

import android.graphics.RectF;
import android.util.DisplayMetrics;

import java.util.ArrayList;

public class Face {
    private Face parallel;
    private Face top;
    private Face bottom;
    private Face right;
    private Face left;

    private int thisHeight; //Face height.

    private int blockHeight; //Individual height of each tile within a face.

    private Tile tile1 = new Tile(0);
    private Tile tile2 = new Tile(1);
    private Tile tile3 = new Tile(2);
    private Tile tile4 = new Tile(3);
    private Tile tile5 = new Tile(4);
    private Tile tile6 = new Tile(5);
    private Tile tile7 = new Tile(6);
    private Tile tile8 = new Tile(7);
    private Tile tile9 = new Tile(8);

    private Tile tile1b = new Tile(0);
    private Tile tile2b = new Tile(1);
    private Tile tile3b = new Tile(2);
    private Tile tile4b = new Tile(3);
    private Tile tile5b = new Tile(4);
    private Tile tile6b = new Tile(5);
    private Tile tile7b = new Tile(6);
    private Tile tile8b = new Tile(7);
    private Tile tile9b = new Tile(8);

    private ArrayList<Tile> tiles = new ArrayList<>();
    private ArrayList<Tile> tilebs = new ArrayList<>();

    Face(DisplayMetrics displayMetrics) {
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        tiles.add(tile4);
        tiles.add(tile5);
        tiles.add(tile6);
        tiles.add(tile7);
        tiles.add(tile8);
        tiles.add(tile9);
        tilebs.add(tile1b);
        tilebs.add(tile2b);
        tilebs.add(tile3b);
        tilebs.add(tile4b);
        tilebs.add(tile5b);
        tilebs.add(tile6b);
        tilebs.add(tile7b);
        tilebs.add(tile8b);
        tilebs.add(tile9b);
        int screenHeight = displayMetrics.heightPixels;
        thisHeight = screenHeight /3;
        blockHeight = thisHeight/3;
    }

    void setNeighbors(Face parallel, Face top, Face bottom, Face left, Face right) {
        this.parallel = parallel;
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    void setTileRow(int distFromTop, int distFromLeft, int start) {
        for(int i = start; i < start+3; i++) {
            if(i == 0 || i == 3 || i == 6) {
                tiles.get(i).getThisTile().set(new RectF(distFromLeft, distFromTop, distFromLeft + blockHeight,
                        distFromTop + blockHeight));
            }
            else {
                tiles.get(i).getThisTile().set(new RectF(tiles.get(i - 1).getThisTile().left+blockHeight+3, distFromTop,
                        tiles.get(i - 1).getThisTile().left+blockHeight*2+3, distFromTop+blockHeight));
            }
        }
    }

    void setTileBs() {
        for(int i = 0; i < 9; i++) {
            RectF tile = tiles.get(i).getThisTile();
            tilebs.get(i).getThisTile().set(tile.left+3, tile.top+3, tile.right-3, tile.bottom-3);
        }
    }

    int getBlockHeight() {
        return blockHeight;
    }

    int getThisHeight() {
        return thisHeight;
    }

    ArrayList<Tile> getTiles() {
        return tiles;
    }

    ArrayList<Tile> getTileBs() {
        return this.tilebs;
    }

    public Face getParallel() {
        return parallel;
    }

    public Face getTop() {
        return top;
    }

    public Face getBottom() {
        return bottom;
    }

    public Face  getRight() {
        return right;
    }

    public Face getLeft() {
        return left;
    }
}
