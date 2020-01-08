package com.sdxxtop.guardianapp.ui.widget.imgservice;


import android.view.View;

import com.sdxxtop.guardianapp.R;

public class OnlineServiceActivity extends ServiceWebActivity {

    @Override
    protected void initData() {
        if (getIntent() != null) {
            boolean isShowTitile = getIntent().getBooleanExtra("isShowTitile", false);
            if (isShowTitile){
                titleView.setVisibility(isShowTitile ? View.VISIBLE : View.GONE);
                titleView.setTitleValue("绩效说明");
                titleView.setBgColor(getResources().getColor(R.color.blue));
                titleView.setWhiteTheme();
            }

            String href = getIntent().getStringExtra("href");
            webView.loadUrl(href);
        }
    }
}
