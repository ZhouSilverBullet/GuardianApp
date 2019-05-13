package com.sdxxtop.guardianapp.ui.activity;

import android.view.View;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.GACEPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GCRContract;
import com.sdxxtop.guardianapp.ui.adapter.GACEDetailAdapter;
import com.sdxxtop.guardianapp.ui.widget.CustomAreaSelectView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * GridAndCompanyEventDetailActivity
 */
public class GACEventDetailActivity extends BaseMvpActivity<GACEPresenter> implements GCRContract.IView {

    @BindView(R.id.title)
    TitleView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.casv_left)
    CustomAreaSelectView casvLeft;
    @BindView(R.id.casv_right)
    CustomAreaSelectView casvRight;

    @Override
    protected int getLayout() {
        return R.layout.activity_gacevent_detail;
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

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GACEDetailAdapter adapter = new GACEDetailAdapter(R.layout.item_gace_view, list,2);
        recyclerView.setAdapter(adapter);



        casvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new AreaSelectPopWindow(GACEventDetailActivity.this,casvLeft.llAreaLayout,data,casvLeft.tvArea);
            }
        });

        casvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new AreaSelectPopWindow(GACEventDetailActivity.this,casvRight.llAreaLayout,data,casvRight.tvArea);
            }
        });
    }

}
