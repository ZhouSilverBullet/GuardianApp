package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.TabTextBean;
import com.sdxxtop.guardianapp.presenter.SafeStaffDetailPresenter;
import com.sdxxtop.guardianapp.presenter.contract.SafeStaffDetailContract;
import com.sdxxtop.guardianapp.ui.adapter.SafeStaffDetailAdapter;
import com.sdxxtop.guardianapp.ui.widget.TabTextView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class SafeStaffDetailActivity extends BaseMvpActivity<SafeStaffDetailPresenter> implements SafeStaffDetailContract, TabTextView.OnTabClickListener {

    @BindView(R.id.ll_layout_temp)
    LinearLayout llLayoutTemp;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.title)
    TitleView title;
    private int type;

    @Override
    protected int getLayout() {
        return R.layout.activity_safe_staff_detail;
    }

    @Override
    protected void initInject() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initView() {
        super.initView();
        List<TabTextBean> list = new ArrayList<>();
        List<String> data = new ArrayList<>();

        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {  //网格员
            tvName.setText("网格员:张三");
            title.setTitleValue("网格员详情");
            list.add(new TabTextBean(1, "3", "上报事件数"));
            list.add(new TabTextBean(2, "2", "处理事件数"));
            list.add(new TabTextBean(3, "4", "完成事件数"));
            list.add(new TabTextBean(4, "5", "打卡数"));
        } else if (type == 2) {  //企业
            tvName.setText("银都铝业");
            title.setTitleValue("安全员详情");
            list.add(new TabTextBean(1, "3", "安全管理员人数"));
            list.add(new TabTextBean(2, "2", "学习培训次数"));
            list.add(new TabTextBean(3, "4", "上报自查次数"));
            list.add(new TabTextBean(4, "5", "打卡次数"));
        }

        for (int i = 0; i < list.size(); i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                    .MATCH_PARENT, 1);
            TabTextBean tabTextBean = list.get(i);
            TabTextView tabTextView = new TabTextView(this);
            tabTextView.setLayoutParams(layoutParams);
            tabTextView.setValue(tabTextBean.getTitle(), tabTextBean.getDesc());
            tabTextView.setOnTabClickListener(i,this);
            llLayoutTemp.addView(tabTextView);
            if (i == list.size() - 1) {
                tabTextView.tvLine.setVisibility(View.GONE);
            }
            data.add("" + i);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SafeStaffDetailAdapter adapter = new SafeStaffDetailAdapter(R.layout.item_sfae_staff_view, data, 1);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onTabClick(int num) {
        Intent intent = new Intent(this, SafeStaffDetail2Activity.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }
}
