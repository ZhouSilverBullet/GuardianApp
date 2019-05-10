package com.sdxxtop.guardianapp.ui.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.EventStatistyPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventStatistyContract;
import com.sdxxtop.guardianapp.ui.adapter.EventStatistyListAdapter;
import com.sdxxtop.guardianapp.ui.pop.AreaSelectPopWindow;
import com.sdxxtop.guardianapp.ui.widget.CustomAreaSelectView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class EventStatistyActivity extends BaseMvpActivity<EventStatistyPresenter> implements EventStatistyContract.IView {

    @BindView(R.id.title)
    TitleView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.casv_view)
    CustomAreaSelectView casvView;

    private List<String> list = new ArrayList<>();
    private EventStatistyListAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_ecent_statistics;
    }


    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        super.initView();
        int eventId = getIntent().getIntExtra("eventId", -1);
        switch (eventId) {
            case 1:
                title.setTitleValue("已上报事件统计");
                break;
            case 2:
                title.setTitleValue("待处理事件统计");
                break;
            case 3:
                title.setTitleValue("处理中事件统计");
                break;
            case 4:
                title.setTitleValue("已处理事件统计");
                break;
            case 5:
                title.setTitleValue("事件统计");
                break;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        adapter = new EventStatistyListAdapter(R.layout.item_event_statisty, null);
        recyclerView.setAdapter(adapter);

        smartRefresh.setOnRefreshListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                smartRefresh.finishRefresh();
                smartRefresh.finishLoadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {

                adapter.replaceData(list);
                smartRefresh.finishRefresh();
                smartRefresh.finishLoadMore();
            }
        });

        smartRefresh.autoRefresh();

        casvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> data = new ArrayList<>();
                data.add("罗庄街道");
                data.add("盛庄街道");
                data.add("王庄街道");
                data.add("李庄街道");
                data.add("赵庄街道");
                data.add("赵庄街道");
                data.add("赵庄街道");
                data.add("赵庄街道");
                data.add("赵庄街道");
                data.add("赵庄街道");
                data.add("赵庄街道");
                new AreaSelectPopWindow(EventStatistyActivity.this,casvView.llAreaLayout,data,casvView.tvArea);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        for (int i = 0; i < 10; i++) {
            list.add("环保局:356" + (i + 1));
        }
    }

    @Override
    protected void initInject() {

    }

    @Override
    public void showError(String error) {

    }
}
