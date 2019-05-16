package com.sdxxtop.guardianapp.ui.activity;

import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.GridreportPatrolBean;
import com.sdxxtop.guardianapp.model.bean.compar.GridreportPatrolCompar;
import com.sdxxtop.guardianapp.presenter.GACPPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GACPContract;
import com.sdxxtop.guardianapp.ui.adapter.GACPDetailAdapter;
import com.sdxxtop.guardianapp.ui.pop.AreaSelectPopWindow;
import com.sdxxtop.guardianapp.ui.widget.CustomAreaSelectView;
import com.sdxxtop.guardianapp.ui.widget.GridreportPatrolTabTitleView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class GACPatrolDetailActivity extends BaseMvpActivity<GACPPresenter> implements GACPContract.IView {

    @BindView(R.id.title)
    TitleView title;
    @BindView(R.id.casv_view)
    CustomAreaSelectView casvView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_bg)
    TextView tvBg;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.gptt_view)
    GridreportPatrolTabTitleView gpttView;

    List<AreaSelectPopWindow.PopWindowDataBean> data = new ArrayList<>();
    private GACPDetailAdapter adapter;
    private int part_Typeid;  // 选中区域的id
    private int start_page;  // 列表开始的标识


    @Override
    protected int getLayout() {
        return R.layout.activity_gacpatrol_detail;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @OnClick(R.id.casv_view)
    public void onViewClicked() {
        AreaSelectPopWindow popWindow = new AreaSelectPopWindow(GACPatrolDetailActivity.this, casvView, data, casvView.tvArea, tvBg);
        popWindow.setOnPopItemClickListener(new AreaSelectPopWindow.OnPopItemClickListener() {
            @Override
            public void onPopItemClick(int partTypeid, String partName) {
                part_Typeid = partTypeid;
                mPresenter.gridreportPatrol(part_Typeid, start_page);
            }
        });
    }

    @Override
    protected void initView() {
        super.initView();

        smartRefresh.setEnableLoadMore(true);
        smartRefresh.setEnableRefresh(true);

        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                start_page = adapter.getItemCount();
                mPresenter.gridreportPatrol(part_Typeid, start_page);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                start_page = 0;
                mPresenter.gridreportPatrol(part_Typeid, start_page);
            }
        });

        gpttView.setOnTextClickListener(new GridreportPatrolTabTitleView.OnTextClickListener() {
            @Override
            public void onTextClick(int num, boolean isOrder) {
                List<GridreportPatrolBean.TrailUser> data = adapter.getData();
                Collections.sort(data, new GridreportPatrolCompar(num, isOrder));
                adapter.replaceData(data);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GACPDetailAdapter(R.layout.item_gace_view, null);
        recyclerView.setAdapter(adapter);

        smartRefresh.autoRefresh();
    }

    @Override
    public void showData(GridreportPatrolBean bean) {
        smartRefresh.finishRefresh();
        smartRefresh.finishLoadMore();

        data.clear();
        if (bean.getUser_part() != null && bean.getUser_part().size() > 0) {
            for (GridreportPatrolBean.UserPart userPart : bean.getUser_part()) {
                data.add(new AreaSelectPopWindow.PopWindowDataBean(userPart.getPart_id(), userPart.getPart_name()));
            }
        }
        if (start_page == 0) {
            adapter.replaceData(bean.getTrail_user());
        } else {
            adapter.addData(bean.getTrail_user());
        }
    }
}
