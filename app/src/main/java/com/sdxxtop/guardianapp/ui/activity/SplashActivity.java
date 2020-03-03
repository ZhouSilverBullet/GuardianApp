package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.alipush.AnalyticsHome;
import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.AutoLoginBean;
import com.sdxxtop.guardianapp.model.bean.TrackInfoBean;
import com.sdxxtop.guardianapp.presenter.SplashPresenter;
import com.sdxxtop.guardianapp.presenter.contract.SplashContract;
import com.sdxxtop.guardianapp.utils.SpUtil;
import com.sdxxtop.guardianapp.utils.UIUtils;

import androidx.annotation.Nullable;

import me.jessyan.autosize.internal.CancelAdapt;


public class SplashActivity extends BaseMvpActivity<SplashPresenter> implements SplashContract.IView, CancelAdapt {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            Log.e("SplashActivity", "true");
            finish();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            // 仅当缺口区域完全包含在状态栏之中时，才允许窗口延伸到刘海区域显示
//            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
            // 永远不允许窗口延伸到刘海区域
//            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
            // 始终允许窗口延伸到屏幕短边上的刘海区域
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }
    }

    @Override
    protected boolean isInitStatusBar() {
        return false;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
        super.initData();

        mPresenter.autoLogin();

    }

    @Override
    public void autoSuccess(AutoLoginBean autoLoginBean) {
        //阿里云推送绑定账号
        AnalyticsHome.bindAccount(SpUtil.getString(Constants.MOBILE));

        String autoToken = SpUtil.getString(Constants.AUTO_TOKEN);
        if (TextUtils.isEmpty(autoToken)) {
            skipLogin();
            return;
        }

        int expireTime = SpUtil.getInt(Constants.EXPIRE_TIME, 0);
        if (expireTime == 0) {
            skipLogin();
            return;
        }

        //token时间失效 php发过来的时间戳需要 除1000
        if (expireTime - (System.currentTimeMillis() / 1000) < 0) {
            skipLogin();
            return;
        }


        //再进行缓存一遍
        String auto_token = autoLoginBean.getAuto_token();
        int expire_time = autoLoginBean.getExpire_time();
        String name = autoLoginBean.getName();
        int part_id = autoLoginBean.getPart_id();
        int userid = autoLoginBean.getUserid();

        SpUtil.putInt(Constants.USER_ID, userid);
        SpUtil.putInt(Constants.EXPIRE_TIME, expire_time);
        SpUtil.putInt(Constants.PART_ID, part_id);
        SpUtil.putString(Constants.AUTO_TOKEN, auto_token);

        /******** 猎鹰相关 ********/
        SpUtil.putLong(Constants.IS_TRACK, autoLoginBean.getIs_track(), false);
        TrackInfoBean trackInfo = autoLoginBean.track_info;
        if (trackInfo != null) {
            SpUtil.putLong(Constants.SERVICE_ID, trackInfo.getSid(), false);
            SpUtil.putLong(Constants.TERMINAL_ID, trackInfo.getTid(), false);
            SpUtil.putLong(Constants.TRACK_ID, trackInfo.getTrid(), false);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

    }

    private void skipLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

    }

    @Override
    protected void onPause() {
        super.onPause();
//        finish();
    }

    @Override
    protected void onStop() {
//        finish();
        super.onStop();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

    @Override
    public void showError(String error) {
        if (!"参数错误".equals(error)) {
            UIUtils.showToast(error);
        }
        skipLogin();
    }
}
