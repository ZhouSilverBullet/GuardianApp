package com.sdxxtop.guardianapp.ui.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.widget.TextView;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.dueeeke.videoplayer.util.PlayerUtils;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseActivity;
import com.sdxxtop.guardianapp.ui.widget.DanmukuVideoView;
import com.sdxxtop.guardianapp.ui.widget.controller.CustomerVideoController;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

import butterknife.BindView;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * @author :  lwb
 * Date: 2019/3/18
 * Desc:视频播放
 */
public class VideoPlayActivity extends BaseActivity {

    @BindView(R.id.player)
    DanmukuVideoView danmukuVideoView;
    @BindView(R.id.tv_title)
    TextView tv_title;

    private DanmakuContext mDanmakuContext;
    private DanmakuView mDanmakuView;
    private BaseDanmakuParser mParser;

    @Override
    protected void initView() {
        statusBar(false);

        String video_path = getIntent().getStringExtra("video_path");
        String title = getIntent().getStringExtra("title");

        tv_title.setText(title);
        initDanMuView();

        danmukuVideoView.addDanmukuView(mDanmakuView, mDanmakuContext, mParser);
        PlayerConfig config = new PlayerConfig.Builder().setLooping().build();
        danmukuVideoView.setPlayerConfig(config);
        danmukuVideoView.setUrl(video_path); //设置视频地址
        danmukuVideoView.setTitle(""); //设置视频标题
        CustomerVideoController controller = new CustomerVideoController(this);
        danmukuVideoView.setVideoController(controller); //设置控制器，如需定制可继承BaseVideoController
        danmukuVideoView.start(); //开始播放，不调用则不自动播放

        controller.setDanmukuSendCallback(new CustomerVideoController.DanmukuSendCallback() {
            @Override
            public void onSend(String value) {
                addDanmaku(value);
            }
        });

        controller.setDanmukuShowCallback(new CustomerVideoController.DanmukuShowCallback() {
            @Override
            public void onShow(boolean isShow) {
                if (isShow) {
                    showDanMu();
                } else {
                    hideDanMu();
                }
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (danmukuVideoView != null) {
            danmukuVideoView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (danmukuVideoView != null) {
            danmukuVideoView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (danmukuVideoView != null) {
            danmukuVideoView.release();
        }
    }

    @Override
    public void onBackPressedSupport() {
        if (!danmukuVideoView.onBackPressed()) {
            super.onBackPressedSupport();
        }
    }

    public void addDanmaku(String value) {
        mDanmakuContext.setCacheStuffer(new SpannedCacheStuffer(), null);
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }

        danmaku.text = value;
        danmaku.priority = 0;  // 可能会被各种过滤器过滤并隐藏显示
        danmaku.isLive = false;
        danmaku.setTime(mDanmakuView.getCurrentTime() + 1200);
        danmaku.textSize = PlayerUtils.sp2px(this, 12);
        danmaku.textColor = Color.WHITE;
        danmaku.textShadowColor = Color.RED;
        // danmaku.underlineColor = Color.GREEN;
        danmaku.borderColor = Color.GREEN;
        mDanmakuView.addDanmaku(danmaku);
    }

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            boolean result = fixOrientation();
        }
        super.onCreate(savedInstanceState);
    }

    private boolean fixOrientation() {
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo o = (ActivityInfo) field.get(this);
            o.screenOrientation = -1;
            field.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isTranslucentOrFloating() {
        boolean isTranslucentOrFloating = false;
        try {
            int[] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            final TypedArray ta = obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean) m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            return;
        }
        super.setRequestedOrientation(requestedOrientation);
    }

    private void initDanMuView() {
// 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        mDanmakuView = new DanmakuView(this);
        mDanmakuView.showFPS(false);
        mDanmakuContext = DanmakuContext.create();
        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3).setDuplicateMergingEnabled(false).setScrollSpeedFactor(1.2f).setScaleTextSize(1.2f)
//                .setCacheStuffer(new SpannedCacheStuffer(), null) // 图文混排使用SpannedCacheStuffer
                .setCacheStuffer(new BackgroundCacheStuffer(), null)  // 绘制背景使用BackgroundCacheStuffer
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair).setDanmakuMargin(40);
        if (mDanmakuView != null) {
            mParser = new BaseDanmakuParser() {
                @Override
                protected IDanmakus parse() {
                    return new Danmakus();
                }
            };
            mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {
                }

                @Override
                public void drawingFinished() {

                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {
//                    Log.d("DFM", "danmakuShown(): text=" + danmaku.text);
                }

                @Override
                public void prepared() {
                    mDanmakuView.start();
                }
            });
            mDanmakuView.setOnDanmakuClickListener(new IDanmakuView.OnDanmakuClickListener() {

                @Override
                public boolean onDanmakuClick(IDanmakus danmakus) {
                    Log.d("DFM", "onDanmakuClick: danmakus size:" + danmakus.size());
                    BaseDanmaku latest = danmakus.last();
                    if (null != latest) {
                        Log.d("DFM", "onDanmakuClick: text of latest danmaku:" + latest.text);
                        return true;
                    }
                    return false;
                }

                @Override
                public boolean onDanmakuLongClick(IDanmakus danmakus) {
                    return false;
                }

                @Override
                public boolean onViewClick(IDanmakuView view) {
                    return false;
                }
            });
            mDanmakuView.showFPS(false);
            mDanmakuView.enableDanmakuDrawingCache(false);
        }
    }

    public void showDanMu() {
        if (mDanmakuView != null) mDanmakuView.show();
    }

    public void hideDanMu() {
        if (mDanmakuView != null) mDanmakuView.hide();
    }

    /**
     * 绘制背景(自定义弹幕样式)
     */
    private class BackgroundCacheStuffer extends SpannedCacheStuffer {


        // 通过扩展SimpleTextCacheStuffer或SpannedCacheStuffer个性化你的弹幕样式
        final Paint paint = new Paint();

        @Override
        public void measure(BaseDanmaku danmaku, TextPaint paint, boolean fromWorkerThread) {
//            danmaku.padding = 5;  // 在背景绘制模式下增加padding
            super.measure(danmaku, paint, fromWorkerThread);
        }

        @Override
        public void drawBackground(BaseDanmaku danmaku, Canvas canvas, float left, float top) {
            paint.setAntiAlias(true);
            paint.setColor(Color.parseColor("#65777777"));//黑色 普通
            int radius = PlayerUtils.dp2px(mContext, 10);
            canvas.drawRoundRect(new RectF(left, top, left + danmaku.paintWidth, top + danmaku.paintHeight), radius, radius, paint);
        }

        @Override
        public void drawStroke(BaseDanmaku danmaku, String lineText, Canvas canvas, float left, float top, Paint paint) {
            // 禁用描边绘制
        }
    }

}
