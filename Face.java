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

    void rotateTopC() {
        //Rows = tile number.
        //Columns = red, green, blue.
        float[][] thisColors = new float[3][3];
        float[][] parallelColors = new float[3][3];
        float[][] rightColors = new float[3][3];
        float[][] leftColors = new float[3][3];
        float[][] topColors = new float[9][3];
        int[] topColorsB = new int[9];
        //Setting colors of non-top faces.
        for(int i = 0; i < 3; i++) {
            Tile parallelTile = parallel.getTiles().get(i);
            Tile rightTile = right.getTiles().get(i);
            Tile leftTile = left.getTiles().get(i);
            Tile thisTile = this.tiles.get(i);
            for(int j = 0; j < 3; j++) {
                if(j == 0) {
                    thisColors[i][j] = thisTile.getRed();
                    parallelColors[i][j] = parallelTile.getRed();
                    rightColors[i][j] = rightTile.getRed();
                    leftColors[i][j] = leftTile.getRed();
                }
                else if (j == 1) {
                    thisColors[i][j] = thisTile.getGreen();
                    parallelColors[i][j] = parallelTile.getGreen();
                    rightColors[i][j] = rightTile.getGreen();
                    leftColors[i][j] = leftTile.getGreen();
                }
                else {
                    thisColors[i][j] = thisTile.getBlue();
                    parallelColors[i][j] = parallelTile.getBlue();
                    rightColors[i][j] = rightTile.getBlue();
                    leftColors[i][j] = leftTile.getBlue();
                }
            }
        }
        //Rotation of non-top face colors.
        //this --> left --> parallel --> right.
        for(int i = 0; i < 3; i++) {
            Tile thisTile = this.tiles.get(i);
            Tile leftTile = left.getTiles().get(i);
            Tile parallelTile = parallel.getTiles().get(i);
            Tile rightTile = right.getTiles().get(i);
            float thisRed = thisColors[i][0];
            float thisGreen = thisColors[i][1];
            float thisBlue = thisColors[i][2];
            int thisColor = thisTile.getThisColor();
            float leftRed = leftColors[i][0];
            float leftGreen = leftColors[i][1];
            float leftBlue = leftColors[i][2];
            int leftColor = leftTile.getThisColor();
            float parallelRed = parallelColors[i][0];
            float parallelGreen = parallelColors[i][1];
            float parallelBlue = parallelColors[i][2];
            int parallelColor = parallelTile.getThisColor();
            float rightRed = rightColors[i][0];
            float rightGreen = rightColors[i][1];
            float rightBlue = rightColors[i][2];
            int rightColor = rightTile.getThisColor();
            leftTile.setThisColor(thisRed, thisGreen, thisBlue);
            leftTile.setThisColor(thisColor);
            parallelTile.setThisColor(leftRed, leftGreen, leftBlue);
            parallelTile.setThisColor(leftColor);
            rightTile.setThisColor(parallelRed, parallelGreen, parallelBlue);
            rightTile.setThisColor(parallelColor);
            thisTile.setThisColor(rightRed, rightGreen, rightBlue);
            thisTile.setThisColor(rightColor);
        }
        //Top tile rotation.
        //Setting top colors.
        for(int i = 0; i < 9; i++) {
            Tile topTile = top.getTiles().get(i);
            topColorsB[i] = topTile.getThisColor();
            for(int j = 0; j < 3; j++) {
                if(j ==0 ) {
                    topColors[i][j] = topTile.getRed();
                } else if (j ==1) {
                    topColors[i][j] = topTile.getGreen();
                } else {
                    topColors[i][j] = topTile.getBlue();
                }
            }
        }
        //Rotation of top face colors.
        for (int i = 0; i < 9; i++) {
            Tile topTile = top.getTiles().get(i);
            switch (i) {
                case 0:
                    topTile.setThisColor(topColorsB[6]);
                    topTile.setThisColor(topColors[6][0], topColors[6][1], topColors[6][2]);
                    break;
                case 1:
                    topTile.setThisColor(topColorsB[3]);
                    topTile.setThisColor(topColors[3][0], topColors[3][1], topColors[3][2]);
                    break;
                case 2:
                    topTile.setThisColor(topColorsB[0]);
                    topTile.setThisColor(topColors[0][0], topColors[0][1], topColors[0][2]);
                    break;
                case 3:
                    topTile.setThisColor(topColorsB[7]);
                    topTile.setThisColor(topColors[7][0], topColors[7][1], topColors[7][2]);
                    break;
                case 4:
                    topTile.setThisColor(topColorsB[4]);
                    topTile.setThisColor(topColors[4][0], topColors[4][1], topColors[4][2]);
                    break;
                case 5:
                    topTile.setThisColor(topColorsB[1]);
                    topTile.setThisColor(topColors[1][0], topColors[1][1], topColors[1][2]);
                    break;
                case 6:
                    topTile.setThisColor(topColorsB[8]);
                    topTile.setThisColor(topColors[8][0], topColors[8][1], topColors[8][2]);
                    break;
                case 7:
                    topTile.setThisColor(topColorsB[5]);
                    topTile.setThisColor(topColors[5][0], topColors[5][1], topColors[5][2]);
                    break;
                case 8:
                    topTile.setThisColor(topColorsB[2]);
                    topTile.setThisColor(topColors[2][0], topColors[2][1], topColors[2][2]);
                    break;
            }
        }
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
