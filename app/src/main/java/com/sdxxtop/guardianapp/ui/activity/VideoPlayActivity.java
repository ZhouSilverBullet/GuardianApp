package com.sdxxtop.guardianapp.ui.activity;

import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseActivity;

import butterknife.BindView;

/**
 * @author :  lwb
 * Date: 2019/3/18
 * Desc:视频播放
 */
public class VideoPlayActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    private String video_path;

    @Override
    protected int getLayout() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void initView() {
        video_path = getIntent().getStringExtra("video_path");
        tvTitle.setText(video_path);
    }
}
