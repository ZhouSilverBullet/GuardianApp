package com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.utils.Date2Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/12/31
 * Desc:
 */
public class MyAssignCalendarView extends LinearLayout implements CalendarView.OnCalendarRangeSelectListener {

    private Context mContext;
    private Dialog dialog;
    private View view;
    public String currentDate;  //默认当天
    private TextView timeStartSelect;
    private TextView timeEndSelect;

    public MyAssignCalendarView(Context context) {
        this(context, null);
    }

    public MyAssignCalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyAssignCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.item_only_tx, this, true);
        this.mContext = context;
        initView();
    }

    private void initView() {
        timeStartSelect = view.findViewById(R.id.timeStartSelect);
        timeEndSelect = view.findViewById(R.id.timeEndSelect);

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(System.currentTimeMillis());
        currentDate = format.format(date);
        timeStartSelect.setText(currentDate);
        timeEndSelect.setText(currentDate);


        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog == null) {
                    showBottomDialog();
                }
                dialog.show();
            }
        });
    }

    private void showBottomDialog() {
        //1、使用Dialog、设置style
        dialog = new Dialog(mContext, R.style.MyDialogStyleBottom);
        //2、设置布局
        View view = View.inflate(mContext, R.layout.dialog_custom_layout_calendar, null);

        initCalendarView(view);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        DisplayMetrics outMetrics = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (outMetrics.heightPixels) / 2);
        dialog.show();
    }

    private void initCalendarView(View view) {
        CalendarView mCalendarView = view.findViewById(R.id.calendarView);
        TextView tvData = view.findViewById(R.id.tv_data);
        tvData.setText(Date2Util.getZeroTime(mCalendarView.getCurYear()) + "-" + Date2Util.getZeroTime(mCalendarView.getCurMonth()));
        TextView tvCommit = view.findViewById(R.id.tvCommit);
        tvCommit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Calendar> calendars = mCalendarView.getSelectCalendarRange();
                if (calendars == null || calendars.size() == 0) {
                    return;
                }
                for (Calendar c : calendars) {
                    Log.e("checkData = ", "" + c.getYear() + "-" + c.getMonth() + "-" + c.getDay());
                }
                if (mListener != null) {
                    mListener.selected(calendars);
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        mCalendarView.setOnMonthChangeListener((year, month) -> tvData.setText("" + year + "-" + Date2Util.getZeroTime(month)));
        mCalendarView.setOnCalendarRangeSelectListener(this);
        mCalendarView.setSelectRange(1, 31);
    }


    public interface OnDataChooseListener {
        void selected(List<Calendar> data);
    }

    private OnDataChooseListener mListener;

    public void setOnDataChoose(OnDataChooseListener listener) {
        this.mListener = listener;
    }

    /**
     * 范围选择超出范围越界
     *
     * @param calendar calendar
     */
    @Override
    public void onCalendarSelectOutOfRange(Calendar calendar) {

    }

    /**
     * 选择范围超出范围
     *
     * @param calendar        calendar
     * @param isOutOfMinRange 是否小于最小范围，否则为最大范围
     */
    @Override
    public void onSelectOutOfRange(Calendar calendar, boolean isOutOfMinRange) {
        Toast.makeText(mContext, "最多可选31天", Toast.LENGTH_SHORT).show();
    }

    /**
     * 日期选择事件
     *
     * @param calendar calendar
     * @param isEnd    是否结束
     */
    @Override
    public void onCalendarRangeSelect(Calendar calendar, boolean isEnd) {
        if (!isEnd) {
            timeStartSelect.setText(calendar.getYear() + "/" + Date2Util.getZeroTime(calendar.getMonth()) + "/" + Date2Util.getZeroTime(calendar.getDay()));
            timeEndSelect.setText("");
        } else {
            timeEndSelect.setText(calendar.getYear() + "/" + Date2Util.getZeroTime(calendar.getMonth()) + "/" + Date2Util.getZeroTime(calendar.getDay()));
        }
    }

}
