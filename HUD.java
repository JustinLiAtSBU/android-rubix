package com.example.rubix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

class HUD {

    private int screenHeight;
    private int screenWidth;

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

    private Bitmap topCBitmap;
    private Bitmap topCCBitmap;
    private Bitmap bottomCBitmap;
    private Bitmap bottomCCBitmap;
    private Bitmap rightUpBitmap;
    private Bitmap rightDownBitmap;
    private Bitmap leftUpBitmap;
    private Bitmap leftDownBitmap;

    HUD (Context context, DisplayMetrics displayMetrics) {
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        colorSelectWidth = screenHeight/7;
        faceWidth = screenHeight/3;
        whiteRect = new RectF(0, 5, colorSelectWidth, colorSelectWidth);
        redRect = new RectF(0, colorSelectWidth+5, colorSelectWidth, colorSelectWidth*2);
        greenRect = new RectF(0, (colorSelectWidth*2)+5, colorSelectWidth, colorSelectWidth*3);
        yellowRect =  new RectF(0, (colorSelectWidth*3)+5, colorSelectWidth, colorSelectWidth*4);
        blueRect = new RectF(0, (colorSelectWidth*4)+5, colorSelectWidth, colorSelectWidth*5);
        orangeRect = new RectF(0, (colorSelectWidth*5)+5, colorSelectWidth, colorSelectWidth*6);
        scrambleRect = new RectF(0, (colorSelectWidth*6)+5, colorSelectWidth, colorSelectWidth*7);
        clearRect = new RectF(colorSelectWidth+10, (colorSelectWidth*6)+5, colorSelectWidth*2+10, colorSelectWidth*7);
        startButton = new RectF(screenWidth-colorSelectWidth-10, screenHeight-colorSelectWidth-10,
                screenWidth, screenHeight);
        topC = new RectF(colorSelectWidth+(faceWidth*4)+80, colorSelectWidth+30,
                colorSelectWidth*2+(faceWidth*4)+80, colorSelectWidth*2+30);
        topCBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.top_rotate_c);
        topCC = new RectF(colorSelectWidth*2+(faceWidth*4)+90, colorSelectWidth+30,
                colorSelectWidth*3+(faceWidth*4)+90, colorSelectWidth*2+30);
        topCCBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.top_rotate_cc);
        leftRotateUp = new RectF(colorSelectWidth+(faceWidth*4)+80, colorSelectWidth*2 +40,
                colorSelectWidth*2+(faceWidth*4)+80, colorSelectWidth*3+40);
        leftUpBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.left_up);
        leftRotateDown = new RectF(colorSelectWidth+(faceWidth*4)+80, colorSelectWidth*3+50,
                colorSelectWidth*2+(faceWidth*4)+80, colorSelectWidth*4+50);
        leftDownBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.left_down);
        rightRotateUp = new RectF(colorSelectWidth*2+faceWidth*4+90, colorSelectWidth*2+40,
                colorSelectWidth*3+faceWidth*4+90, colorSelectWidth*3+40);
        rightUpBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.right_up);
        rightRotateDown = new RectF(colorSelectWidth*2+faceWidth*4+90, colorSelectWidth*3+50,
                colorSelectWidth*3+faceWidth*4+90, colorSelectWidth*4+50);
        rightDownBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.right_down);
        bottomC = new RectF(colorSelectWidth+faceWidth*4+80, colorSelectWidth*4+60,
                colorSelectWidth*2+faceWidth*4+80, colorSelectWidth*5+60);
        bottomCBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bottom_rotate_c);
        bottomCC = new RectF(colorSelectWidth*2+faceWidth*4+90, colorSelectWidth*4+60,
                colorSelectWidth*3+faceWidth*4+90,colorSelectWidth*5+60);
        bottomCCBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bottom_rotate_cc);

        rectFS[0] = whiteRect;
        rectFS[1] = redRect;
        rectFS[2] = greenRect;
        rectFS[3] = yellowRect;
        rectFS[4] = blueRect;
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
            //paint.setColor(Color.CYAN);
            //canvas.drawRect(startButton, paint);

            //Draw buttons.
            paint.setColor(Color.BLACK);
            canvas.drawRect(topC, paint);
            canvas.drawBitmap(topCBitmap, topC.left, topC.top, paint);
            canvas.drawRect(topCC, paint);
            canvas.drawBitmap(topCCBitmap, topCC.left, topCC.top, paint);
            canvas.drawRect(leftRotateDown, paint);
            canvas.drawBitmap(leftDownBitmap, leftRotateDown.left, leftRotateDown.top, paint);
            canvas.drawRect(leftRotateUp, paint);
            canvas.drawBitmap(leftUpBitmap, leftRotateUp.left, leftRotateUp.top, paint);
            canvas.drawRect(rightRotateDown, paint);
            canvas.drawBitmap(rightDownBitmap, rightRotateDown.left, rightRotateDown.top, paint);
            canvas.drawRect(rightRotateUp,paint);
            canvas.drawBitmap(rightUpBitmap, rightRotateUp.left, rightRotateUp.top, paint);
            canvas.drawRect(bottomC, paint);
            canvas.drawBitmap(bottomCBitmap, bottomC.left, bottomC.top, paint);
            canvas.drawRect(bottomCC, paint);
            canvas.drawBitmap(bottomCCBitmap, bottomCC.left, bottomCC.top, paint);
        }

    }

    RectF getStartButton() {
        return startButton;
    }

    int getSelectionTouched(MotionEvent event) {
        float xCoord = event.getX();
        float yCoord = event.getY();
        int index = 0;
        int rectFSMaxIndex = 8;
        for (int i = 0; i < rectFSMaxIndex; i++) {
            RectF current = rectFS[i];
            if (intersects(xCoord, yCoord, current)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private boolean intersects(float x, float y, RectF rectF) {
        return x >= rectF.left && x <= rectF.right && y >= rectF.top && y <= rectF.bottom;
    }

    boolean getAllFilled() {
        return this.allFilled;
    }

    void setAllFilled(boolean b) {
        allFilled = b;
    }

    boolean getStartPressed() {
        return this.startPressed;
    }

    void setStartPressed(boolean b) {
        startPressed = b;
    }

    RectF getClearRect() {
        return clearRect;
    }

    RectF getColorSelectRectF() {
        return colorSelectRectF;
    }
}
