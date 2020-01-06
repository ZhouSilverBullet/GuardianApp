package com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment;


import com.haibin.calendarview.Calendar;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter.KQMX_Adapter;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.MyAssessCalendarView;

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
public class KQMX_AttendanceFragment extends BaseFragment {

    @BindView(R.id.mcv_view)
    MyAssessCalendarView mcvView;
    @BindView(R.id.attendance_detail_recycler)
    RecyclerView recyclerView;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_kqmx;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        KQMX_Adapter adapter = new KQMX_Adapter();
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            dataList.add("laji");
        }
        adapter.replaceData(dataList);
        recyclerView.setAdapter(adapter);

        mcvView.setOnDataChoose(new MyAssessCalendarView.OnDataChooseListener() {
            @Override
            public void selected(List<Calendar> data) {
                dataList.clear();
                for (Calendar item : data) {
                    dataList.add("" + item.getYear() + "-" + item.getMonth() + "-" + item.getDay() + item.getWeek());
                }

                adapter.replaceData(dataList);
            }
        });

    }


}
