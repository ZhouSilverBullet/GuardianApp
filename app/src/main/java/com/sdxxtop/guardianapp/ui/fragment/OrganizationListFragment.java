package com.sdxxtop.guardianapp.ui.fragment;


import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpFragment;
import com.sdxxtop.guardianapp.model.bean.EventListBean;
import com.sdxxtop.guardianapp.presenter.OrganizationListPresenter;
import com.sdxxtop.guardianapp.presenter.contract.OrganizationListContract;
import com.sdxxtop.guardianapp.ui.activity.EventStatistyActivity;
import com.sdxxtop.guardianapp.ui.adapter.OrganizationAdapter;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author :  lwb
 * Date: 2019/10/15
 * Desc:
 */
public class OrganizationListFragment extends BaseMvpFragment<OrganizationListPresenter> implements OrganizationListContract.IView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    public static boolean canRecode = false;
    private OrganizationAdapter adapter;

    public static OrganizationListFragment newInstance() {
        Bundle args = new Bundle();
        OrganizationListFragment fragment = new OrganizationListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_organization_list;
    }

    @Override
    protected void initView() {
        smartRefresh.setEnableLoadMore(false);

        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                ((EventStatistyActivity) getActivity()).rePlaceData();
            }
        });

        adapter = new OrganizationAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EventListBean.CompleteInfo item = (EventListBean.CompleteInfo) adapter.getItem(position);
                if (item.children != null && item.children.size() == 0) {
                    UIUtils.showToast("未找到下级");
                    return;
                }
                ((EventStatistyActivity) getActivity()).onPartClick(item.parent_id, item.part_id);
//                ((EventStatistyActivity) getActivity()).upList.add(item.parent_id);
                List<EventListBean.CompleteInfo> children = item.children;
                replaceData(children);
            }
        });
    }

    @Override
    public void showError(String error) {

    }

    public void replaceData(List<EventListBean.CompleteInfo> bean) {
        EventStatistyActivity activity = (EventStatistyActivity) getActivity();
        if (activity != null) {
            adapter.setTime(activity.startTime, activity.endTime, activity.eventStatus);
        }

        if (smartRefresh != null) {
            smartRefresh.finishRefresh();
        }
        if (adapter != null && bean != null) {
            adapter.replaceData(bean);
        }
    }
}
