package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.AutoLoginBean;
import com.sdxxtop.guardianapp.presenter.SplashPresenter;
import com.sdxxtop.guardianapp.presenter.contract.SplashContract;
import com.sdxxtop.guardianapp.utils.SpUtil;

import androidx.annotation.Nullable;


public class SplashActivity extends BaseMvpActivity<SplashPresenter> implements SplashContract.IView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
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

        //再进行跳转
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }

    private void skipLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onStop() {
        finish();
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                skipLogin();
            }
        }, 1000);
    }
}
