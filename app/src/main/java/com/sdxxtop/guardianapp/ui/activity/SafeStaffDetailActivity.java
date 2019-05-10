package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.TabTextBean;
import com.sdxxtop.guardianapp.presenter.SafeStaffDetailPresenter;
import com.sdxxtop.guardianapp.presenter.contract.SafeStaffDetailContract;
import com.sdxxtop.guardianapp.ui.adapter.SafeStaffDetailAdapter;
import com.sdxxtop.guardianapp.ui.widget.TabTextView;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class SafeStaffDetailActivity extends BaseMvpActivity<SafeStaffDetailPresenter> implements SafeStaffDetailContract, TabTextView.OnTabClickListener {

    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

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
        list.add(new TabTextBean(1,"3","安全管理员人数"));
        list.add(new TabTextBean(1,"2","学习培训次数"));
        list.add(new TabTextBean(1,"4","上报自查次数"));
        list.add(new TabTextBean(1,"5","打卡次数"));

        for (int i = 0; i < list.size(); i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            layoutParams.setMargins(5,5,5,5);
            TabTextBean tabTextBean = list.get(i);
            TabTextView tabTextView = new TabTextView(this);
            tabTextView.setLayoutParams(layoutParams);
            tabTextView.setOnTabClickListener(i,this);
            tabTextView.setValue(tabTextBean.getTitle(),tabTextBean.getDesc());
            llLayout.addView(tabTextView);
            data.add(""+i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SafeStaffDetailAdapter adapter = new SafeStaffDetailAdapter(R.layout.item_sfae_staff_view,data,1);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onTabClick(int num) {
        UIUtils.showToast("点击了第"+num+"个");
        Intent intent = new Intent(this,SafeStaffDetail2Activity.class);
        startActivity(intent);

    }
}
