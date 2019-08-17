package com.example.rubix;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
public class RubixEngine extends SurfaceView implements Runnable {

    private Thread rubixThread = null;

    private boolean mPlaying = true;

    private Canvas rubixCanvas;
    private Paint rubixPaint;
    private SurfaceHolder rubixHolder;

    private HUD rubixHUD;
    private RubiksCube rubiksCube;

    private long targetFPS = 30;
    private long nextFrameTime;

    private int currentColor = 0;

    private int scrambleTimes = 0;

    public RubixEngine(Context context, DisplayMetrics displayMetrics) {
        super(context);
        rubixHUD = new HUD(context, displayMetrics);
        rubiksCube = new RubiksCube(displayMetrics);
        rubiksCube.setFaceTiles();
        rubixHolder = getHolder();
        rubixPaint = new Paint();
    }

    @Override
    public void run() {
        while (mPlaying) {
            update(this.rubiksCube, this.rubixHUD);
            boolean mPaused = false;
            if (!mPaused && updateRequired()){
                draw();
            }
        }
    }

    public void draw() {
        if(rubixHolder.getSurface().isValid()) {
            rubixCanvas = rubixHolder.lockCanvas();
            rubixCanvas.drawColor(Color.argb(255, 255, 255, 255));
            rubixHUD.draw(rubixCanvas, rubixPaint);
            rubiksCube.draw(rubixCanvas, rubixPaint);
            rubixHolder.unlockCanvasAndPost(rubixCanvas);
        }
    }

    void update(RubiksCube rubiksCube, HUD rubixHUD) {
        rubixHUD.updateAllFilled(rubiksCube);
    }

    public boolean onTouchEvent(MotionEvent event) {
        RectF pointTouched = new RectF(event.getX(), event.getY(), event.getX(), event.getY());
        if (event.getAction() == (MotionEvent.ACTION_UP & MotionEvent.ACTION_MASK)) {
            //Start button hasn't been pressed.
            if (!rubixHUD.getStartPressed()) {
                //If touch is on left column.
                if (RectF.intersects(rubixHUD.getColorSelectRectF(), pointTouched)) {
                    Log.d("debug", "Color select touched");
                    //Selected a color.
                    if (rubixHUD.getSelectionTouched(event) >= 0 && rubixHUD.getSelectionTouched(event) < 6) {
                        currentColor = rubixHUD.getSelectionTouched(event);
                        Log.d("debug", "" + currentColor);
                    }
                    //Selected scramble.
                    else {
                        if(scrambleTimes != 6) {
                            rubiksCube.scramble(scrambleTimes);
                            incScrambleTimes();
                            rubiksCube.printDebuggingText();
                        } else {
                            resetScrambleTimes();
                            rubiksCube.scramble(scrambleTimes);
                            incScrambleTimes();
                        }
                    }
                }
                //Touched clear button.
                else if (RectF.intersects(rubixHUD.getClearRect(), pointTouched)) {
                    rubiksCube.clear();
                    rubixHUD.setAllFilled(false);
                    rubiksCube.setAllFilled(false);
                    resetScrambleTimes();
                }
                //Touched start button.
                else if (RectF.intersects(rubixHUD.getStartButton(), pointTouched)) {
                    rubixHUD.setStartPressed(true);
                    Log.d("debug", "start pressed: " + "" + rubixHUD.getStartPressed());
                }
                //Touch on rubix cube.
                else {
                    if (rubixHolder.getSurface().isValid()) {
                        rubixCanvas = rubixHolder.lockCanvas();
                        rubiksCube.updateTileColor(event, currentColor);
                        rubixHolder.unlockCanvasAndPost(rubixCanvas);
                    }
                }
            }

            //Start has been pressed.
            else {
                //Touched the color selection.
                if(RectF.intersects(rubixHUD.getColorSelectRectF(), pointTouched)) {
                    Log.d("debug", "Color select touched");
                    //Selected a color.
                    if (rubixHUD.getSelectionTouched(event) >= 0 && rubixHUD.getSelectionTouched(event) < 6) {
                        currentColor = rubixHUD.getSelectionTouched(event);
                        Log.d("debug", "" + currentColor);
                    }
                }

                //Touched clear button.
                else if (RectF.intersects(rubixHUD.getClearRect(), pointTouched)) {
                    rubiksCube.clear();
                    rubixHUD.setAllFilled(false);
                    rubixHUD.setStartPressed(false);
                    rubiksCube.setAllFilled(false);
                }
                //TOUCHING MOVEMENT BUTTONS
                //Touched top clockwise
                else if (RectF.intersects(rubixHUD.getTopC(), pointTouched)) {
                    rubiksCube.getFaces().get(currentColor).rotateTopC(currentColor);
                }
                //Touched top counter-clockwise
                else if (RectF.intersects(rubixHUD.getTopCC(), pointTouched)) {
                    rubiksCube.getFaces().get(currentColor).rotateTopCC(currentColor);
                }
                //Touched left rotate down
                else if (RectF.intersects(rubixHUD.getLeftRotateDown(), pointTouched)) {

                }
                //Touched left rotate up
                else if (RectF.intersects(rubixHUD.getLeftRotateUp(), pointTouched)) {

                }
                //Touched right rotate down.
                else if (RectF.intersects(rubixHUD.getRightRotateDown(), pointTouched)) {

                }
                //Touched right rotate up.
                else if (RectF.intersects(rubixHUD.getRightRotateUp(), pointTouched)) {

                }
                //Touched bottom clockwise.
                else if (RectF.intersects(rubixHUD.getBottomC(), pointTouched)) {
                    rubiksCube.getFaces().get(currentColor).rotateBottomC(currentColor);
                }
                //Touched bottom counter-clockwise.
                else if (RectF.intersects(rubixHUD.getBottomCC(), pointTouched)) {

                }
            }
        }
        return true;
    }

    public boolean updateRequired() {
        final long MILLIS_PER_SECOND = 1000;
        if(nextFrameTime <= System.currentTimeMillis()) {
            nextFrameTime = System.currentTimeMillis() + MILLIS_PER_SECOND/targetFPS;
            return true;
        }
        return false;
    }

    public void startThread() {
        mPlaying = true;
        rubixThread = new Thread(this);
        rubixThread.start();
    }

    public void stopThread() {
        mPlaying = false;
        try{
            rubixThread.join();
        } catch (InterruptedException e) {
            Log.d("Interrupted exception", "RubixEngine.stopThread()");
        }
    }

    public void incScrambleTimes() {
        scrambleTimes++;
    }

    public void resetScrambleTimes() {
        scrambleTimes = 0;
    }
}
