package com.sdxxtop.sdkagora;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;

import java.io.IOException;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-11-19 10:35
 * Version: 1.0
 * Description:
 */
public class SoundPlayer implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    private static final String TAG = "SoundPlayer2";
    private MediaPlayer mMediaPlayer;
    private Vibrator vibrator;

    public void initPlayer() {
        mMediaPlayer = new MediaPlayer();
        Application context = AgoraSession.getLiveConfig().getApplicationContext();
        AssetManager am = context.getAssets();
        try {
            AssetFileDescriptor afd = am.openFd("music.mp3");
            mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.prepareAsync(); // prepare async to not block main thread

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mMediaPlayer.start();
                mMediaPlayer.setLooping(true);
            }
        });

        vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] patter = {1000, 1000, 2000, 2000};
        vibrator.vibrate(patter, 0);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer.start();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e(TAG, "onError: extra --> " + extra);
        return false;
    }

    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }

        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}
