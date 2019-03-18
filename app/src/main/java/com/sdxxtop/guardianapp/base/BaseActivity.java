package com.sdxxtop.guardianapp.base;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.sdxxtop.guardianapp.utils.StatusBarUtil;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

public abstract class BaseActivity extends SwipeBackActivity {

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        setSwipeBackEnable(false);
        initStatusBar();

        mUnbinder = ButterKnife.bind(this);
        initVariables();
        initView();
        initEvent();
        initData();
    }

    /**
     * statusBar 控制
     */
    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            if (StatusBarUtil.MIUISetStatusBarLightMode(getWindow(), true)) {//小米MIUI系统
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Android6.0以上系统
                    StatusBarUtil.android6_SetStatusBarLightMode(getWindow());
                    StatusBarUtil.compat(this);
                } else {
                    StatusBarUtil.compat(this);
                }
            } else if (StatusBarUtil.FlymeSetStatusBarLightMode(getWindow(), true)) {//魅族flyme系统
                StatusBarUtil.compat(this);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Android6.0以上系统
                StatusBarUtil.android6_SetStatusBarLightMode(getWindow());
                StatusBarUtil.compat(this);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
    }
    public void statusBar(boolean isDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Android6.0以上系统
            StatusBarUtil.setDarkStatusIcon(this.getWindow(), isDark);
        }
    }
    protected void initEvent() {
    }

    protected void initData() {
    }

    protected void initView() {
    }

    protected void initVariables() {
    }

    protected abstract int getLayout();
}
