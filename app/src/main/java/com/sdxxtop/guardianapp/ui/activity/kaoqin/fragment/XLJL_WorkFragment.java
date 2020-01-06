package com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.DashLineView;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter.AverAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author :  lwb
 * Date: 2020/1/5
 * Desc: XLJL = 巡逻距离
 */
public class XLJL_WorkFragment extends BaseFragment {
    @BindView(R.id.average_recycler)
    RecyclerView averageRecycler;
    @BindView(R.id.average_weight_view)
    View weightView;
    @BindView(R.id.average_text)
    TextView averageText;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_xljl;
    }

    @Override
    protected void initView() {
        weightView.setLayoutParams(new LinearLayout.LayoutParams(0, DashLineView.dp2px(getContext(), 30), 9));
        averageText.setText("平均" + 9 + "小时");

        AverAdapter adapter = new AverAdapter();
        averageRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        averageRecycler.setAdapter(adapter);
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        adapter.replaceData(list);
    }
}
