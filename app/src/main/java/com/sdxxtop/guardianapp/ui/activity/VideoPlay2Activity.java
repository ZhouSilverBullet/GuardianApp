package com.sdxxtop.guardianapp.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.sdxxtop.guardianapp.R;

import androidx.appcompat.app.AppCompatActivity;
import cn.jzvd.JZUtils;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoPlay2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play2);
        JzvdStd jzvdStd = findViewById(R.id.jzvideo);

        JZUtils.hideStatusBar(this);
        String url = getIntent().getStringExtra("video_path");
        jzvdStd.setUp(url, " ");
//        jzvdStd.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4", " ");
//        jzvdStd.setScreenFullscreen();
        jzvdStd.startVideo();

        jzvdStd.fullscreenButton.setVisibility(View.GONE);
        jzvdStd.currentTimeTextView.setVisibility(View.GONE);
        jzvdStd.progressBar.setVisibility(View.GONE);
        jzvdStd.totalTimeTextView.setVisibility(View.GONE);
        jzvdStd.batteryLevel.setVisibility(View.GONE);
        jzvdStd.videoCurrentTime.setVisibility(View.GONE);

        jzvdStd.fullscreenButton.performClick();
        jzvdStd.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {

        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}
