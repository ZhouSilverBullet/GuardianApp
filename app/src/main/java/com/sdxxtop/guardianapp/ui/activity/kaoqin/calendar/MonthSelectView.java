package com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdxxtop.guardianapp.R;

import java.util.Calendar;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2020/1/6
 * Desc:
 */
public class MonthSelectView extends LinearLayout {

    private Context mContext;
    private View view;
    private TextView tvData;
    private ImageView tvLeft;
    private ImageView tvRight;
    private int month = 12;
    private int year = 2020;

    private int currentYear;
    private int currentMonth;

    public MonthSelectView(Context context) {
        this(context, null);
    }

    public MonthSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        month = calendar.get(Calendar.MONTH) + 1;
        currentMonth = month;
        tvData.setText(year + "年" + month + "月");

        tvLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (month > 1) {
                    month -= 1;
                } else {
                    year -= 1;
                    month = 12;
                }
                if (mListener != null) {
                    mListener.onMonthChanged(year + "-" + month);
                }
                tvData.setText(year + "年" + month + "月");
                Toast.makeText(mContext, year + "-" + month, Toast.LENGTH_SHORT).show();
            }
        });
        tvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (month < 12) {
                    month += 1;
                } else {
                    year += 1;
                    month = 1;
                }

                if (mListener != null) {
                    mListener.onMonthChanged(year + "-" + month);
                }
                tvData.setText(year + "年" + month + "月");
                Toast.makeText(mContext, year + "-" + month, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private OnMonthChangeListener mListener;

    public interface OnMonthChangeListener {
        void onMonthChanged(String month);
    }

    public void setOnMonthChageListener(OnMonthChangeListener listener) {
        this.mListener = listener;
    }
}
