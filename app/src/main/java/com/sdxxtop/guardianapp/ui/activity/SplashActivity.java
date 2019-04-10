package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
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
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        super.initView();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                skipLogin();
            }
        }, 1000);
    }

    private void startActivity() {
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

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void skipLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
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

    }
}
