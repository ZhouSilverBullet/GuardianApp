package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EnterpriseSecurityBean;
import com.sdxxtop.guardianapp.model.bean.TabTextBean;
import com.sdxxtop.guardianapp.presenter.SafeStaffDetailPresenter;
import com.sdxxtop.guardianapp.presenter.contract.SafeStaffDetailContract;
import com.sdxxtop.guardianapp.ui.adapter.SafeStaffDetailAdapter;
import com.sdxxtop.guardianapp.ui.widget.CustomEventLayout;
import com.sdxxtop.guardianapp.ui.widget.GERTimeSelectView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class SafeStaffDetailActivity extends BaseMvpActivity<SafeStaffDetailPresenter> implements SafeStaffDetailContract.IView, CustomEventLayout.OnTabClickListener {

    @BindView(R.id.cel_view)
    CustomEventLayout celView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.title)
    TitleView title;
    @BindView(R.id.gertsv_view)
    GERTimeSelectView gertsvView;


    private int type;
    private int resultId;

    List<TabTextBean> list = new ArrayList<>();
    private SafeStaffDetailAdapter adapter;
    private String event_name;

    @Override
    protected int getLayout() {
        return R.layout.activity_safe_staff_detail;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {
        UIUtils.showToast(error);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.enterpriseSecurity(resultId,"","");
    }

    @Override
    protected void initView() {
        super.initView();

        resultId = getIntent().getIntExtra("id", 0);
        type = getIntent().getIntExtra("type", 0);

        if (type == 1) {  //网格员
            tvName.setText("");
            title.setTitleValue("网格员详情");
            list.add(new TabTextBean(1, "--", "上报事件数"));
            list.add(new TabTextBean(2, "--", "处理事件数"));
            list.add(new TabTextBean(3, "--", "完成事件数"));
            list.add(new TabTextBean(4, "--", "打卡数"));
        } else if (type == 2) {  //企业
            tvName.setText("");
            title.setTitleValue("安全员详情");
            list.add(new TabTextBean(1, "--", "安全管理员人数"));
            list.add(new TabTextBean(2, "--", "学习培训次数"));
            list.add(new TabTextBean(3, "--", "上报自查次数"));
            list.add(new TabTextBean(4, "--", "打卡次数"));
        }

        celView.addLayout(list);
        celView.setOnTabClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SafeStaffDetailAdapter(R.layout.item_sfae_staff_view, null);
        recyclerView.setAdapter(adapter);

        gertsvView.setOnTimeSelectListener(new GERTimeSelectView.OnTimeChooseListener() {
            @Override
            public void onTimeSelect(String startTime, String endTime) {
                mPresenter.enterpriseSecurity(resultId,startTime,endTime);
            }
        });
    }

    @Override
    public void onTabClick(int num) {
        Intent intent = new Intent(this, SafeStaffDetail2Activity.class);
        intent.putExtra("type",type);
        intent.putExtra("partId",resultId);
        intent.putExtra("title",event_name);
        startActivity(intent);
    }

    @Override
    public void showData(EnterpriseSecurityBean bean) {
        event_name = bean.getEvent_name();
        tvName.setText(event_name);
        addTabTextLayout(bean);
        adapter.replaceData(bean.getSign_data());
    }

    private void addTabTextLayout(EnterpriseSecurityBean bean) {
        list.clear();
        if (bean!=null){
            list.add(new TabTextBean(1, String.valueOf(bean.getUser_count()), "安全管理员人数"));
            list.add(new TabTextBean(2,  String.valueOf(bean.getTrai_count()), "学习培训次数"));
            list.add(new TabTextBean(3,  String.valueOf(bean.getReport_info()), "上报自查次数"));
            list.add(new TabTextBean(4,  String.valueOf(bean.getPart_count()), "打卡次数"));
            celView.addLayout(list);
        }
    }
}
