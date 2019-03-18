package com.xuxin.guardianapp.ui.fragment;

import android.os.Bundle;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuxin.guardianapp.R;
import com.xuxin.guardianapp.base.BaseMvpFragment;
import com.xuxin.guardianapp.model.bean.LearnNewsBean;
import com.xuxin.guardianapp.presenter.NewsListFragmentPresenter;
import com.xuxin.guardianapp.presenter.contract.NewsListFragmentContract;
import com.xuxin.guardianapp.ui.adapter.NewsListAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class NewsListFragment extends BaseMvpFragment<NewsListFragmentPresenter> implements NewsListFragmentContract.IView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smart_refresh;

    private NewsListAdapter adapter;
    /******  1:新闻学习  2:罗庄发布  *******/
    private int type = 1;
    /*********  1:下拉刷新,2:上拉加载  *********/
    private int isLoadMore = 1;


    public static NewsListFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
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
        if (getArguments() != null) {
            type = getArguments().getInt("type");
            mPresenter.loadData(0,type);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsListAdapter(new ArrayList<>(), getContext(), type);
        recyclerView.setAdapter(adapter);
        //刷新
        smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isLoadMore = 1;
                mPresenter.loadData(0,type);
                refreshlayout.finishRefresh();
            }
        });
        //加载更多
        smart_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                isLoadMore = 2;
                int itemCount = 0;
                if (adapter != null) {
                    itemCount = adapter.getItemCount();
                }
                mPresenter.loadData(itemCount,type);
                refreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    public void showData(List<LearnNewsBean> data) {
        if (isLoadMore == 2) {
            adapter.addData(data);
        } else {
            adapter.replaceData(data);
        }
    }

    @Override
    public void showError(String error) {

    }
}
