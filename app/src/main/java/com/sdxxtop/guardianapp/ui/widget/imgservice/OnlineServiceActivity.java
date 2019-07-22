package com.sdxxtop.guardianapp.ui.widget.imgservice;


public class OnlineServiceActivity extends ServiceWebActivity {

    @Override
    protected void initData() {
        String href = getIntent().getStringExtra("href");
        webView.loadUrl(href);
    }
}
