package com.example.rubix;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.io.IOException;

class SoundEngine {

    private SoundPool mSP;
    private int turn1_ID = -1;
    private int turn2_ID = -1;
    private int turn3_ID = -1;
    private int turn4_ID = -1;
    private int turn5_ID = -1;
    private int turn6_ID = -1;
    private int turn7_ID = -1;
    private int selection_ID = -1;
    private int putColor_ID = -1;
    private int wrong_ID = -1;

    SoundEngine(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes =
                    new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build();
            mSP = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            mSP = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("turn1.ogg");
            turn1_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("turn2.ogg");
            turn2_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("turn3.ogg");
            turn3_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("turn4.ogg");
            turn4_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("turn5.ogg");
            turn5_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("turn6.ogg");
            turn6_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("turn7.ogg");
            turn7_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("actual_selection_sound.ogg");
            selection_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("selection_sound.ogg");
            putColor_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("wrong.ogg");
            wrong_ID = mSP.load(descriptor, 0);
        } catch (IOException e) {
            //Error
        }
    }

    void playTurn(int idNumber) {
        switch (idNumber) {
            case 1:
                mSP.play(turn1_ID, 1, 1, 0, 0, 1);
                break;
            case 2:
                mSP.play(turn2_ID, 1, 1, 0, 0, 1);
                break;
            case 3:
                mSP.play(turn3_ID, 1, 1, 0, 0, 1);
                break;
            case 4:
                mSP.play(turn4_ID, 1, 1, 0, 0, 1);
                break;
            case 5:
                mSP.play(turn5_ID, 1, 1, 0, 0, 1);
                break;
            case 6:
                mSP.play(turn6_ID, 1, 1, 0, 0, 1);
                break;
            case 7:
                mSP.play(turn7_ID, 1, 1, 0, 0, 1);
                break;
            case 8:
                mSP.play(selection_ID, 1, 1, 0, 0, 1);
                break;
            case 9:
                mSP.play(putColor_ID, 1, 1, 0, 0, 1);
                break;
            case 10:
                mSP.play(wrong_ID, 1, 1, 0, 0, 1);
                break;
        }
    }
}
