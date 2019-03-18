package com.xuxin.guardianapp.ui.activity;

import com.xuxin.guardianapp.R;
import com.xuxin.guardianapp.base.BaseMvpActivity;
import com.xuxin.guardianapp.presenter.NewsDetailPresenter;
import com.xuxin.guardianapp.presenter.contract.NewDetailContract;

/**
 * @author :  lwb
 * Date: 2019/3/18
 * Desc:  文章详情
 */
public class NewsDetailsActivity extends BaseMvpActivity<NewsDetailPresenter> implements NewDetailContract.IView {

    private int article_id; // 文章id

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
        article_id = getIntent().getIntExtra("article_id",0);
    }

    @Override
    protected void initData() {
        presenter.loadData(article_id);
    }
}
