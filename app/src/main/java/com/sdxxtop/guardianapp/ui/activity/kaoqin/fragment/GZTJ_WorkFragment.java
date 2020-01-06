package com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter.GZTJ_Adapter;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.MonthSelectView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author :  lwb
 * Date: 2020/1/5
 * Desc: GZTJ = 工作统计
 */
public class GZTJ_WorkFragment extends BaseFragment {

    @BindView(R.id.msv_view)
    MonthSelectView msvView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_gztj;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        GZTJ_Adapter adapter = new GZTJ_Adapter();
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        recyclerView.setAdapter(adapter);
        adapter.replaceData(list);
    }
}
