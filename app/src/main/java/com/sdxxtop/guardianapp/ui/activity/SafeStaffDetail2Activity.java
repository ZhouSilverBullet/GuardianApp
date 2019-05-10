package com.sdxxtop.guardianapp.ui.activity;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.SafeStaffDetail2Presenter;
import com.sdxxtop.guardianapp.presenter.contract.SafeStaffDetailContract;
import com.sdxxtop.guardianapp.ui.adapter.SafeStaffDetailAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class SafeStaffDetail2Activity extends BaseMvpActivity<SafeStaffDetail2Presenter> implements SafeStaffDetailContract.IView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

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
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("pp");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SafeStaffDetailAdapter adapter = new SafeStaffDetailAdapter(R.layout.item_sfae_staff_view,data,2);
        recyclerView.setAdapter(adapter);
    }
}
