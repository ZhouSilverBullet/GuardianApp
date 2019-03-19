package com.sdxxtop.guardianapp.ui.activity;

import android.widget.TextView;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseActivity;

/**
 * @author :  lwb
 * Date: 2019/3/18
 * Desc:视频播放
 */
public class VideoPlayActivity extends BaseActivity {

    private IjkVideoView ijkVideoView;
    private TextView tv_title;

    @Override
    protected void initView() {
        statusBar(false);

        String video_path = getIntent().getStringExtra("video_path");
        String title = getIntent().getStringExtra("title");

        ijkVideoView = findViewById(R.id.player);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText(title);

        ijkVideoView.setUrl(video_path); //设置视频地址
        ijkVideoView.setTitle(""); //设置视频标题
        StandardVideoController controller = new StandardVideoController(this);
        ijkVideoView.setVideoController(controller); //设置控制器，如需定制可继承BaseVideoController
        ijkVideoView.start(); //开始播放，不调用则不自动播放

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ijkVideoView!=null){
            ijkVideoView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ijkVideoView!=null){
            ijkVideoView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ijkVideoView!=null){
            ijkVideoView.release();
        }
    }

    @Override
    public void onBackPressedSupport() {
        if (!ijkVideoView.onBackPressed()) {
            super.onBackPressedSupport();
        }
    }
}
