package com.sdxxtop.guardianapp.ui.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.SafeStaffDetail2Presenter;
import com.sdxxtop.guardianapp.presenter.contract.SafeStaffDetailContract;
import com.sdxxtop.guardianapp.ui.adapter.SafeStaffDetailAdapter;
import com.sdxxtop.guardianapp.ui.widget.GERTimeSelectView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class SafeStaffDetail2Activity extends BaseMvpActivity<SafeStaffDetail2Presenter> implements SafeStaffDetailContract.IView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.title)
    TitleView title;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.gertsv_view)
    GERTimeSelectView gertsvView;
    @BindView(R.id.ll_event_layout)
    LinearLayout llEventLayout;
    @BindView(R.id.ll_detail_layout)
    LinearLayout llDetailLayout;

    @Override
    protected int getLayout() {
        return R.layout.activity_safe_staff_detail2;
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
        int type = getIntent().getIntExtra("type", 0);
        if (type == 1) {  // 网格员
            title.setTitleValue("上报事件");
            tvName.setVisibility(View.GONE);
            gertsvView.setVisibility(View.VISIBLE);
            llEventLayout.setVisibility(View.VISIBLE);
            llDetailLayout.setVisibility(View.GONE);
        } else if (type == 2) {  // 企业
            title.setTitleValue("安全员详情");
            tvName.setVisibility(View.VISIBLE);
            tvName.setText("银都铝业");
            gertsvView.setVisibility(View.GONE);
            llEventLayout.setVisibility(View.GONE);
            llDetailLayout.setVisibility(View.VISIBLE);
        }

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("pp");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SafeStaffDetailAdapter adapter = new SafeStaffDetailAdapter(R.layout.item_sfae_staff_view, data, type);
        recyclerView.setAdapter(adapter);
    }

}
