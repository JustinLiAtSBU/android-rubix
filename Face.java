package com.example.rubix;

import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.ArrayList;

class Face {
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

    //Rows = tile number.
    //Columns = red, green, blue.
    private float[][] thisColors = new float[9][3];
    private float[][] parallelColors = new float[9][3];
    private float[][] rightColors = new float[9][3];
    private float[][] leftColors = new float[9][3];
    private float[][] topColors = new float[9][3];
    private float[][] bottomColors = new float[9][3];
    private int[] topColorsB = new int[9];
    private int[] bottomColorsB = new int[9];

    private ArrayList<Tile> tiles = new ArrayList<>();
    private ArrayList<Tile> tilebs = new ArrayList<>();
    private ArrayList<Face> neighbors = new ArrayList<>();

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

    void rotateLeftUp(int faceNumber) {
        getAdjacentFacesColors();
        //Green
        if (faceNumber == 2) {
            int yellowIndex = 8;
            //8 on yellow becomes 0 on the top.
            for (int i = 0; i <= 6; i += 3) {
                Tile thisTile = this.tiles.get(i);
                Tile topTile = top.tiles.get(i);
                Tile bottomTile = bottom.tiles.get(i);
                Tile parallelTile = parallel.tiles.get(yellowIndex);

                int thisTileColor = thisTile.getThisColor();
                float thisTileRed = thisTile.getRed();
                float thisTileGreen = thisTile.getGreen();
                float thisTileBlue = thisTile.getBlue();

                //this <-- bottom
                thisTile.setThisColor(bottomTile.getThisColor());
                setRGBColors(thisTile, bottomColors, i);
                //bottom <-- parallel
                bottomTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(bottomTile, parallelColors, yellowIndex);
                //parallel <-- top
                parallelTile.setThisColor(topTile.getThisColor());
                setRGBColors(parallelTile, topColors, i);
                //top <-- this
                topTile.setThisColor(thisTileColor);
                topTile.setThisColor(thisTileRed, thisTileGreen, thisTileBlue);
                yellowIndex -= 3;

                //ADD ROTATION OF LEFT FACE
            }
        } else if (faceNumber == 0) {
            int yellowIndex = 8;
            for (int i = 0; i <= 6; i += 3) {
                Tile thisTile = this.tiles.get(i);
                Tile topTile = top.tiles.get(yellowIndex);
                Tile bottomTile = bottom.tiles.get(i);
                Tile parallelTile = parallel.tiles.get(i);

                int thisTileColor = thisTile.getThisColor();
                float thisTileRed = thisTile.getRed();
                float thisTileGreen = thisTile.getGreen();
                float thisTileBlue = thisTile.getBlue();

                //this <-- bottom
                thisTile.setThisColor(bottomTile.getThisColor());
                setRGBColors(thisTile, bottomColors, i);
                //bottom <-- parallel
                bottomTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(bottomTile, parallelColors, i);
                //parallel <-- top
                parallelTile.setThisColor(topTile.getThisColor());
                setRGBColors(parallelTile, topColors, yellowIndex);
                //top <-- this
                topTile.setThisColor(thisTileColor);
                topTile.setThisColor(thisTileRed, thisTileGreen, thisTileBlue);
                yellowIndex -= 3;
            }
        } else if (faceNumber ==  3) {
            int yellowIndex = 8;
            for (int i = 0; i <= 6; i += 3) {
                Tile thisTile = this.tiles.get(i);
                Tile topTile = top.tiles.get(i);
                Tile bottomTile = bottom.tiles.get(yellowIndex);
                Tile parallelTile = parallel.tiles.get(i);

                int thisTileColor = thisTile.getThisColor();
                float thisTileRed = thisTile.getRed();
                float thisTileGreen = thisTile.getGreen();
                float thisTileBlue = thisTile.getBlue();

                //this <-- bottom
                thisTile.setThisColor(bottomTile.getThisColor());
                setRGBColors(thisTile, bottomColors, yellowIndex);
                //bottom <-- parallel
                bottomTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(bottomTile, parallelColors, i);
                //parallel <-- top
                parallelTile.setThisColor(topTile.getThisColor());
                setRGBColors(parallelTile, topColors, i);
                //top <-- this
                topTile.setThisColor(thisTileColor);
                topTile.setThisColor(thisTileRed, thisTileGreen, thisTileBlue);

                yellowIndex -= 3;
            }
        } else if (faceNumber == 1) {
            //save thisTile's colors.
            int[] thisPrevColors = new int[9];
            thisPrevColors[0] = tile1.getThisColor();
            thisPrevColors[3] = tile4.getThisColor();
            thisPrevColors[6] = tile7.getThisColor();

            //this <-- bottom
            for (int i = 0, b = 2; i <= 6; i += 3, b--) {
                Tile bottomTile = bottom.tiles.get(b);
                Tile thisTile = this.tiles.get(i);
                thisTile.setThisColor(bottomTile.getThisColor());
                setRGBColors(thisTile, bottomColors, b);
            }
            //bottom <-- parallel
            for (int k = 8, b = 2; k >= 2; k -= 3, b--) {
                Tile parallelTile = parallel.tiles.get(k);
                Tile bottomTile = bottom.tiles.get(b);
                bottomTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(bottomTile, parallelColors, k);
            }
            //parallel <-- top
            for (int j = 6, k = 8; j < 9; j++, k -= 3) {
                Tile topTile = top.tiles.get(j);
                Tile parallelTile = parallel.tiles.get(k);
                parallelTile.setThisColor(topTile.getThisColor());
                setRGBColors(parallelTile, topColors, j);
            }
            //top <-- this
            for (int i = 0, j = 6; j < 9; i += 3, j++) {
                Tile thisTile = this.tiles.get(i);
                Tile topTile = top.tiles.get(j);
                topTile.setThisColor(thisPrevColors[i]);
                setRGBColors(topTile, thisColors, i);
            }
        }
        //yellow
        else if (faceNumber == 4) {
            int[] thisPrevColors = new int[9];
            thisPrevColors[0] = this.tiles.get(0).getThisColor();
            thisPrevColors[3] = this.tiles.get(3).getThisColor();
            thisPrevColors[6] = this.tiles.get(6).getThisColor();

            //this <-- bottom
            for (int bottomIndex = 8, thisIndex = 0; bottomIndex >= 2; bottomIndex -= 3, thisIndex += 3) {
                Tile bottomTile = bottom.tiles.get(bottomIndex);
                Tile thisTile = this.tiles.get(thisIndex);
                thisTile.setThisColor(bottomTile.getThisColor());
                setRGBColors(thisTile, bottomColors, bottomIndex);
            }
            //bottom <-- parallel
            for (int parallelIndex = 8, bottomIndex = 8; parallelIndex >= 2; parallelIndex -= 3, bottomIndex -= 3) {
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                Tile bottomTile = bottom.tiles.get(bottomIndex);
                bottomTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(bottomTile, parallelColors, parallelIndex);
            }
            //parallel <-- top
            for (int parallelIndex = 8, topIndex = 8; topIndex >= 2; parallelIndex -= 3, topIndex -= 3) {
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                Tile topTile = top.tiles.get(topIndex);
                parallelTile.setThisColor(topTile.getThisColor());
                setRGBColors(parallelTile, topColors, topIndex);
            }
            //top <-- this
            for (int topIndex = 8, thisIndex = 0; thisIndex <= 6; topIndex -= 3, thisIndex += 3) {
                Tile topTile = top.tiles.get(topIndex);
                topTile.setThisColor(thisPrevColors[thisIndex]);
                setRGBColors(topTile, thisColors, thisIndex);
            }
        }
        //orange
        else if (faceNumber == 5) {
            int[] thisPrevColors = new int[9];
            thisPrevColors[0] = this.tiles.get(0).getThisColor();
            thisPrevColors[3] = this.tiles.get(3).getThisColor();
            thisPrevColors[6] = this.tiles.get(6).getThisColor();

            //this <-- bottom
            for (int bottomIndex = 6, thisIndex = 0; bottomIndex <= 8; bottomIndex++, thisIndex += 3) {
                Tile bottomTile = bottom.tiles.get(bottomIndex);
                Tile thisTile = this.tiles.get(thisIndex);
                thisTile.setThisColor(bottomTile.getThisColor());
                setRGBColors(thisTile, bottomColors, bottomIndex);
            }
            //bottom <-- parallel
            for (int parallelIndex = 8, bottomIndex = 6; parallelIndex >= 2; parallelIndex -= 3, bottomIndex++) {
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                Tile bottomTile = bottom.tiles.get(bottomIndex);
                bottomTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(bottomTile, parallelColors, parallelIndex);
            }
            //parallel <-- top
            for (int parallelIndex = 8, topIndex = 2; topIndex >= 0; parallelIndex -= 3, topIndex--) {
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                Tile topTile = top.tiles.get(topIndex);
                parallelTile.setThisColor(topTile.getThisColor());
                setRGBColors(parallelTile, topColors, topIndex);
            }
            //top <-- this
            for (int topIndex = 2, thisIndex = 0; thisIndex <= 6; topIndex--, thisIndex += 3) {
                Tile topTile = top.tiles.get(topIndex);
                Tile thisTile = this.tiles.get(thisIndex);
                topTile.setThisColor(thisPrevColors[thisIndex]);
                setRGBColors(topTile, thisColors, thisIndex);
            }
        }
    }

    void rotateLeftDown(int faceNumber) {
        getAdjacentFacesColors();
        //Green
        if (faceNumber == 2) {
            int yellowIndex = 8;
            //8 on yellow becomes 0 on the top.
            for (int i = 0; i <= 6; i += 3) {
                Tile thisTile = this.tiles.get(i);
                Tile topTile = top.tiles.get(i);
                Tile bottomTile = bottom.tiles.get(i);
                Tile parallelTile = parallel.tiles.get(yellowIndex);

                int thisTileColor = thisTile.getThisColor();
                float thisTileRed = thisTile.getRed();
                float thisTileGreen = thisTile.getGreen();
                float thisTileBlue = thisTile.getBlue();

                //top --> this
                thisTile.setThisColor(topTile.getThisColor());
                setRGBColors(thisTile, topColors, i);
                //parallel --> top
                topTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(topTile, parallelColors, yellowIndex);
                //bottom --> parallel
                parallelTile.setThisColor(bottomTile.getThisColor());
                setRGBColors(parallelTile, bottomColors, i);
                //this --> bottom
                bottomTile.setThisColor(thisTileColor);
                bottomTile.setThisColor(thisTileRed, thisTileGreen, thisTileBlue);

                yellowIndex -= 3;
            }
        }
        else if (faceNumber == 0) {
            int yellowIndex = 8;
            for (int i = 0; i <= 6; i += 3) {
                Tile thisTile = this.tiles.get(i);
                Tile topTile = top.tiles.get(yellowIndex);
                Tile bottomTile = bottom.tiles.get(i);
                Tile parallelTile = parallel.tiles.get(i);

                int thisTileColor = thisTile.getThisColor();
                float thisTileRed = thisTile.getRed();
                float thisTileGreen = thisTile.getGreen();
                float thisTileBlue = thisTile.getBlue();

                //top --> this
                thisTile.setThisColor(topTile.getThisColor());
                setRGBColors(thisTile, topColors, yellowIndex);
                //parallel --> top
                topTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(topTile, parallelColors, i);
                //bottom --> parallel
                parallelTile.setThisColor(bottomTile.getThisColor());
                setRGBColors(parallelTile, bottomColors, i);
                //this --> bottom
                bottomTile.setThisColor(thisTileColor);
                bottomTile.setThisColor(thisTileRed, thisTileGreen, thisTileBlue);

                yellowIndex -= 3;
            }
        }
        else if (faceNumber ==  3) {
            int yellowIndex = 8;
            for (int i = 0; i <= 6; i += 3) {
                Tile thisTile = this.tiles.get(i);
                Tile topTile = top.tiles.get(i);
                Tile bottomTile = bottom.tiles.get(yellowIndex);
                Tile parallelTile = parallel.tiles.get(i);

                int thisTileColor = thisTile.getThisColor();
                float thisTileRed = thisTile.getRed();
                float thisTileGreen = thisTile.getGreen();
                float thisTileBlue = thisTile.getBlue();

                //top --> this
                thisTile.setThisColor(topTile.getThisColor());
                setRGBColors(thisTile, topColors, i);
                //parallel --> top
                topTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(topTile, parallelColors, i);
                //bottom --> parallel
                parallelTile.setThisColor(bottomTile.getThisColor());
                setRGBColors(parallelTile, bottomColors, yellowIndex);
                //this --> bottom
                bottomTile.setThisColor(thisTileColor);
                bottomTile.setThisColor(thisTileRed, thisTileGreen, thisTileBlue);

                yellowIndex -= 3;
            }
        } else if (faceNumber == 1) {
            //save thisTile's colors.
            int[] thisPrevColors = new int[9];
            thisPrevColors[0] = tile1.getThisColor();
            thisPrevColors[3] = tile4.getThisColor();
            thisPrevColors[6] = tile7.getThisColor();

            //top --> this
            for (int i = 0, j = 6; j < 9; i += 3, j++) {
                Tile thisTile = this.tiles.get(i);
                Tile topTile = top.tiles.get(j);
                thisTile.setThisColor(topTile.getThisColor());
                setRGBColors(thisTile, topColors, j);
            }
            //parallel --> top
            for (int j = 6, k = 8; j < 9; j++, k -= 3) {
                Tile topTile = top.tiles.get(j);
                Tile parallelTile = parallel.tiles.get(k);
                topTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(topTile, parallelColors, k);
            }
            //bottom --> parallel
            for (int k = 8, b = 2; k >= 2; k -= 3, b--) {
                Tile parallelTile = parallel.tiles.get(k);
                Tile bottomTile = bottom.tiles.get(b);
                parallelTile.setThisColor(bottomTile.getThisColor());
                setRGBColors(parallelTile, bottomColors, b);
            }
            //this --> bottom
            for (int i = 0, b = 2; i <= 6; i += 3, b--) {
                Tile bottomTile = bottom.tiles.get(b);
                bottomTile.setThisColor(thisPrevColors[i]);
                setRGBColors(bottomTile, thisColors, i);
            }
        }
        //yellow
        else if (faceNumber == 4) {
            int[] thisPrevColors = new int[9];
            thisPrevColors[0] = this.tiles.get(0).getThisColor();
            thisPrevColors[3] = this.tiles.get(3).getThisColor();
            thisPrevColors[6] = this.tiles.get(6).getThisColor();

            //top --> this
            for (int topIndex = 8, thisIndex = 0; thisIndex <= 6; topIndex -= 3, thisIndex += 3) {
                Tile topTile = top.tiles.get(topIndex);
                Tile thisTile = this.tiles.get(thisIndex);
                thisTile.setThisColor(topTile.getThisColor());
                setRGBColors(thisTile, topColors, topIndex);
            }
            //parallel --> top
            for (int parallelIndex = 8, topIndex = 8; topIndex >= 2; parallelIndex -= 3, topIndex -= 3) {
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                Tile topTile = top.tiles.get(topIndex);
                topTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(topTile, parallelColors, parallelIndex);
            }
            //bottom --> parallel
            for (int parallelIndex = 8, bottomIndex = 8; parallelIndex >= 2; parallelIndex -= 3, bottomIndex -= 3) {
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                Tile bottomTile = bottom.tiles.get(bottomIndex);
                parallelTile.setThisColor(bottomTile.getThisColor());
                setRGBColors(parallelTile, bottomColors, bottomIndex);
            }
            //this --> bottom
            for (int bottomIndex = 8, thisIndex = 0; bottomIndex >= 2; bottomIndex -= 3, thisIndex += 3) {
                Tile bottomTile = bottom.tiles.get(bottomIndex);
                bottomTile.setThisColor(thisPrevColors[thisIndex]);
                setRGBColors(bottomTile, thisColors, thisIndex);
            }
        }
        //orange
        else if (faceNumber == 5) {
            int[] thisPrevColors = new int[9];
            thisPrevColors[0] = this.tiles.get(0).getThisColor();
            thisPrevColors[3] = this.tiles.get(3).getThisColor();
            thisPrevColors[6] = this.tiles.get(6).getThisColor();

            //top --> this
            for (int topIndex = 2, thisIndex = 0; thisIndex <= 6; topIndex--, thisIndex += 3) {
                Tile topTile = top.tiles.get(topIndex);
                Tile thisTile = this.tiles.get(thisIndex);
                thisTile.setThisColor(topTile.getThisColor());
                setRGBColors(thisTile, topColors, topIndex);
            }
            //parallel --> top
            for (int parallelIndex = 8, topIndex = 2; topIndex >= 0; parallelIndex -= 3, topIndex--) {
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                Tile topTile = top.tiles.get(topIndex);
                topTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(topTile, parallelColors, parallelIndex);
            }
            //bottom --> parallel
            for (int parallelIndex = 8, bottomIndex = 6; parallelIndex >= 2; parallelIndex -= 3, bottomIndex++) {
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                Tile bottomTile = bottom.tiles.get(bottomIndex);
                parallelTile.setThisColor(bottomTile.getThisColor());
                setRGBColors(parallelTile, bottomColors, bottomIndex);
            }
            //this --> bottom
            for (int bottomIndex = 6, thisIndex = 0; bottomIndex <= 8; bottomIndex++, thisIndex += 3) {
                Tile bottomTile = bottom.tiles.get(bottomIndex);
                bottomTile.setThisColor(thisPrevColors[thisIndex]);
                setRGBColors(bottomTile, thisColors, thisIndex);
            }
        }
        //ADD ROTATION
    }
    void rotateBottomC(int faceNumber) {
        getAdjacentFacesColors();
        int[] thisPrevColors = new int[9];
        thisPrevColors[6] = this.tile7.getThisColor();
        thisPrevColors[7] = this.tile8.getThisColor();
        thisPrevColors[8] = this.tile9.getThisColor();
        //white --> orange --> blue --> red
        //right: 0, 3, 6
        if (faceNumber == 0) {
            //right --> this
            for(int thisIndex = 6, rightIndex = 0; thisIndex < 9; thisIndex++, rightIndex += 3) {
                Tile thisTile = this.tiles.get(thisIndex);
                Tile rightTile = right.tiles.get(rightIndex);
                thisTile.setThisColor(rightTile.getThisColor());
                setRGBColors(thisTile, rightColors, rightIndex);
            }
            //parallel --> right
            for (int rightIndex = 0, parallelIndex = 2; rightIndex <= 6; rightIndex += 3, parallelIndex--) {
                Tile rightTile = right.tiles.get(rightIndex);
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                rightTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(rightTile, parallelColors, parallelIndex);
            }
            //left --> parallel
            for (int leftIndex = 8, parallelIndex = 0; leftIndex >= 2; leftIndex -= 3, parallelIndex++) {
                Tile leftTile = left.tiles.get(leftIndex);
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                parallelTile.setThisColor(leftTile.getThisColor());
                setRGBColors(parallelTile, leftColors, leftIndex);
            }
            //this -->left
            for (int leftIndex = 8, thisIndex = 6; leftIndex >= 2; leftIndex -= 3, thisIndex++) {
                Tile leftTile = left.tiles.get(leftIndex);
                leftTile.setThisColor(thisPrevColors[thisIndex]);
                setRGBColors(leftTile, thisColors, thisIndex);
            }
        }
        // right --> this --> left --> parallel
        else if(faceNumber == 3) {
            //right --> this
            for (int thisIndex = 6, rightIndex = 8; thisIndex < 9 ; thisIndex++, rightIndex -= 3) {
                Tile thisTile = this.tiles.get(thisIndex);
                Tile rightTile = right.getTiles().get(rightIndex);
                thisTile.setThisColor(rightTile.getThisColor());
                setRGBColors(thisTile, rightColors, rightIndex);
            }
            //parallel --> right
            for (int rightIndex = 8, parallelIndex = 2; rightIndex >= 2; rightIndex -=3, parallelIndex--) {
                Tile rightTile = right.tiles.get(rightIndex);
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                rightTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(rightTile, parallelColors, parallelIndex);
            }
            //left --> parallel
            for (int parallelIndex = 2, leftIndex = 0; parallelIndex >= 0; parallelIndex--, leftIndex += 3) {
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                Tile leftTile = left.tiles.get(leftIndex);
                parallelTile.setThisColor(leftTile.getThisColor());
                setRGBColors(parallelTile, leftColors, leftIndex);
            }
            //this --> left
            for (int leftIndex = 0, thisIndex = 6; thisIndex < 9; thisIndex++, leftIndex += 3) {
                Tile leftTile = left.tiles.get(leftIndex);
                leftTile.setThisColor(thisPrevColors[thisIndex]);
                setRGBColors(leftTile, thisColors, thisIndex);
            }
        }
        else {
            //this --> left --> parallel --> right.
            for (int i = 6; i < 9; i++) {
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
        }
        faceRotationC(neighbors.get(2), 2);
    }

    void rotateBottomCC(int faceNumber) {
        getAdjacentFacesColors();
        int[] thisPrevColors = new int[9];
        thisPrevColors[6] = this.tile7.getThisColor();
        thisPrevColors[7] = this.tile8.getThisColor();
        thisPrevColors[8] = this.tile9.getThisColor();
        //white --> orange --> blue --> red
        //right: 0, 3, 6
        if (faceNumber == 0) {
            //this <-- left
            for (int leftIndex = 8, thisIndex = 6; leftIndex >= 2; leftIndex -= 3, thisIndex++) {
                Tile leftTile = left.tiles.get(leftIndex);
                Tile thisTile = this.tiles.get(thisIndex);
                thisTile.setThisColor(thisPrevColors[thisIndex]);
                setRGBColors(thisTile, leftColors, leftIndex);
            }
            //left <-- parallel
            for (int leftIndex = 8, parallelIndex = 0; leftIndex >= 2; leftIndex -= 3, parallelIndex++) {
                Tile leftTile = left.tiles.get(leftIndex);
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                leftTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(leftTile, parallelColors, parallelIndex);
            }
            //parallel <-- right
            for (int rightIndex = 0, parallelIndex = 2; rightIndex <= 6; rightIndex += 3, parallelIndex--) {
                Tile rightTile = right.tiles.get(rightIndex);
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                parallelTile.setThisColor(rightTile.getThisColor());
                setRGBColors(parallelTile, rightColors, rightIndex);
            }
            //right <-- this
            for(int thisIndex = 6, rightIndex = 0; thisIndex < 9; thisIndex++, rightIndex += 3) {
                Tile thisTile = this.tiles.get(thisIndex);
                Tile rightTile = right.tiles.get(rightIndex);
                rightTile.setThisColor(thisPrevColors[thisIndex]);
                setRGBColors(rightTile, thisColors, thisIndex);
            }
        }
        // right --> this --> left --> parallel
        else if(faceNumber == 3) {
            //this <-- left
            for (int leftIndex = 0, thisIndex = 6; thisIndex < 9; thisIndex++, leftIndex += 3) {
                Tile leftTile = left.tiles.get(leftIndex);
                Tile thisTile = this.tiles.get(thisIndex);
                thisTile.setThisColor(leftTile.getThisColor());
                setRGBColors(thisTile, leftColors, leftIndex);
            }
            //left <-- parallel
            for (int parallelIndex = 2, leftIndex = 0; parallelIndex >= 0; parallelIndex--, leftIndex += 3) {
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                Tile leftTile = left.tiles.get(leftIndex);
                leftTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(leftTile, parallelColors, parallelIndex);
            }
            //parallel <-- right
            for (int rightIndex = 8, parallelIndex = 2; rightIndex >= 2; rightIndex -=3, parallelIndex--) {
                Tile rightTile = right.tiles.get(rightIndex);
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                parallelTile.setThisColor(rightTile.getThisColor());
                setRGBColors(parallelTile, rightColors, rightIndex);
            }
            //right <-- this
            for (int thisIndex = 6, rightIndex = 8; thisIndex < 9 ; thisIndex++, rightIndex -= 3) {
                Tile thisTile = this.tiles.get(thisIndex);
                Tile rightTile = right.getTiles().get(rightIndex);
                rightTile.setThisColor(thisPrevColors[thisIndex]);
                setRGBColors(rightTile, thisColors, thisIndex);
            }
        }
        else {
            //this <-- left <-- parallel <-- right.
            for (int i = 6; i < 9; i++) {
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
                leftTile.setThisColor(parallelRed, parallelGreen, parallelBlue);
                leftTile.setThisColor(parallelColor);
                parallelTile.setThisColor(rightRed, rightGreen, rightBlue);
                parallelTile.setThisColor(rightColor);
                rightTile.setThisColor(thisRed, thisGreen, thisBlue);
                rightTile.setThisColor(thisColor);
                thisTile.setThisColor(leftRed, leftGreen, leftBlue);
                thisTile.setThisColor(leftColor);
            }
        }
        faceRotationCC(neighbors.get(2), 2);
    }

    void rotateTopC(int faceNumber) {
        getAdjacentFacesColors();
        int[] thisPrevColors = new int[3];
        thisPrevColors[0] = this.tile1.getThisColor();
        thisPrevColors[1] = this.tile2.getThisColor();
        thisPrevColors[2] = this.tile3.getThisColor();
        //white --> orange --> blue --> red
        if (faceNumber == 0) {
            //right --> this
            for(int thisIndex = 0, rightIndex = 2; thisIndex < 3; thisIndex++, rightIndex += 3) {
                Tile thisTile = this.tiles.get(thisIndex);
                Tile rightTile = right.tiles.get(rightIndex);
                thisTile.setThisColor(rightTile.getThisColor());
                setRGBColors(thisTile, rightColors, rightIndex);
            }
            //parallel --> right
            for (int rightIndex = 2, parallelIndex = 8; rightIndex < 9; rightIndex += 3, parallelIndex--) {
                Tile rightTile = right.tiles.get(rightIndex);
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                rightTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(rightTile, parallelColors, parallelIndex);
            }
            //left --> parallel
            for (int leftIndex = 6, parallelIndex = 8; leftIndex >= 0; leftIndex -= 3, parallelIndex--) {
                Tile leftTile = left.tiles.get(leftIndex);
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                parallelTile.setThisColor(leftTile.getThisColor());
                setRGBColors(parallelTile, leftColors, leftIndex);
            }
            //this -->left
            for (int leftIndex = 6, thisIndex = 0; leftIndex >= 0; leftIndex -= 3, thisIndex++) {
                Tile leftTile = left.tiles.get(leftIndex);
                leftTile.setThisColor(thisPrevColors[thisIndex]);
                setRGBColors(leftTile, thisColors, thisIndex);
            }
        }
        // right --> this --> left --> parallel
        else if(faceNumber == 3) {
            //right --> this
            for (int thisIndex = 0, rightIndex = 6; thisIndex < 3; thisIndex++, rightIndex -= 3) {
                Tile thisTile = this.tiles.get(thisIndex);
                Tile rightTile = right.getTiles().get(rightIndex);
                thisTile.setThisColor(rightTile.getThisColor());
                setRGBColors(thisTile, rightColors, rightIndex);
            }
            //parallel --> right
            for (int rightIndex = 6, parallelIndex = 8; rightIndex >= 0; rightIndex -=3, parallelIndex--) {
                Tile rightTile = right.tiles.get(rightIndex);
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                rightTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(rightTile, parallelColors, parallelIndex);
            }
            //left --> parallel
            for (int parallelIndex = 8, leftIndex = 2; parallelIndex >= 6; parallelIndex--, leftIndex += 3) {
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                Tile leftTile = left.tiles.get(leftIndex);
                parallelTile.setThisColor(leftTile.getThisColor());
                setRGBColors(parallelTile, leftColors, leftIndex);
            }
            //this --> left
            for (int leftIndex = 2, thisIndex = 0; thisIndex < 3; thisIndex++, leftIndex += 3) {
                Tile leftTile = left.tiles.get(leftIndex);
                leftTile.setThisColor(thisPrevColors[thisIndex]);
                setRGBColors(leftTile, thisColors, thisIndex);
            }
        }
        else {
            //this --> left --> parallel --> right.
            for (int i = 0; i < 3; i++) {
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
        }
        faceRotationC(neighbors.get(1), 1);
    }

    void rotateTopCC(int faceNumber) {
        getAdjacentFacesColors();
        int[] thisPrevColors = new int[3];
        thisPrevColors[0] = this.tile1.getThisColor();
        thisPrevColors[1] = this.tile2.getThisColor();
        thisPrevColors[2] = this.tile3.getThisColor();
        if (faceNumber == 0) {
            //this <-- left
            for (int leftIndex = 6, thisIndex = 0; leftIndex >= 0; leftIndex -= 3, thisIndex++) {
                Tile leftTile = left.tiles.get(leftIndex);
                Tile thisTile = this.tiles.get(thisIndex);
                thisTile.setThisColor(leftTile.getThisColor());
                setRGBColors(thisTile, leftColors, leftIndex);
            }
            //left <-- parallel
            for (int leftIndex = 6, parallelIndex = 8; leftIndex >= 0; leftIndex -= 3, parallelIndex--) {
                Tile leftTile = left.tiles.get(leftIndex);
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                leftTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(leftTile, parallelColors, parallelIndex);
            }
            //parallel <-- right
            for (int rightIndex = 2, parallelIndex = 8; rightIndex < 9; rightIndex += 3, parallelIndex--) {
                Tile rightTile = right.tiles.get(rightIndex);
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                parallelTile.setThisColor(rightTile.getThisColor());
                setRGBColors(parallelTile, rightColors, rightIndex);
            }
            //right <-- this
            for(int thisIndex = 0, rightIndex = 2; thisIndex < 3; thisIndex++, rightIndex += 3) {
                Tile rightTile = right.tiles.get(rightIndex);
                rightTile.setThisColor(thisPrevColors[thisIndex]);
                setRGBColors(rightTile, thisColors, thisIndex);
            }
        }
        // right <-- this <-- left <-- parallel
        else if(faceNumber == 3) {
            //left --> this
            for (int leftIndex = 2, thisIndex = 0; thisIndex < 3; thisIndex++, leftIndex += 3) {
                Tile leftTile = left.tiles.get(leftIndex);
                Tile thisTile = this.tiles.get(thisIndex);
                thisTile.setThisColor(leftTile.getThisColor());
                setRGBColors(thisTile, leftColors, leftIndex);
            }
            //parallel --> left
            for (int parallelIndex = 8, leftIndex = 2; parallelIndex >= 6; parallelIndex--, leftIndex += 3) {
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                Tile leftTile = left.tiles.get(leftIndex);
                leftTile.setThisColor(parallelTile.getThisColor());
                setRGBColors(leftTile, parallelColors, parallelIndex);
            }
            //right --> parallel
            for (int rightIndex = 6, parallelIndex = 8; rightIndex >= 0; rightIndex -=3, parallelIndex--) {
                Tile rightTile = right.tiles.get(rightIndex);
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                parallelTile.setThisColor(rightTile.getThisColor());
                setRGBColors(parallelTile, rightColors, rightIndex);
            }
            //this --> right
            for (int thisIndex = 0, rightIndex = 6; thisIndex < 3; thisIndex++, rightIndex -=3) {
                Tile rightTile = right.getTiles().get(rightIndex);
                rightTile.setThisColor(thisPrevColors[thisIndex]);
                setRGBColors(rightTile, thisColors, thisIndex);
            }
        }
        else {
            //this <-- left <-- parallel <-- right.
            for (int i = 0; i < 3; i++) {
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
                leftTile.setThisColor(parallelRed, parallelGreen, parallelBlue);
                leftTile.setThisColor(parallelColor);
                parallelTile.setThisColor(rightRed, rightGreen, rightBlue);
                parallelTile.setThisColor(rightColor);
                rightTile.setThisColor(thisRed, thisGreen, thisBlue);
                rightTile.setThisColor(thisColor);
                thisTile.setThisColor(leftRed, leftGreen, leftBlue);
                thisTile.setThisColor(leftColor);
            }
        }
        faceRotationCC(neighbors.get(1), 1);
    }

    private void setRGBColors(Tile tileToBeSet, float[][] tileColorsToSetTo, int index) {
        tileToBeSet.setThisColor(tileColorsToSetTo[index][0], tileColorsToSetTo[index][1], tileColorsToSetTo[index][2]);
    }

    void setNeighbors(Face parallel, Face top, Face bottom, Face left, Face right) {
        this.parallel = parallel;
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        neighbors.add(parallel);
        neighbors.add(top);
        neighbors.add(bottom);
        neighbors.add(left);
        neighbors.add(right);
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

    private void faceRotationCC(Face face, int side) {
        for (int i = 0; i < 9; i++) {
            Tile topTile = face.getTiles().get(i);
            switch (side) {
                case 1:
                    switch (i) {
                        case 8:
                            topTile.setThisColor(topColorsB[6]);
                            topTile.setThisColor(topColors[6][0], topColors[6][1], topColors[6][2]);
                            break;
                        case 7:
                            topTile.setThisColor(topColorsB[3]);
                            topTile.setThisColor(topColors[3][0], topColors[3][1], topColors[3][2]);
                            break;
                        case 6:
                            topTile.setThisColor(topColorsB[0]);
                            topTile.setThisColor(topColors[0][0], topColors[0][1], topColors[0][2]);
                            break;
                        case 5:
                            topTile.setThisColor(topColorsB[7]);
                            topTile.setThisColor(topColors[7][0], topColors[7][1], topColors[7][2]);
                            break;
                        case 4:
                            topTile.setThisColor(topColorsB[4]);
                            topTile.setThisColor(topColors[4][0], topColors[4][1], topColors[4][2]);
                            break;
                        case 3:
                            topTile.setThisColor(topColorsB[1]);
                            topTile.setThisColor(topColors[1][0], topColors[1][1], topColors[1][2]);
                            break;
                        case 2:
                            topTile.setThisColor(topColorsB[8]);
                            topTile.setThisColor(topColors[8][0], topColors[8][1], topColors[8][2]);
                            break;
                        case 1:
                            topTile.setThisColor(topColorsB[5]);
                            topTile.setThisColor(topColors[5][0], topColors[5][1], topColors[5][2]);
                            break;
                        case 0:
                            topTile.setThisColor(topColorsB[2]);
                            topTile.setThisColor(topColors[2][0], topColors[2][1], topColors[2][2]);
                            break;
                    }
                    break;
                case 2:
                    switch (i) {
                        case 8:
                            topTile.setThisColor(bottomColorsB[6]);
                            topTile.setThisColor(bottomColors[6][0], bottomColors[6][1], bottomColors[6][2]);
                            break;
                        case 7:
                            topTile.setThisColor(bottomColorsB[3]);
                            topTile.setThisColor(bottomColors[3][0], bottomColors[3][1], bottomColors[3][2]);
                            break;
                        case 6:
                            topTile.setThisColor(bottomColorsB[0]);
                            topTile.setThisColor(bottomColors[0][0], bottomColors[0][1], bottomColors[0][2]);
                            break;
                        case 5:
                            topTile.setThisColor(bottomColorsB[7]);
                            topTile.setThisColor(bottomColors[7][0], bottomColors[7][1], bottomColors[7][2]);
                            break;
                        case 4:
                            topTile.setThisColor(bottomColorsB[4]);
                            topTile.setThisColor(bottomColors[4][0], bottomColors[4][1], bottomColors[4][2]);
                            break;
                        case 3:
                            topTile.setThisColor(bottomColorsB[1]);
                            topTile.setThisColor(bottomColors[1][0], bottomColors[1][1], bottomColors[1][2]);
                            break;
                        case 2:
                            topTile.setThisColor(bottomColorsB[8]);
                            topTile.setThisColor(bottomColors[8][0], bottomColors[8][1], bottomColors[8][2]);
                            break;
                        case 1:
                            topTile.setThisColor(bottomColorsB[5]);
                            topTile.setThisColor(bottomColors[5][0], bottomColors[5][1], bottomColors[5][2]);
                            break;
                        case 0:
                            topTile.setThisColor(bottomColorsB[2]);
                            topTile.setThisColor(bottomColors[2][0], bottomColors[2][1], bottomColors[2][2]);
                            break;
                    }

            }
        }
    }

    private void faceRotationC(Face face, int side) {
        for (int i = 0; i < 9; i++) {
            Tile topTile = face.getTiles().get(i);
            switch (side) {
                case 1:
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
                    break;
                case 2:
                    switch (i) {
                        case 0:
                            topTile.setThisColor(bottomColorsB[6]);
                            topTile.setThisColor(bottomColors[6][0], bottomColors[6][1], bottomColors[6][2]);
                            break;
                        case 1:
                            topTile.setThisColor(bottomColorsB[3]);
                            topTile.setThisColor(bottomColors[3][0], bottomColors[3][1], bottomColors[3][2]);
                            break;
                        case 2:
                            topTile.setThisColor(bottomColorsB[0]);
                            topTile.setThisColor(bottomColors[0][0], bottomColors[0][1], bottomColors[0][2]);
                            break;
                        case 3:
                            topTile.setThisColor(bottomColorsB[7]);
                            topTile.setThisColor(bottomColors[7][0], bottomColors[7][1], bottomColors[7][2]);
                            break;
                        case 4:
                            topTile.setThisColor(bottomColorsB[4]);
                            topTile.setThisColor(bottomColors[4][0], bottomColors[4][1], bottomColors[4][2]);
                            break;
                        case 5:
                            topTile.setThisColor(bottomColorsB[1]);
                            topTile.setThisColor(bottomColors[1][0], bottomColors[1][1], bottomColors[1][2]);
                            break;
                        case 6:
                            topTile.setThisColor(bottomColorsB[8]);
                            topTile.setThisColor(bottomColors[8][0], bottomColors[8][1], bottomColors[8][2]);
                            break;
                        case 7:
                            topTile.setThisColor(bottomColorsB[5]);
                            topTile.setThisColor(bottomColors[5][0], bottomColors[5][1], bottomColors[5][2]);
                            break;
                        case 8:
                            topTile.setThisColor(bottomColorsB[2]);
                            topTile.setThisColor(bottomColors[2][0], bottomColors[2][1], bottomColors[2][2]);
                            break;
                    }
                    break;
                case 3:
                    switch (i) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                    }
                    break;
            }

        }
    }

    //Setting colors of non-top faces.
    private void getAdjacentFacesColors() {
        for(int i = 0; i < 9; i++) {
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
        for(int i = 0; i < 9; i++) {
            Tile topTile = top.getTiles().get(i);
            Tile bottomTile = bottom.getTiles().get(i);
            topColorsB[i] = topTile.getThisColor();
            bottomColorsB[i] = bottomTile.getThisColor();
            for(int j = 0; j < 3; j++) {
                if(j ==0 ) {
                    topColors[i][j] = topTile.getRed();
                    bottomColors[i][j] = bottomTile.getRed();
                } else if (j ==1) {
                    topColors[i][j] = topTile.getGreen();
                    bottomColors[i][j] = bottomTile.getGreen();
                } else {
                    topColors[i][j] = topTile.getBlue();
                    bottomColors[i][j] = bottomTile.getBlue();
                }
            }
        }
    }
}
