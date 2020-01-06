package com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter.KQTJ_Adapter;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.MonthSelectView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author :  lwb
 * Date: 2019/12/31
 * Desc:
 */
public class KQTJ_AttendanceFragment extends BaseFragment {
    @BindView(R.id.msv_view)
    MonthSelectView msvView;
    @BindView(R.id.attendance_statistical_recycler)
    RecyclerView recyclerView;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_kqtj;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        KQTJ_Adapter adapter = new KQTJ_Adapter();
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            dataList.add("" + i);
        }
        recyclerView.setAdapter(adapter);
        adapter.replaceData(dataList);
    }
}
