package com.sdxxtop.guardianapp.ui.activity;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.PELPresenter;
import com.sdxxtop.guardianapp.presenter.contract.PELContract;
import com.sdxxtop.guardianapp.ui.adapter.PartEventListAdapter;
import com.sdxxtop.guardianapp.ui.pop.AreaSelectPopWindow;
import com.sdxxtop.guardianapp.ui.widget.CustomAreaSelectView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class PartEventListActivity extends BaseMvpActivity<PELPresenter> implements PELContract.IView {

    @BindView(R.id.title)
    TitleView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.casv_view)
    CustomAreaSelectView casvView;

    @Override
    protected int getLayout() {
        return R.layout.activity_part_event_list;
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
        title.setTitleValue(getIntent().getStringExtra("title") + "事件");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("环保局:356" + (i + 1));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PartEventListAdapter(R.layout.item_part_event, list));

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
        new AreaSelectPopWindow(PartEventListActivity.this, casvView.llAreaLayout, data, casvView.tvArea);
    }

}
