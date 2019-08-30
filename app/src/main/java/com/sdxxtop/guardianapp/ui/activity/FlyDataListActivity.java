package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.FlyDataListPresenter;
import com.sdxxtop.guardianapp.presenter.contract.FlyDataListContract;
import com.sdxxtop.guardianapp.ui.adapter.FlyDataAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FlyDataListActivity extends BaseMvpActivity<FlyDataListPresenter> implements FlyDataListContract.IView, CalendarView.OnYearChangeListener,
        CalendarView.OnCalendarSelectListener {

    TextView mTextMonthDay;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;

    SmartRefreshLayout mSmartRefresh;

    private int mYear;
    CalendarLayout mCalendarLayout;
    private RecyclerView recyclerView;

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_fly_data_list;
    }


    @Override
    public void initData() {
        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();
        Map<String, Calendar> map = new HashMap<>();
        for (int y = 1997; y < 2082; y++) {
            for (int m = 1; m <= 12; m++) {
                map.put(getSchemeCalendar(y, m, 3, 0xFFedc56d, "飞").toString(),
                        getSchemeCalendar(y, m, 3, 0xFFedc56d, "飞"));
                map.put(getSchemeCalendar(y, m, 6, 0xFFedc56d, "飞").toString(),
                        getSchemeCalendar(y, m, 6, 0xFFedc56d, "飞"));
                map.put(getSchemeCalendar(y, m, 9, 0xFFedc56d, "飞").toString(),
                        getSchemeCalendar(y, m, 9, 0xFFedc56d, "飞"));
                map.put(getSchemeCalendar(y, m, 13, 0xFFedc56d, "飞").toString(),
                        getSchemeCalendar(y, m, 13, 0xFFedc56d, "飞"));
                map.put(getSchemeCalendar(y, m, 14, 0xFFedc56d, "飞").toString(),
                        getSchemeCalendar(y, m, 14, 0xFFedc56d, "飞"));
                map.put(getSchemeCalendar(y, m, 15, 0xFFedc56d, "飞").toString(),
                        getSchemeCalendar(y, m, 15, 0xFFedc56d, "飞"));
                map.put(getSchemeCalendar(y, m, 18, 0xFFedc56d, "飞").toString(),
                        getSchemeCalendar(y, m, 18, 0xFFedc56d, "飞"));
                map.put(getSchemeCalendar(y, m, 25, 0xFFedc56d, "飞").toString(),
                        getSchemeCalendar(y, m, 25, 0xFFedc56d, "飞"));
                map.put(getSchemeCalendar(y, m, 27, 0xFFedc56d, "飞").toString(),
                        getSchemeCalendar(y, m, 27, 0xFFedc56d, "飞"));
            }
        }
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(map);
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

    @Override
    public void initView() {
        mTextMonthDay = findViewById(R.id.tv_month_day);
        mTextYear = findViewById(R.id.tv_year);
        mTextLunar = findViewById(R.id.tv_lunar);
        mRelativeTool = findViewById(R.id.rl_tool);
        mCalendarView = findViewById(R.id.calendarView);
        mTextCurrentDay = findViewById(R.id.tv_current_day);
        mSmartRefresh = findViewById(R.id.smart_refresh);

        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCalendarView != null) {
                    mCalendarView.closeYearSelectLayout();
                }
                mCalendarView.scrollToCurrent(true);
            }
        });
        mCalendarLayout = findViewById(R.id.calendarLayout);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> strings = new ArrayList<>();
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        recyclerView.setAdapter(new FlyDataAdapter(R.layout.item_fly_datalist, strings));

        mSmartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mSmartRefresh.finishLoadMore();
                mSmartRefresh.finishRefresh();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mSmartRefresh.finishLoadMore();
                mSmartRefresh.finishRefresh();
            }
        });

        findViewById(R.id.iv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FlyDataListActivity.this, FlyEventReportActivity.class));
            }
        });
        findViewById(R.id.iv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.iv_year_seclect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
                    return;
                }
                mTextMonthDay.setText(String.valueOf(mYear));
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
    }
}
