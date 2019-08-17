package com.example.rubix;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

public class HUD {

    int screenHeight;
    int screenWidth;

    private int colorSelectWidth;
    private int faceWidth;

    private RectF whiteRect;
    private RectF redRect;
    private RectF greenRect;
    private RectF blueRect;
    private RectF yellowRect;
    private RectF orangeRect;
    private RectF clearRect;
    private RectF scrambleRect;
    private RectF[] rectFS = new RectF[8];
    private RectF colorSelectRectF;
    private RectF startButton;
    private RectF topC;
    private RectF topCC;
    private RectF leftRotateUp;
    private RectF leftRotateDown;
    private RectF rightRotateUp;
    private RectF rightRotateDown;
    private RectF bottomC;
    private RectF bottomCC;

    private boolean allFilled = false;
    private boolean startPressed = false;

    private int rectFSMaxIndex = 8;

    HUD (Context context, DisplayMetrics displayMetrics) {
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        colorSelectWidth = screenHeight/7;
        faceWidth = screenHeight/3;
        whiteRect = new RectF(0, 5, colorSelectWidth, colorSelectWidth);
        redRect = new RectF(0, colorSelectWidth+5, colorSelectWidth, colorSelectWidth*2);
        greenRect = new RectF(0, (colorSelectWidth*2)+5, colorSelectWidth, colorSelectWidth*3);
        blueRect =  new RectF(0, (colorSelectWidth*3)+5, colorSelectWidth, colorSelectWidth*4);
        yellowRect = new RectF(0, (colorSelectWidth*4)+5, colorSelectWidth, colorSelectWidth*5);
        orangeRect = new RectF(0, (colorSelectWidth*5)+5, colorSelectWidth, colorSelectWidth*6);
        scrambleRect = new RectF(0, (colorSelectWidth*6)+5, colorSelectWidth, colorSelectWidth*7);
        clearRect = new RectF(colorSelectWidth+10, (colorSelectWidth*6)+5, colorSelectWidth*2+10, colorSelectWidth*7);
        startButton = new RectF(screenWidth-colorSelectWidth-10, screenHeight-colorSelectWidth-10,
                screenWidth, screenHeight);
        topC = new RectF(colorSelectWidth+(faceWidth*4)+80, colorSelectWidth+30,
                colorSelectWidth*2+(faceWidth*4)+80, colorSelectWidth*2+30);
        topCC = new RectF(colorSelectWidth*2+(faceWidth*4)+90, colorSelectWidth+30,
                colorSelectWidth*3+(faceWidth*4)+90, colorSelectWidth*2+30);
        leftRotateUp = new RectF(colorSelectWidth+(faceWidth*4)+80, colorSelectWidth*2 +40,
                colorSelectWidth*2+(faceWidth*4)+80, colorSelectWidth*3+40);
        leftRotateDown = new RectF(colorSelectWidth+(faceWidth*4)+80, colorSelectWidth*3+50,
                colorSelectWidth*2+(faceWidth*4)+80, colorSelectWidth*4+50);
        rightRotateUp = new RectF(colorSelectWidth*2+faceWidth*4+90, colorSelectWidth*2+40,
                colorSelectWidth*3+faceWidth*4+90, colorSelectWidth*3+40);
        rightRotateDown = new RectF(colorSelectWidth*2+faceWidth*4+90, colorSelectWidth*3+50,
                colorSelectWidth*3+faceWidth*4+90, colorSelectWidth*4+50);
        bottomC = new RectF(colorSelectWidth+faceWidth*4+80, colorSelectWidth*4+60,
                colorSelectWidth*2+faceWidth*4+80, colorSelectWidth*5+60);
        bottomCC = new RectF(colorSelectWidth*2+faceWidth*4+90, colorSelectWidth*4+60,
                colorSelectWidth*3+faceWidth*4+90,colorSelectWidth*5+60);

        rectFS[0] = whiteRect;
        rectFS[1] = redRect;
        rectFS[2] = greenRect;
        rectFS[3] = blueRect;
        rectFS[4] = yellowRect;
        rectFS[5] = orangeRect;
        rectFS[6] = scrambleRect;
        rectFS[7] = clearRect;
        colorSelectRectF = new RectF(0, 0, colorSelectWidth, colorSelectWidth * 7);
    }

    RectF getTopC() {
        return topC;
    }

    RectF getTopCC() {
        return topCC;
    }

    RectF getLeftRotateUp() {
        return leftRotateUp;
    }

    RectF getLeftRotateDown() {
        return leftRotateDown;
    }

    RectF getRightRotateUp() {
        return rightRotateUp;
    }

    RectF getRightRotateDown() {
        return rightRotateDown;
    }

    RectF getBottomC() {
        return bottomC;
    }

    RectF getBottomCC() {
        return bottomCC;
    }

    void updateAllFilled(RubiksCube rubiksCube) {
        if (rubiksCube.getAllFilled())
            this.allFilled = true;
    }

    void draw(Canvas canvas, Paint paint) {
        canvas.drawColor(Color.argb(255, 255, 255, 255));
        if(!this.allFilled){
            //Drawing white.
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.argb(255, 0, 0, 0));
            canvas.drawRect(whiteRect, paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.argb(255, 255, 255, 255));
            canvas.drawRect(colorSelectWidth+7, 7, (colorSelectWidth*2)-7, colorSelectWidth-7, paint);
            //Drawing red.
            paint.setColor(Color.argb(255, 255, 0, 0));
            canvas.drawRect(redRect, paint);
            //Drawing green.
            paint.setColor(Color.argb(255, 0, 255, 0));
            canvas.drawRect(greenRect, paint);
            //Drawing blue.
            paint.setColor(Color.argb(255, 0, 0, 255));
            canvas.drawRect(blueRect, paint);
            //Drawing yellow.
            paint.setColor(Color.argb(255, 255, 255, 0));
            canvas.drawRect(yellowRect, paint);
            //Drawing orange.
            paint.setColor(Color.argb(255, 255, 165, 0));
            canvas.drawRect(orangeRect, paint);
            //Drawing scramble.
            paint.setColor(Color.BLACK);
            canvas.drawRect(scrambleRect, paint);
            //Drawing clear
            paint.setColor(Color.MAGENTA);
            canvas.drawRect(clearRect, paint);
        }
        //All filled but start button hasn't been pressed.
        else if (allFilled && !startPressed){
            //Drawing white.
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.argb(255, 0, 0, 0));
            canvas.drawRect(whiteRect, paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.argb(255, 255, 255, 255));
            canvas.drawRect(colorSelectWidth+7, 7, (colorSelectWidth*2)-7, colorSelectWidth-7, paint);
            //Drawing red.
            paint.setColor(Color.argb(255, 255, 0, 0));
            canvas.drawRect(redRect, paint);
            //Drawing green.
            paint.setColor(Color.argb(255, 0, 255, 0));
            canvas.drawRect(greenRect, paint);
            //Drawing blue.
            paint.setColor(Color.argb(255, 0, 0, 255));
            canvas.drawRect(blueRect, paint);
            //Drawing yellow.
            paint.setColor(Color.argb(255, 255, 255, 0));
            canvas.drawRect(yellowRect, paint);
            //Drawing orange.
            paint.setColor(Color.argb(255, 255, 165, 0));
            canvas.drawRect(orangeRect, paint);
            //Drawing scramble.
            paint.setColor(Color.BLACK);
            canvas.drawRect(scrambleRect, paint);
            //Drawing clear
            paint.setColor(Color.MAGENTA);
            canvas.drawRect(clearRect, paint);
            //Draw start.
            paint.setColor(Color.CYAN);
            canvas.drawRect(startButton, paint);
        }
        else if (allFilled && startPressed) {
            //Drawing white.
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.argb(255, 0, 0, 0));
            canvas.drawRect(whiteRect, paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.argb(255, 255, 255, 255));
            canvas.drawRect(colorSelectWidth+7, 7, (colorSelectWidth*2)-7, colorSelectWidth-7, paint);
            //Drawing red.
            paint.setColor(Color.argb(255, 255, 0, 0));
            canvas.drawRect(redRect, paint);
            //Drawing green.
            paint.setColor(Color.argb(255, 0, 255, 0));
            canvas.drawRect(greenRect, paint);
            //Drawing blue.
            paint.setColor(Color.argb(255, 0, 0, 255));
            canvas.drawRect(blueRect, paint);
            //Drawing yellow.
            paint.setColor(Color.argb(255, 255, 255, 0));
            canvas.drawRect(yellowRect, paint);
            //Drawing orange.
            paint.setColor(Color.argb(255, 255, 165, 0));
            canvas.drawRect(orangeRect, paint);
            //Drawing scramble.
            paint.setColor(Color.BLACK);
            canvas.drawRect(scrambleRect, paint);
            //Drawing clear
            paint.setColor(Color.MAGENTA);
            canvas.drawRect(clearRect, paint);
            //Draw start.
            paint.setColor(Color.CYAN);
            canvas.drawRect(startButton, paint);
            //Draw buttons.
            paint.setColor(Color.BLACK);
            canvas.drawRect(topC, paint);
            canvas.drawRect(topCC, paint);
            canvas.drawRect(leftRotateDown, paint);
            canvas.drawRect(leftRotateUp, paint);
            canvas.drawRect(rightRotateDown, paint);
            canvas.drawRect(rightRotateUp,paint);
            canvas.drawRect(bottomC, paint);
            canvas.drawRect(bottomCC, paint);
        }

    }

    RectF getStartButton() {
        return startButton;
    }

    int getSelectionTouched(MotionEvent event) {
        float xCoord = event.getX();
        float yCoord = event.getY();
        int index = 0;
        for (int i = 0; i < rectFSMaxIndex; i++) {
            RectF current = rectFS[i];
            if (intersects(xCoord, yCoord, current)) {
                index = i;
                break;
            }
        }
        return index;
    }

    boolean intersects(float x, float y, RectF rectF) {
        return x >= rectF.left && x <= rectF.right && y >= rectF.top && y <= rectF.bottom;
    }

    public boolean getAllFilled() {
        return this.allFilled;
    }

    void setAllFilled(boolean b) {
        allFilled = b;
    }

    public boolean getStartPressed() {
        return this.startPressed;
    }

    void setStartPressed(boolean b) {
        startPressed = b;
    }

    public int getColorSelectWidth() {
        return colorSelectWidth;
    }

    RectF getClearRect() {
        return clearRect;
    }

    RectF getColorSelectRectF() {
        return colorSelectRectF;
    }
}
