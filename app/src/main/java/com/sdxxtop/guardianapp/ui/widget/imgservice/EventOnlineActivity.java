package com.sdxxtop.guardianapp.ui.widget.imgservice;

import android.view.View;

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

}
