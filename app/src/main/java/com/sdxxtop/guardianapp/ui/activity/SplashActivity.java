package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;

import com.sdxxtop.guardianapp.base.BaseActivity;
import com.sdxxtop.guardianapp.R;

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
