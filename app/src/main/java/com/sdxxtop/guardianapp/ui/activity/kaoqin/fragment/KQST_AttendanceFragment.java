package com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment;


import android.graphics.Color;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter.KQST_Adapter;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.MyDayFormatter;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.OutsetEventDecorator;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.TextGrayDecorator;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.TodayTextDecorator;
import com.sdxxtop.guardianapp.ui.widget.decorator.CalendarSelectorDecorator;
import com.sdxxtop.guardianapp.ui.widget.decorator.MaxTodayDecorator;
import com.sdxxtop.guardianapp.utils.Date2Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 考勤视图
 */
public class KQST_AttendanceFragment extends BaseFragment {

    @BindView(R.id.attendance_calendar_view)
    MaterialCalendarView calendarView;
    @BindView(R.id.home_fragment_recycler)
    RecyclerView mRecyclerView;

    private CalendarDay currentCalendarDay;
    private OutsetEventDecorator redPointDecorator;
    private OutsetEventDecorator bluePointDecorator;
    private MyDayFormatter myDayFormatter;

    public KQST_AttendanceFragment() {

    }


    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_kqst;
    }

    @Override
    protected void initView() {
        //隐藏标题，这里需要自定义
        calendarView.setTopbarVisible(false);
        calendarView.state().edit().setMaximumDate(Date2Util.getLastDayOfMonth())
                .commit();
        //选中当前日期
        calendarView.setSelectedDate(Calendar.getInstance().getTime());
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (Date2Util.compareToday2(date)) {
                    if (currentCalendarDay != null) {
                        calendarView.setSelectedDate(currentCalendarDay);
                    }
                    return;
                }
                currentCalendarDay = calendarView.getSelectedDate();
            }
        });

        myDayFormatter = new MyDayFormatter();
        redPointDecorator = new OutsetEventDecorator(Color.RED);
        bluePointDecorator = new OutsetEventDecorator(Color.BLUE);
        calendarView.setDayFormatter(myDayFormatter);
        calendarView.addDecorators(new CalendarSelectorDecorator(getActivity()),
                new TodayTextDecorator(getActivity()),
                new TextGrayDecorator(getActivity()),
                redPointDecorator,
                bluePointDecorator,
                new MaxTodayDecorator(getActivity())
        );

        currentCalendarDay = calendarView.getSelectedDate();

        List<String> list1 = new ArrayList<>();
        list1.add("2020-01-06");
        list1.add("2020-01-04");
        list1.add("2020-01-02");
        list1.add("2020-01-01");
        redPointDecorator.setDates(list1);

        List<String> list2 = new ArrayList<>();
        list2.add("2020-01-05");
        list2.add("2020-01-03");
        bluePointDecorator.setDates(list2);
        calendarView.invalidateDecorators();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        KQST_Adapter adapter = new KQST_Adapter();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        mRecyclerView.setAdapter(adapter);
        adapter.replaceData(list);
    }
}
