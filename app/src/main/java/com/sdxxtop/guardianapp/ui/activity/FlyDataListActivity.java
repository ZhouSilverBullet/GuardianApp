package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
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
import butterknife.BindView;
import butterknife.OnClick;

public class FlyDataListActivity extends BaseMvpActivity<FlyDataListPresenter> implements FlyDataListContract.IView, CalendarView.OnYearChangeListener,
        CalendarView.OnCalendarSelectListener {

    @BindView(R.id.tv_month_day)
    TextView mTextMonthDay;
    @BindView(R.id.tv_year)
    TextView mTextYear;
    @BindView(R.id.tv_lunar)
    TextView mTextLunar;
    @BindView(R.id.ib_calendar)
    ImageView ibCalendar;
    @BindView(R.id.tv_current_day)
    TextView mTextCurrentDay;
    @BindView(R.id.fl_current)
    FrameLayout flCurrent;
    @BindView(R.id.iv_year_seclect)
    ImageView ivYearSeclect;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.rl_tool)
    RelativeLayout mRelativeTool;
    @BindView(R.id.calendarView)
    CalendarView mCalendarView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    @BindView(R.id.calendarLayout)
    CalendarLayout mCalendarLayout;
    @BindView(R.id.ll_caldenar_layout)
    LinearLayout llCaldenarLayout;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.recyclerView_search)
    RecyclerView recyclerViewSearch;
    @BindView(R.id.ll_search_layout)
    RelativeLayout llSearchLayout;
    @BindView(R.id.edittext)
    EditText edittext;

    private int mYear;

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
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> strings = new ArrayList<>();
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        recyclerView.setAdapter(new FlyDataAdapter(R.layout.item_fly_datalist, strings));
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


    @OnClick({R.id.tv_month_day, R.id.fl_current, R.id.tv_cancel, R.id.iv_add, R.id.iv_search, R.id.iv_year_seclect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_month_day:  // 返回
                finish();
                break;
            case R.id.iv_add:  // 添加
                startActivity(new Intent(FlyDataListActivity.this, FlyEventReportActivity.class));
                break;
            case R.id.iv_search:   // 搜索
                edittext.setText("");
                llCaldenarLayout.setVisibility(View.GONE);
                llSearchLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_cancel:   // 取消搜索
                hideKeyboard(view);
                llCaldenarLayout.setVisibility(View.VISIBLE);
                llSearchLayout.setVisibility(View.GONE);
                break;
            case R.id.fl_current:   // 返回当前日期
                if (mCalendarView != null) {
                    mCalendarView.scrollToCurrent(true);
                    mCalendarView.closeYearSelectLayout();
                }
                break;
            case R.id.iv_year_seclect:   // 选择年
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
//                    return;
                }
                mTextMonthDay.setText(String.valueOf(mYear));
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                break;
        }
    }
}
