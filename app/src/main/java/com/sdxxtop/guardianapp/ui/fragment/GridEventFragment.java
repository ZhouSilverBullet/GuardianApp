package com.sdxxtop.guardianapp.ui.fragment;

import android.os.Bundle;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpFragment;
import com.sdxxtop.guardianapp.model.bean.GridEventListBean;
import com.sdxxtop.guardianapp.presenter.GridEventFmPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GridEventFmContract;
import com.sdxxtop.guardianapp.ui.adapter.GridEventAdapter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author :  lwb
 * Date: 2019/9/17
 * Desc:
 */
public class GridEventFragment extends BaseMvpFragment<GridEventFmPresenter> implements GridEventFmContract.IView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    private GridEventAdapter adapter;
    public int type = 0;  // 默认列表的全部
    private int pathType;

    public static GridEventFragment newInstance(int pathType) {
        Bundle args = new Bundle();
        args.putInt("pathType", pathType);
        GridEventFragment fragment = new GridEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_section_event;
    }

    @Override
    public void showError(String error) {
        showToast(error);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible()) {
            loadData(type, 0);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        pathType = getArguments().getInt("pathType");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GridEventAdapter(null);
        recyclerView.setAdapter(adapter);

        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (adapter != null) {
                    int itemCount = adapter.getItemCount();
                    loadData(type, itemCount);
                }
                smartRefresh.finishRefresh();
                smartRefresh.finishLoadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadData(type, 0);
                smartRefresh.finishRefresh();
                smartRefresh.finishLoadMore();
            }
        });
    }

    public void loadData(int type, int start_page) {
        if (adapter != null) {
            mPresenter.loadData(pathType, type, start_page);
        }
    }

    @Override
    public void showData(GridEventListBean bean, int pathType, int start_page) {
        List<GridEventListBean.GridListBean> event = pathType == 3 ? bean.partol : bean.event;
        if (event != null) {
            if (start_page == 0) {
                adapter.replaceData(event);
            } else {
                adapter.addData(event);
            }
            adapter.setPathType(pathType);
        }
    }


    /**
     * 此方法目前仅适用于标示ViewPager中的Fragment是否真实可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadData(type, 0);
        }
    }
}
