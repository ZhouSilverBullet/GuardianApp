package com.sdxxtop.guardianapp.ui.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventListBean;
import com.sdxxtop.guardianapp.presenter.EventStatistyPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventStatistyContract;
import com.sdxxtop.guardianapp.ui.adapter.EventStatistyListAdapter;
import com.sdxxtop.guardianapp.ui.pop.AreaSelectPopWindow;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class EventStatistyActivity extends BaseMvpActivity<EventStatistyPresenter> implements EventStatistyContract.IView {

    @BindView(R.id.title)
    TitleView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.tv_event_num)
    TextView tvEventNum;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.tv_bg)
    TextView tvBg;
    @BindView(R.id.ll_area_layout)
    LinearLayout llAreaLayout;
    @BindView(R.id.ll_containor_temp)
    LinearLayout llContainorTemp;

    private EventStatistyListAdapter adapter;
    private List<EventListBean.CompleteInfo> part = new ArrayList<>();
    private int eventId;
    private int part_typeid = 0;  // 选择区的标识
    private String tempText = "";  // 用来拼接count


    @Override
    protected int getLayout() {
        return R.layout.activity_ecent_statistics;
    }


    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        super.initView();
        smartRefresh.setEnableRefresh(false);
        smartRefresh.setEnableLoadMore(false);
        eventId = getIntent().getIntExtra("event_type", 0);

        switch (eventId) {
            case 0:
                title.setTitleValue("已上报事件统计");
                tempText="已上报事件总数：";
                break;
            case 1:
                title.setTitleValue("待处理事件统计");
                tempText="待处理事件总数：";
                break;
            case 2:
                title.setTitleValue("处理中事件统计");
                tempText="处理中事件总数：";
                break;
            case 3:
                title.setTitleValue("已处理事件统计");
                tempText="已处理报事件总数：";
                break;
            case 4:
                title.setTitleValue("事件统计");
                tempText="已完成事件总数：";
                break;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        adapter = new EventStatistyListAdapter(R.layout.item_event_statisty, null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.eventlist(eventId, part_typeid);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @OnClick(R.id.ll_area_layout)
    public void onViewClicked(View view) {
        AreaSelectPopWindow popWindow = new AreaSelectPopWindow(EventStatistyActivity.this, llContainorTemp, part, tvArea,tvBg);
        popWindow.setOnPopItemClickListener(new AreaSelectPopWindow.OnPopItemClickListener() {
            @Override
            public void onPopItemClick(int part_typeid) {
                mPresenter.eventlist(eventId, part_typeid);
            }
        });
    }

    @Override
    public void showListData(EventListBean listBean) {
        part.clear();
        tvArea.setText(listBean.getEvent_name()+"");
        tvEventNum.setText(tempText+listBean.getCount());
        part.add(new EventListBean.CompleteInfo(0,"全部",0));
        part.addAll(listBean.getPart());
        adapter.replaceData(listBean.getCompleteInfo());
    }
}
