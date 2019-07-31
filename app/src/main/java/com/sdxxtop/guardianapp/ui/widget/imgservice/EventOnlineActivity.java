package com.sdxxtop.guardianapp.ui.widget.imgservice;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.sdxxtop.guardianapp.utils.StatusBarUtil;

/**
 * @author :  lwb
 * Date: 2019/7/26
 * Desc:
 */
public class EventOnlineActivity extends ServiceWebActivity {

    @Override
    protected void initData() {
        initStatusBar();
        titleView.setVisibility(View.VISIBLE);
        include.setVisibility(View.VISIBLE);
        String href = getIntent().getStringExtra("href");
        webView.loadUrl(href);
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
}
