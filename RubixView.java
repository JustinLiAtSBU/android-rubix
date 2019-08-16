package com.example.rubix;

import android.app.Activity;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

public class RubixView extends Activity {

    RubixEngine mRubixEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mRubixEngine = new RubixEngine(this, displayMetrics);
        setContentView(mRubixEngine);
    }

    @Override
    public void onResume() {
        super.onResume();
        mRubixEngine.startThread();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRubixEngine.stopThread();
    }
}
