package com.sdxxtop.guardianapp.ui.fragment;


import android.os.Bundle;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpFragment;
import com.sdxxtop.guardianapp.model.bean.AuthDataBean;
import com.sdxxtop.guardianapp.presenter.DataMonitoringPresenter;
import com.sdxxtop.guardianapp.presenter.contract.DataMonitoringContract;
import com.sdxxtop.guardianapp.ui.adapter.DataMonitoringAdapter;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * A simple {@link DataMonitoringFragment} subclass.
 */
public class DataMonitoringFragment extends BaseMvpFragment<DataMonitoringPresenter> implements DataMonitoringContract.IView {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.titleView)
    TitleView titleView;
    private DataMonitoringAdapter adapter;

    public static DataMonitoringFragment newInstance() {
        Bundle args = new Bundle();
        DataMonitoringFragment fragment = new DataMonitoringFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_data_monitor;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        super.initView();
        statusBar(true);
        topViewPadding(titleView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DataMonitoringAdapter(R.layout.item_monitor_list, null);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void showData(AuthDataBean bean) {
        List<AuthDataBean.AuthBean> beanList = bean.getAuth();
        if (beanList != null && beanList.size() > 0) {
            adapter.replaceData(beanList);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadData();
    }


    @Override
    public void showError(String error) {
        showToast(error);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            statusBar(true);
            mPresenter.loadData();
        }
    }
}
