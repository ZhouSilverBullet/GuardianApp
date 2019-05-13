package com.sdxxtop.guardianapp.ui.activity;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.GACPPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GACPContract;
import com.sdxxtop.guardianapp.ui.adapter.GACEDetailAdapter;
import com.sdxxtop.guardianapp.ui.widget.CustomAreaSelectView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;
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

    @Override
    protected int getLayout() {
        return R.layout.activity_gacpatrol_detail;
    }

    @Override
    protected void initInject() {

    }

    @Override
    public void showError(String error) {

    }

    @OnClick(R.id.casv_view)
    public void onViewClicked() {
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
//        new AreaSelectPopWindow(GACPatrolDetailActivity.this, casvView.llAreaLayout, data, casvView.tvArea);
    }

    @Override
    protected void initView() {
        super.initView();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("环保局:356" + (i + 1));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GACEDetailAdapter adapter = new GACEDetailAdapter(R.layout.item_gace_view, list,1);
        recyclerView.setAdapter(adapter);
    }
}
