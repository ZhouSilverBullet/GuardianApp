package com.sdxxtop.guardianapp.ui.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EnterpriseSecurityBean;
import com.sdxxtop.guardianapp.model.bean.EnterpriseUserdetailsBean;
import com.sdxxtop.guardianapp.presenter.SafeStaffDetail2Presenter;
import com.sdxxtop.guardianapp.presenter.contract.SafeStaffDetail2Contract;
import com.sdxxtop.guardianapp.ui.adapter.SafeStaffDetail2Adapter;
import com.sdxxtop.guardianapp.ui.widget.GERTimeSelectView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class SafeStaffDetail2Activity extends BaseMvpActivity<SafeStaffDetail2Presenter> implements SafeStaffDetail2Contract.IView {

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

    private int partId;
    private SafeStaffDetail2Adapter adapter;

    private List<EnterpriseSecurityBean.SignData> data = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_safe_staff_detail2;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.enterpriseUserdetails(partId);
    }

    @Override
    protected void initView() {
        super.initView();
        int type = getIntent().getIntExtra("type", 0);
        String titleValue = getIntent().getStringExtra("title");
        partId = getIntent().getIntExtra("partId", 0);
        if (type == 1) {  // 网格员
            title.setTitleValue("上报事件");
            tvName.setVisibility(View.GONE);
            gertsvView.setVisibility(View.VISIBLE);
            llEventLayout.setVisibility(View.VISIBLE);
            llDetailLayout.setVisibility(View.GONE);
        } else if (type == 2) {  // 企业
            title.setTitleValue("安全员详情");
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(titleValue);
            gertsvView.setVisibility(View.GONE);
            llEventLayout.setVisibility(View.GONE);
            llDetailLayout.setVisibility(View.VISIBLE);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SafeStaffDetail2Adapter(R.layout.item_sfae_staff_view, null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showData(EnterpriseUserdetailsBean bean) {
        adapter.replaceData(bean.getUserinfo());
    }
}
