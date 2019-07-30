package com.sdxxtop.guardianapp.ui.fragment;

import android.os.Bundle;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpFragment;
import com.sdxxtop.guardianapp.model.bean.SectionEventBean;
import com.sdxxtop.guardianapp.presenter.UnifyEventPresenter;
import com.sdxxtop.guardianapp.presenter.contract.UnifyEventContract;
import com.sdxxtop.guardianapp.ui.activity.SectionEventActivity;
import com.sdxxtop.guardianapp.ui.adapter.SectionEventAdapter;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author :  lwb
 * Date: 2019/7/26
 * Desc:
 */
public class SectionEventFragment extends BaseMvpFragment<UnifyEventPresenter> implements UnifyEventContract.IView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    private SectionEventAdapter adapter;
    /**
     * 切换刷新
     */
    protected boolean isCreated = false;

    public static SectionEventFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
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
        showToast(error);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(0);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_section_event;
    }

    @Override
    protected void initView() {
        super.initView();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SectionEventAdapter(R.layout.item_section_event, null);
        recyclerView.setAdapter(adapter);

        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (adapter != null) {
                    int itemCount = adapter.getItemCount();
                    loadData(itemCount);
                }
                smartRefresh.finishRefresh();
                smartRefresh.finishLoadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadData(0);
                smartRefresh.finishRefresh();
                smartRefresh.finishLoadMore();
            }
        });
    }

    public void loadData(int start_page) {
        if (adapter != null) {
            mPresenter.loadData(getArguments().getInt("type"), start_page);
        }
    }

    @Override
    public void showData(SectionEventBean bean, int page_start) {
        List<SectionEventBean.ClaimInfoBean> claim = bean.getClaim();
        if (bean.getNumStr() != null) {
            ((SectionEventActivity) getActivity()).setChengeCount(bean.getNumStr());
        }
        if (claim != null) {
            if (page_start == 0) {
                adapter.replaceData(claim);
            } else {
                adapter.addData(claim);
            }
        }
    }

    /**
     * 此方法目前仅适用于标示ViewPager中的Fragment是否真实可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isCreated) {
            return;
        }
        if (isVisibleToUser) {
            loadData(0);
        }
    }
}
