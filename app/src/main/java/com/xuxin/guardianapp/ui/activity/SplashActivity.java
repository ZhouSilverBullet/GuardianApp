package com.xuxin.guardianapp.ui.activity;

import android.content.Intent;

import com.xuxin.guardianapp.R;
import com.xuxin.guardianapp.base.BaseActivity;

public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayout() {
//        reportFullyDrawn();
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        super.initView();

        startActivity();
    }

    private void startActivity() {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
