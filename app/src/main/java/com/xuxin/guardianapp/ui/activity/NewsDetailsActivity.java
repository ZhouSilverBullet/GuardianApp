package com.xuxin.guardianapp.ui.activity;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xuxin.guardianapp.R;
import com.xuxin.guardianapp.base.BaseMvpActivity;
import com.xuxin.guardianapp.presenter.NewsDetailPresenter;
import com.xuxin.guardianapp.presenter.contract.NewDetailContract;

import butterknife.BindView;

/**
 * @author :  lwb
 * Date: 2019/3/18
 * Desc:  文章详情
 */
public class NewsDetailsActivity extends BaseMvpActivity<NewsDetailPresenter> implements NewDetailContract.IView {

    @BindView(R.id.webview)
    WebView webView;

    private String article_path; // 文章id

    @Override
    protected int getLayout() {
        return R.layout.activity_news_details;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initView() {
        article_path = getIntent().getStringExtra("article_path");

        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示

        // 设置WebView的客户端
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;// 返回false
            }
        });

        WebSettings webSettings = webView.getSettings();
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        webSettings.setAppCacheEnabled(true);
        // 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 设置缓存路径
//        webSettings.setAppCachePath("");
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(false);
        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);
        //现加载网页,在加载图片
        webSettings.setBlockNetworkImage(true);
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(true);
        // 设置默认字体大小
        webSettings.setDefaultFontSize(12);

        webView.loadUrl(article_path);
    }

    @Override
    protected void initData() {

    }
}
