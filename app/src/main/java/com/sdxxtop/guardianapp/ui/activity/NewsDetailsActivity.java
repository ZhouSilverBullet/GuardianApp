package com.sdxxtop.guardianapp.ui.activity;

import android.view.KeyEvent;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseActivity;
import com.sdxxtop.webview.CommonWebFragment;
import com.sdxxtop.webview.basefragment.BaseWebviewFragment;

/**
 * @author :  lwb
 * Date: 2019/3/18
 * Desc:  文章详情
 */
public class NewsDetailsActivity extends BaseActivity {

    private String article_path; // 文章id
    BaseWebviewFragment webviewFragment;

    @Override
    protected int getLayout() {
        return R.layout.activity_news_details;
    }

    @Override
    protected boolean isInitStatusBar() {
        return false;
    }

    @Override
    protected void initView() {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        article_path = getIntent().getStringExtra("article_path");

        webviewFragment = CommonWebFragment.newInstance(article_path);
        transaction.replace(R.id.web_view_fragment, webviewFragment).commit();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webviewFragment != null && webviewFragment instanceof BaseWebviewFragment) {
            boolean flag = webviewFragment.onKeyDown(keyCode, event);
            if (flag) {
                return flag;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
