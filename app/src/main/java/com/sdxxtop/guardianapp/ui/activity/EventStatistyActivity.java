package com.sdxxtop.guardianapp.ui.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventListBean;
import com.sdxxtop.guardianapp.model.bean.EventShowBean;
import com.sdxxtop.guardianapp.presenter.EventStatistyPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventStatistyContract;
import com.sdxxtop.guardianapp.ui.adapter.EventStatistyListAdapter;
import com.sdxxtop.guardianapp.ui.pop.AreaSelectPopWindow;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.ui.widget.timePicker.ProvincePickerView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class EventStatistyActivity extends BaseMvpActivity<EventStatistyPresenter> implements EventStatistyContract.IView, ProvincePickerView.OnConfirmClick {

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
    @BindView(R.id.ll_containor_temp)
    LinearLayout llContainorTemp;

    private EventStatistyListAdapter adapter;
    private List<AreaSelectPopWindow.PopWindowDataBean> popWondowData = new ArrayList<>();
    private int eventId;
    private int part_typeid = 0;  // 选择区的标识
    private String tempText = "";  // 用来拼接count
    private String startTime, endTime;


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
        startTime = getIntent().getStringExtra("startTime");
        endTime = getIntent().getStringExtra("endTime");

        switch (eventId) {
            case 0:
                title.setTitleValue("已上报事件统计");
                tempText = "已上报事件统计：";
                break;
            case 1:
                title.setTitleValue("待处理事件统计");
                tempText = "待处理事件统计：";
                break;
            case 2:
                title.setTitleValue("处理中事件统计");
                tempText = "处理中事件统计：";
                break;
            case 3:
                title.setTitleValue("已处理事件统计");
                tempText = "已处理报事件统计：";
                break;
            case 4:
                title.setTitleValue("已完成事件统计");
                tempText = "已完成事件统计：";
                break;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        adapter = new EventStatistyListAdapter(R.layout.item_event_statisty, null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.eventlist(eventId, part_typeid, startTime, endTime);
        mPresenter.eventShow();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @OnClick(R.id.rl_area_layout)
    public void onViewClicked(View view) {
        if (pickerUtil != null) {
            pickerUtil.show();
        }
        if (1 == 1) {
            return;
        }
        AreaSelectPopWindow popWindow = new AreaSelectPopWindow(EventStatistyActivity.this, llContainorTemp, popWondowData, tvArea, tvBg);
        popWindow.setOnPopItemClickListener(new AreaSelectPopWindow.OnPopItemClickListener() {
            @Override
            public void onPopItemClick(int part_typeid, String partName) {
                mPresenter.eventlist(eventId, part_typeid, startTime, endTime);
            }
        });
    }

    @Override
    public void showListData(EventListBean listBean) {
        popWondowData.clear();
        tvArea.setText(listBean.getEvent_name() + "");
        tvEventNum.setText(tempText + listBean.getCount());
        if (listBean.getPart() != null && listBean.getPart().size() > 0) {
            for (EventListBean.CompleteInfo completeInfo : listBean.getPart()) {
                popWondowData.add(new AreaSelectPopWindow.PopWindowDataBean(completeInfo.getPart_id(), completeInfo.getPart_name()));
            }
        }
        adapter.replaceData(listBean.getCompleteInfo());
        adapter.setTime(startTime, endTime, eventId);
    }

    private ProvincePickerView pickerUtil;

    @Override
    public void showEventBean(EventShowBean bean) {
        pickerUtil = new ProvincePickerView(this, bean.getPart());
        pickerUtil.setOnConfirmClick(this);
    }

    /**
     * @param resultTx 选中的部门的名称
     * @param resultId 选中部门的id
     */
    @Override
    public void confirmClick(String resultTx, int resultId) {
        part_typeid = resultId;
        mPresenter.eventlist(eventId, part_typeid, startTime, endTime);
    }
}
