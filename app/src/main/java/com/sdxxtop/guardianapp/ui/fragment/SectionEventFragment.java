package com.sdxxtop.guardianapp.ui.fragment;

import android.os.Bundle;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpFragment;
import com.sdxxtop.guardianapp.presenter.UnifyEventPresenter;
import com.sdxxtop.guardianapp.presenter.contract.UnifyEventContract;
import com.sdxxtop.guardianapp.ui.adapter.SectionEventAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author :  lwb
 * Date: 2019/7/26
 * Desc:
 */
public class SectionEventFragment extends BaseMvpFragment<UnifyEventPresenter> implements UnifyEventContract.IView {

    private static int mEventType;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    private SectionEventAdapter adapter;

    public static SectionEventFragment newInstance(int type) {
        mEventType = type;
        Bundle args = new Bundle();
        args.putInt("type", mEventType);
        SectionEventFragment fragment = new SectionEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_section_event;
    }

    @Override
    protected void initView() {
        super.initView();

        List<String> list =  new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            list.add("sss");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SectionEventAdapter(R.layout.item_section_event, list);
        recyclerView.setAdapter(adapter);

        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                smartRefresh.finishRefresh();
                smartRefresh.finishLoadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                smartRefresh.finishRefresh();
                smartRefresh.finishLoadMore();
            }
        });
    }
}
