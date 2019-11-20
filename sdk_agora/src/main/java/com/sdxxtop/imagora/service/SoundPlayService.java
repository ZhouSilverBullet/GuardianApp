package com.sdxxtop.imagora.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.sdxxtop.sdkagora.R;

import java.io.IOException;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-11-19 10:26
 * Version: 1.0
 * Description:
 */
public class SoundPlayService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    public static final String ACTION_PLAY = "com.example.action.PLAY";
    MediaPlayer mMediaPlayer = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (ACTION_PLAY.equals(intent.getAction())) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.music);
//            try {
//                mMediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
//                        file.getLength());
//                mMediaPlayer.prepare();
//                file.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            mMediaPlayer.setVolume(0.5f, 0.5f);
//            mMediaPlayer.setOnPreparedListener(this);
//            mMediaPlayer.setOnErrorListener(this);
//            mMediaPlayer.prepareAsync(); // prepare async to not block main thread
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer.start();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }
}
