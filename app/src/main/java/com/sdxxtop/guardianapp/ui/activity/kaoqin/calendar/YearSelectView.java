package com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.sdxxtop.guardianapp.R;

import java.util.Calendar;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2020/1/6
 * Desc:
 */
public class YearSelectView extends LinearLayout {

    private Context mContext;
    private View view;
    private TextView tvData;
    private ImageView tvLeft;
    private ImageView tvRight;
    public int year = 2020;

    private int currentYear;

    private MaterialCalendarView mCalendarView;

    public YearSelectView(Context context) {
        this(context, null);
    }

    public YearSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YearSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.cv_month_select, this, true);
        this.mContext = context;
        initView();
    }

    private void initView() {
        tvData = findViewById(R.id.tvDate);
        tvLeft = findViewById(R.id.tvLeft);
        tvRight = findViewById(R.id.tvRight);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        currentYear = year;
        tvData.setText(year + "年");

        tvLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                year -= 1;

                if (mCalendarView != null) {
                    mCalendarView.goToPrevious();
                }
                if (mListener != null) {
                    mListener.onMonthChanged(year);
                }
                tvData.setText(year + "年");
            }
        });
        tvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (year == currentYear) return;
                year += 1;

                if (mCalendarView != null) {
                    mCalendarView.goToNext();
                }
                if (mListener != null) {
                    mListener.onMonthChanged(year);
                }
                tvData.setText(year + "年");
            }
        });
    }

    public void setOnYearChange(int y, int m) {
        year = y;
        tvData.setText(year + "年");
        if (mListener != null) {
            mListener.onMonthChanged(year);
        }
    }

    private OnMonthChangeListener mListener;

    public void bindCalendar(MaterialCalendarView calendarView) {
        this.mCalendarView = calendarView;
    }

    public interface OnMonthChangeListener {
        void onMonthChanged(int year);
    }

    public void setOnMonthChageListener(OnMonthChangeListener listener) {
        this.mListener = listener;
    }
}
