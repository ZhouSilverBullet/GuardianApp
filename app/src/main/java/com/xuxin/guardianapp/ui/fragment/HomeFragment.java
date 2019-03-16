package com.xuxin.guardianapp.ui.fragment;


import com.orhanobut.logger.Logger;
import com.xuxin.guardianapp.R;
import com.xuxin.guardianapp.base.BaseMvpFragment;
import com.xuxin.guardianapp.presenter.HomeFragmentPresenter;
import com.xuxin.guardianapp.presenter.contract.HomeFragmentContract;
import com.xuxin.guardianapp.ui.adapter.HomeRecyclerAdapter;
import com.xuxin.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * A simple {@link HomeFragment} subclass.
 */
public class HomeFragment extends BaseMvpFragment<HomeFragmentPresenter> implements HomeFragmentContract.IView {
    @BindView(R.id.tv_title)
    TitleView mTitleView;
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        super.initView();
        statusBar(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<Integer> data = getRecyclerData();

        mRecyclerView.setAdapter(new HomeRecyclerAdapter(R.layout.item_home_recycler, data));
    }

    private ArrayList<Integer> getRecyclerData() {
        ArrayList<Integer> data = new ArrayList<>();
        data.add(R.drawable.list_1);
        data.add(R.drawable.list_2);
        data.add(R.drawable.list_3);
        data.add(R.drawable.list_4);
        data.add(R.drawable.list_5);
        data.add(R.drawable.list_6);
        return data;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadSignData();
    }

    @Override
    public void showError(String error) {
//        mTextView.setText(error);
    }

    @Override
    public void showData(String data) {
        Logger.e(data);
//        mTextView.setText(data);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            statusBar(false);
        }
    }

}
