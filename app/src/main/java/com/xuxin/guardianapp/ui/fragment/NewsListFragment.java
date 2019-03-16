package com.xuxin.guardianapp.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;


import com.xuxin.guardianapp.R;
import com.xuxin.guardianapp.base.BaseMvpFragment;
import com.xuxin.guardianapp.presenter.NewsListFragmentPresenter;
import com.xuxin.guardianapp.presenter.contract.NewsListFragmentContract;

import butterknife.BindView;

public class NewsListFragment extends BaseMvpFragment<NewsListFragmentPresenter> implements NewsListFragmentContract.IView {
    @BindView(R.id.tv_news_list)
    TextView tv;

    public static NewsListFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("title", title);
        NewsListFragment fragment = new NewsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void initView() {
        super.initView();
        if (getArguments() != null) {
            String title = getArguments().getString("title");
            mPresenter.loadData(title);
        }
    }

    @Override
    public void showData(String value) {
        tv.setText(value);
    }

    @Override
    public void showError(String error) {

    }
}
