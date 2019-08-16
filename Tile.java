package com.example.rubix;

import android.graphics.RectF;

class Tile {
    private RectF thisRectF;
    private int thisColor;

    private int tileNum;

    private float red = 255;
    private float green = 255;
    private float blue = 255;

    Tile(int tileNum) {
        this.thisRectF = new RectF(0,0,0,0);
        this.thisColor = 6;
        this.tileNum = tileNum;
    }

    void setThisColor(int color) {
        this.thisColor = color;
    }

    void setThisColor(float r, float g, float b) {
        red = r;
        green = g;
        blue = b;
    }

    int getThisColor() {
        return thisColor;
    }

    RectF getThisTile() {
        return thisRectF;
    }

    float getRed() {
        return this.red;
    }

    float getGreen() {
        return this.green;
    }

    float getBlue() {
        return this.blue;
    }

    int getTileNum() {
        return this.tileNum;
    }
}
