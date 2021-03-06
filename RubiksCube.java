package com.example.rubix;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import java.util.ArrayList;
class RubiksCube {
    private Face greenFace;
    private Face whiteFace;
    private Face redFace;
    private Face blueFace;
    private Face yellowFace;
    private Face orangeFace;
    private ArrayList<Face> faces = new ArrayList<>();

    private int initDistFromLeft;
    private int faceHeight;
    private int tileHeight;

    private int currentWhite0 = 0;
    private int currentRed1 = 0;
    private int currentGreen2 = 0;
    private int currentBlue3 = 0;
    private int currentYellow4 = 0;
    private int currentOrange5 = 0;

    private SoundEngine mSE;

    private boolean allFilled = false;

    RubiksCube(Context context, DisplayMetrics displayMetrics) {
        mSE = new SoundEngine(context);

        initDistFromLeft = (displayMetrics.heightPixels)/7;

        greenFace = new Face(displayMetrics);
        whiteFace = new Face(displayMetrics);
        redFace = new Face(displayMetrics);
        blueFace = new Face(displayMetrics);
        yellowFace = new Face(displayMetrics);
        orangeFace = new Face(displayMetrics);

        whiteFace.getTiles().get(4).setThisColor(230, 230, 230);
        whiteFace.getTiles().get(4).setThisColor(0);
        redFace.getTiles().get(4).setThisColor(255, 0, 0);
        redFace.getTiles().get(4).setThisColor(1);
        greenFace.getTiles().get(4).setThisColor(0, 255, 0);
        greenFace.getTiles().get(4).setThisColor(2);
        blueFace.getTiles().get(4).setThisColor(0, 0, 255);
        blueFace.getTiles().get(4).setThisColor(4);
        yellowFace.getTiles().get(4).setThisColor(255, 255, 0);
        yellowFace.getTiles().get(4).setThisColor(3);
        orangeFace.getTiles().get(4).setThisColor(255, 165, 0);
        orangeFace.getTiles().get(4).setThisColor(5);

        faces.add(whiteFace); //0
        faces.add(redFace);   //1
        faces.add(greenFace); //2
        faces.add(yellowFace);  //3
        faces.add(blueFace);//4
        faces.add(orangeFace);//5

        faceHeight = greenFace.getThisHeight();
        tileHeight = greenFace.getBlockHeight();

        //                     parallel,   top,      bottom,    left,       right
        whiteFace.setNeighbors(yellowFace, blueFace, greenFace, orangeFace, redFace);
        redFace.setNeighbors(orangeFace, whiteFace, yellowFace, greenFace, blueFace);
        greenFace.setNeighbors(blueFace, whiteFace, yellowFace, orangeFace, redFace);
        blueFace.setNeighbors(greenFace, whiteFace, yellowFace, redFace, orangeFace);
        yellowFace.setNeighbors(whiteFace, greenFace, blueFace, orangeFace, redFace);
        orangeFace.setNeighbors(redFace, whiteFace, yellowFace, blueFace, greenFace);
    }

    void setFaceTiles() {
        orangeFace.setTileRow(faceHeight, initDistFromLeft+20, 0);
        orangeFace.setTileRow(faceHeight+tileHeight+3, initDistFromLeft+20, 3);
        orangeFace.setTileRow(faceHeight+tileHeight*2+6, initDistFromLeft+20, 6);
        whiteFace.setTileRow(0, initDistFromLeft+faceHeight+30, 0);
        whiteFace.setTileRow(tileHeight + 3, initDistFromLeft + faceHeight + 30, 3);
        whiteFace.setTileRow(tileHeight*2+6, initDistFromLeft + faceHeight + 30, 6);
        greenFace.setTileRow(faceHeight+3, initDistFromLeft +faceHeight+ 30, 0);
        greenFace.setTileRow(faceHeight+tileHeight+6, initDistFromLeft+faceHeight+30, 3);
        greenFace.setTileRow(faceHeight+tileHeight*2+9, initDistFromLeft+faceHeight+30, 6);
        //SWITCH TO YELLOW
        yellowFace.setTileRow(faceHeight*2+3,initDistFromLeft+faceHeight+30,0);
        yellowFace.setTileRow(faceHeight*2+tileHeight+6, initDistFromLeft+faceHeight+30, 3);
        yellowFace.setTileRow(faceHeight*2+tileHeight*2+9, initDistFromLeft+faceHeight+30, 6);
        redFace.setTileRow(faceHeight+3, initDistFromLeft+faceHeight*2+40,0);
        redFace.setTileRow(faceHeight+tileHeight+6, initDistFromLeft+faceHeight*2+40,3);
        redFace.setTileRow(faceHeight+tileHeight*2+9, initDistFromLeft+faceHeight*2+40,6);
        //SWITCH TO BLUE
        blueFace.setTileRow(faceHeight+3,initDistFromLeft+faceHeight*3+50,0);
        blueFace.setTileRow(faceHeight+tileHeight+6,initDistFromLeft+faceHeight*3+50,3);
        blueFace.setTileRow(faceHeight+tileHeight*2+9,initDistFromLeft+faceHeight*3+50,6);
        //SWITCH TO BLUE
        for (Face aFace : faces) {
            aFace.setTileBs();
        }
    }

    void draw(Canvas canvas, Paint paint) {
        for(Face aFace: faces) {
            for(int i = 0; i < 9; i++) {
                Tile currentTile = aFace.getTiles().get(i);
                Tile currentTileB = aFace.getTileBs().get(i);
                paint.setColor(Color.BLACK);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect(currentTile.getThisTile(), paint);
                paint.setStyle(Paint.Style.FILL);
                int red = (int)currentTile.getRed();
                int green = (int)currentTile.getGreen();
                int blue = (int)currentTile.getBlue();
                paint.setColor(Color.argb(255, red, green, blue));
                canvas.drawRect(currentTileB.getThisTile(), paint);
            }
        }
    }

    private void updateAllFilled() {
        if ((currentWhite0+currentOrange5+currentYellow4+currentBlue3+currentGreen2+currentRed1)==48)
            allFilled = true;
    }

    ArrayList<Face> getFaces() {
        return faces;
    }

    boolean getAllFilled() {
        return ((currentWhite0+currentOrange5+currentYellow4+currentBlue3+currentGreen2+currentRed1)==48);
    }

    void setAllFilled(boolean b) {
        allFilled = b;
    }

    void updateTileColor(MotionEvent event, int color) {
        boolean canSetColor = false;
        float r = 0;
        float g = 0;
        float b = 0;
        float x = event.getX();
        float y = event.getY();

        switch (color) {
            case 0:
                if (currentWhite0 != 8) {
                    canSetColor = true;
                }
                r = 230;
                g = 230;
                b = 230;
                break;
            case 1:
                if (currentRed1 != 8) {
                    canSetColor = true;
                }
                r = 255;
                break;
            case 2:
                if (currentGreen2 != 8) {
                    canSetColor = true;
                }
                g = 255;
                break;
            case 3:
                if (currentYellow4 != 8) {
                    canSetColor = true;
                }
                r = 255;
                g = 255;
                break;
            case 4:
                if (currentBlue3 != 8) {
                    canSetColor = true;
                }
                b = 255;
                break;
            case 5:
                if (currentOrange5 != 8) {
                    canSetColor = true;
                }
                r = 255;
                g = 165;
                break;
        }

        for(Face aFace : faces) {
            for (Tile aTile : aFace.getTiles()) {
                RectF rectF = aTile.getThisTile();
                if (RectF.intersects(rectF, new RectF(x, y, x, y))) {
                    if(aTile.getTileNum() != 4 && canSetColor) {
                        switch (color) {
                            case 0:
                                currentWhite0++;
                                break;
                            case 1:
                                currentRed1++;
                                break;
                            case 2:
                                currentGreen2++;
                                break;
                            case 3:
                                currentYellow4++;
                                break;
                            case 4:
                                currentBlue3++;
                                break;
                            case 5:
                                currentOrange5++;
                                break;
                        }
                        if(aTile.getThisColor() != 6) {
                            switch (aTile.getThisColor()) {
                                case 0:
                                    currentWhite0--;
                                    break;
                                case 1:
                                    currentRed1--;
                                    break;
                                case 2:
                                    currentGreen2--;
                                    break;
                                case 3:
                                    currentYellow4--;
                                    break;
                                case 4:
                                    currentBlue3--;
                                    break;
                                case 5:
                                    currentOrange5--;
                                    break;
                            }
                        }
                        aTile.setThisColor(r, g, b);
                        aTile.setThisColor(color);
                    }
                    break;
                }
            }
        }
        if (canSetColor)
            mSE.playTurn(9);
        else
            mSE.playTurn(10);
        updateAllFilled();
        printDebuggingText();
    }

    void clear() {
        currentWhite0 = 0;
        currentRed1 = 0;
        currentGreen2 = 0;
        currentBlue3 = 0;
        currentYellow4 = 0;
        currentOrange5 = 0;
        for (Face face : faces) {
            for (Tile tile : face.getTiles()) {
                if (tile.getTileNum() != 4) {
                    tile.setThisColor(255, 255, 255);
                    tile.setThisColor(6);
                }
            }
        }
    }

    void scramble(int scrambleTimes) {
        Face currentFace = faces.get(scrambleTimes);
        for (int i = 0; i < 9; i++) {
            if (i == 4)
                continue;
            Tile tile = currentFace.getTiles().get(i);
            boolean canSetColor = false;
            float r = 0;
            float g = 0;
            float b = 0;
            while (!canSetColor) {
                switch (scrambleTimes) {
                    case 0:
                        if (currentWhite0 != 8) {
                            canSetColor = true;
                            currentWhite0++;
                        }
                        r = 230;
                        g = 230;
                        b = 230;
                        break;
                    case 1:
                        if (currentRed1 != 8) {
                            canSetColor = true;
                            currentRed1++;
                        }
                        r = 255;
                        break;
                    case 2:
                        if (currentGreen2 != 8) {
                            canSetColor = true;
                            currentGreen2++;
                        }
                        g = 255;
                        break;
                    case 3:
                        if (currentYellow4 != 8) {
                            canSetColor = true;
                            currentYellow4++;
                        }
                        r = 255;
                        g = 255;
                        break;
                    case 4:
                        if (currentBlue3 != 8) {
                            canSetColor = true;
                            currentBlue3++;
                        }
                        b = 255;
                        break;
                    case 5:
                        if (currentOrange5 != 8) {
                            canSetColor = true;
                            currentOrange5++;
                        }
                        r = 255;
                        g = 165;
                        break;
                }
                tile.setThisColor(r, g, b);
                tile.setThisColor(scrambleTimes);
            }
        }
    }

    void printDebuggingText() {
        Log.d("debug", "Current white: " + "" + currentWhite0);
        Log.d("debug", "Current red: " + "" + currentRed1);
        Log.d("debug", "Current green: " + "" + currentGreen2);
        Log.d("debug", "Current blue: " + "" + currentBlue3);
        Log.d("debug", "Current yellow: " + "" + currentYellow4);
        Log.d("debug", "Current orange: " + "" + currentOrange5);
        Log.d("debug", "--------------------------------------");
    }
}
