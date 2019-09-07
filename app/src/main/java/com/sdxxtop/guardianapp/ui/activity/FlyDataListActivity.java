package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
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
import com.sdxxtop.guardianapp.model.bean.FlyEventListBean;
import com.sdxxtop.guardianapp.presenter.FlyDataListPresenter;
import com.sdxxtop.guardianapp.presenter.contract.FlyDataListContract;
import com.sdxxtop.guardianapp.ui.adapter.FlyDataAdapter;
import com.sdxxtop.guardianapp.utils.Date2Util;
import com.sdxxtop.guardianapp.utils.MyTextWatcher;

import java.util.HashMap;
import java.util.List;
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
    private FlyDataAdapter adapter;
    private FlyDataAdapter searchAdapter;
    private String value = "";

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
    protected void onResume() {
        super.onResume();
        mPresenter.getUavData(value);
        mPresenter.getUavEventData(value);
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
        mTextMonthDay.setText(mYear + "年" + mCalendarView.getCurMonth() + "月");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FlyDataAdapter(R.layout.item_fly_datalist, null);
        recyclerView.setAdapter(adapter);

        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new FlyDataAdapter(R.layout.item_fly_datalist, null);
        recyclerViewSearch.setAdapter(searchAdapter);

        edittext.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tx = s.toString().trim();
                if (!TextUtils.isEmpty(tx)) {
                    mPresenter.getSearchData(tx);
                }
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
        mTextMonthDay.setText(calendar.getYear() + "年" + calendar.getMonth() + "月");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();

        value = Date2Util.getZeroTime(calendar.getYear()) + "-" +
                Date2Util.getZeroTime(calendar.getMonth()) + "-" +
                Date2Util.getZeroTime(calendar.getDay());

        mPresenter.getUavData(value);
        mPresenter.getUavEventData(value);
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

    @Override
    public void setMonthUavData(List<FlyEventListBean.MonthTash> data) {
        if (data != null && data.size() > 0) {
            mCalendarView.clearSchemeDate();
            Map<String, Calendar> map = new HashMap<>();
            for (FlyEventListBean.MonthTash item : data) {
                int y = Date2Util.getDateInt(item.add_date, 0);
                int m = Date2Util.getDateInt(item.add_date, 1);
                int d = Date2Util.getDateInt(item.add_date, 2);
                map.put(getSchemeCalendar(y, m, d, 0xFFedc56d, "飞").toString(),
                        getSchemeCalendar(y, m, d, 0xFFedc56d, "飞"));
            }
            mCalendarView.setSchemeDate(map);
        }
    }

    @Override
    public void setDayUavList(List<FlyEventListBean.DayTash> data) {
        adapter.replaceData(data);
    }

    @Override
    public void setSearchData(List<FlyEventListBean.DayTash> data) {
        searchAdapter.replaceData(data);
    }
}
