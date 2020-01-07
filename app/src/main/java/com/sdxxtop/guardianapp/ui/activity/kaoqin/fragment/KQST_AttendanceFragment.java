package com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment;


import android.graphics.Color;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpFragment;
import com.sdxxtop.guardianapp.model.bean.KqstDayBean;
import com.sdxxtop.guardianapp.model.bean.KqstMonthBean;
import com.sdxxtop.guardianapp.presenter.KQST_fmPresenter;
import com.sdxxtop.guardianapp.presenter.contract.KQST_fmContract;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter.KQST_Adapter;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.MonthSelectView;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.MyDayFormatter;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.OutsetEventDecorator;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.TextGrayDecorator;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.TodayTextDecorator;
import com.sdxxtop.guardianapp.ui.widget.decorator.CalendarSelectorDecorator;
import com.sdxxtop.guardianapp.ui.widget.decorator.MaxTodayDecorator;
import com.sdxxtop.guardianapp.utils.Date2Util;

import java.text.SimpleDateFormat;
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
public class KQST_AttendanceFragment extends BaseMvpFragment<KQST_fmPresenter> implements KQST_fmContract.IView, OnDateSelectedListener,
        OnMonthChangedListener {

    @BindView(R.id.attendance_calendar_view)
    MaterialCalendarView calendarView;
    @BindView(R.id.home_fragment_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.msv_view)
    MonthSelectView msvView;
    @BindView(R.id.tvClockName)
    TextView tvClockName;
    @BindView(R.id.tvClockDesc)
    TextView tvClockDesc;

    private CalendarDay currentCalendarDay;
    private OutsetEventDecorator redPointDecorator;
    private OutsetEventDecorator bluePointDecorator;
    private MyDayFormatter myDayFormatter;
    private KQST_Adapter adapter;

    @Override
    protected void initData() {
        loadMonthInfo(msvView.year, msvView.month);
        mPresenter.loadDayInfo(Date2Util.getToday());
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
        calendarView.setOnDateChangedListener(this);
        calendarView.setOnMonthChangedListener(this);

        currentCalendarDay = calendarView.getSelectedDate();


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new KQST_Adapter();
        mRecyclerView.setAdapter(adapter);

        msvView.setOnMonthChageListener((year, month) -> loadMonthInfo(year, month));
        msvView.bindCalendar(calendarView);
    }

    private void loadMonthInfo(int year, int month) {
        mPresenter.loadKqstInfo(year, month);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    /**
     * 日历视图的数据展示
     *
     * @param bean
     */
    @Override
    public void showMonthData(KqstMonthBean bean) {
        redPointDecorator.setDates(bean.abnormal);
        List<String> redDate = new ArrayList<>();
        for (String item : bean.sign_data) {
            if (!bean.abnormal.contains(item)) {
                redDate.add(item);
            }
        }
        bluePointDecorator.setDates(redDate);
        calendarView.invalidateDecorators();
    }

    /**
     * 点击日期刷新数据回调
     *
     * @param bean
     */
    @Override
    public void showDayInfo(KqstDayBean bean) {
        tvClockName.setText(bean.data);
        tvClockDesc.setText("今日打卡"+bean.sign_count+"次，工时共计"+bean.treno_time);
        if (bean.sign_log != null) {
            adapter.replaceData(bean.sign_log);
        }
    }

    /**
     * 切换日期回调
     *
     * @param widget
     * @param date
     * @param selected
     */
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        mPresenter.loadDayInfo(new SimpleDateFormat("yyyy-MM-dd").format(date.getDate()));
    }

    /**
     * 切换日历月份的回调
     *
     * @param widget
     * @param date
     */
    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        msvView.setOnMonthChange(date.getYear(), date.getMonth() + 1);
    }
}
