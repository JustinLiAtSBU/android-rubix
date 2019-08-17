package com.example.rubix;

import android.graphics.RectF;
import android.util.DisplayMetrics;

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
    private int[] topColorsB = new int[9];

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

    void rotateBottomC(int faceNumber) {
        getAdjacentFacesColors();
        if (faceNumber == 0) {

        }
        else if (faceNumber == 3) {

        }
        else {

        }
    }

    void rotateTopC(int faceNumber) {
        getAdjacentFacesColors();
        //white --> orange --> blue --> red
        if (faceNumber == 0) {
            int index = 0;
            int rightIndex = 2; //redface
            int parallelIndex = 6; //blueface
            int leftIndex = 6; //orangeface
            while (index < 3) {
                Tile whiteTile = this.tiles.get(index);
                int whiteColor = whiteTile.getThisColor();
                Tile redTile = right.tiles.get(rightIndex);
                int redColor = redTile.getThisColor();
                Tile blueTile = parallel.tiles.get(parallelIndex);
                int blueColor = blueTile.getThisColor();
                Tile orangeTile = left.tiles.get(leftIndex);
                int orangeColor = orangeTile.getThisColor();
                whiteTile.setThisColor(redColor);
                whiteTile.setThisColor(rightColors[rightIndex][0], rightColors[rightIndex][1], rightColors[rightIndex][2]);
                orangeTile.setThisColor(whiteColor);
                orangeTile.setThisColor(thisColors[index][0], thisColors[index][1], thisColors[index][2]);
                blueTile.setThisColor(orangeColor);
                blueTile.setThisColor(leftColors[leftIndex][0], leftColors[leftIndex][1], leftColors[leftIndex][2]);
                redTile.setThisColor(blueColor);
                redTile.setThisColor(parallelColors[parallelIndex][0], parallelColors[parallelIndex][1], parallelColors[parallelIndex][2]);
                rightIndex += 3;
                parallelIndex++;
                leftIndex -= 3;
                index++;
            }
        }
        // right --> this --> left --> parallel
        else if(faceNumber == 3) {
            int index = 0;
            int rightIndex = 0;
            int leftIndex = 2; //blueface
            int parallelIndex = 6; //orangeface
            while (index < 3) {
                Tile thisTile = this.tiles.get(index);
                int thisColor = thisTile.getThisColor();
                Tile rightTile = right.tiles.get(rightIndex);
                int rightColor = rightTile.getThisColor();
                Tile leftTile = left.tiles.get(leftIndex);
                int leftColor = leftTile.getThisColor();
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                int parallelColor = parallelTile.getThisColor();
                thisTile.setThisColor(rightColor);
                thisTile.setThisColor(rightColors[rightIndex][0], rightColors[rightIndex][1], rightColors[rightIndex][2]);
                leftTile.setThisColor(thisColor);
                leftTile.setThisColor(thisColors[index][0], thisColors[index][1], thisColors[index][2]);
                parallelTile.setThisColor(leftColor);
                parallelTile.setThisColor(leftColors[leftIndex][0], leftColors[leftIndex][1], leftColors[leftIndex][2]);
                rightTile.setThisColor(parallelColor);
                rightTile.setThisColor(parallelColors[parallelIndex][0], parallelColors[parallelIndex][1], parallelColors[parallelIndex][2]);
                rightIndex += 3;
                leftIndex += 3;
                parallelIndex++;
                index++;
            }
        }
        else {
            //Rotation of non-top face colors.
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
        faceRotationC(neighbors.get(1));
    }
    //white --> orange --> blue --> red

    void rotateTopCC(int faceNumber) {
        getAdjacentFacesColors();
        //<-- white <-- red <-- blue <-- orange
        if (faceNumber == 0) {
            int index = 0;
            int rightIndex = 2; //redface
            int parallelIndex = 6; //blueface
            int leftIndex = 6; //orangeface
            while (index < 3) {
                Tile whiteTile = this.tiles.get(index);
                int whiteColor = whiteTile.getThisColor();
                Tile redTile = right.tiles.get(rightIndex);
                int redColor = redTile.getThisColor();
                Tile blueTile = parallel.tiles.get(parallelIndex);
                int blueColor = blueTile.getThisColor();
                Tile orangeTile = left.tiles.get(leftIndex);
                int orangeColor = orangeTile.getThisColor();
                whiteTile.setThisColor(orangeColor);
                whiteTile.setThisColor(leftColors[leftIndex][0], leftColors[leftIndex][1], leftColors[leftIndex][2]);
                orangeTile.setThisColor(whiteColor);
                orangeTile.setThisColor(thisColors[index][0], thisColors[index][1], thisColors[index][2]);
                blueTile.setThisColor(redColor);
                blueTile.setThisColor(rightColors[rightIndex][0], rightColors[rightIndex][1], rightColors[rightIndex][2]);
                redTile.setThisColor(blueColor);
                redTile.setThisColor(parallelColors[parallelIndex][0], parallelColors[parallelIndex][1], parallelColors[parallelIndex][2]);
                rightIndex += 3;
                parallelIndex++;
                leftIndex -= 3;
                index++;
            }
        }
        // right <-- this <-- left <-- parallel
        else if(faceNumber == 3) {
            int index = 0;
            int rightIndex = 0;
            int leftIndex = 2; //blueface
            int parallelIndex = 6; //orangeface
            while (index < 3) {
                Tile thisTile = this.tiles.get(index);
                int thisColor = thisTile.getThisColor();
                Tile rightTile = right.tiles.get(rightIndex);
                int rightColor = rightTile.getThisColor();
                Tile leftTile = left.tiles.get(leftIndex);
                int leftColor = leftTile.getThisColor();
                Tile parallelTile = parallel.tiles.get(parallelIndex);
                int parallelColor = parallelTile.getThisColor();
                thisTile.setThisColor(leftColor);
                thisTile.setThisColor(leftColors[leftIndex][0], leftColors[leftIndex][1], leftColors[leftIndex][2]);
                leftTile.setThisColor(parallelColor);
                leftTile.setThisColor(parallelColors[parallelIndex][0], parallelColors[parallelIndex][1], parallelColors[parallelIndex][2]);
                parallelTile.setThisColor(rightColor);
                parallelTile.setThisColor(rightColors[rightIndex][0], rightColors[rightIndex][1], rightColors[rightIndex][2]);
                rightTile.setThisColor(thisColor);
                rightTile.setThisColor(thisColors[index][0], thisColors[index][1], thisColors[index][2]);
                rightIndex += 3;
                leftIndex += 3;
                parallelIndex++;
                index++;
            }
        }
        else {
            //Rotation of non-top face colors.
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
        faceRotationCC(neighbors.get(1));
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

    private void faceRotationCC(Face face) {
        for (int i = 0; i < 9; i++) {
            Tile topTile = face.getTiles().get(i);
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
        }
    }

    private void faceRotationC(Face face) {
        for (int i = 0; i < 9; i++) {
            Tile topTile = face.getTiles().get(i);
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
    }
}
