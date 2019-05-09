package com.sdxxtop.guardianapp.ui.widget.timerselect;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.contrarywind.view.WheelView;
import com.sdxxtop.guardianapp.R;

import java.util.Calendar;
import java.util.List;



/**
 * @author :  lwb
 * Date: 2018/10/26
 * Desc:
 */

public class BottomDialogView extends Dialog implements View.OnClickListener {

    private Context context;
    private MinuteAdapter minuteAdapter;
    private HourAdapter hourAdapter;
    private SelectTimeAdapter selectTimeAdapter;
    private WheelView timeWheel, hourWheel, minuteWheel, lineWheel;
    private onConfirmClick mListener;
    private String mHour, mMinute;
    private LineAdapter lineAdapter;
    private View loadProgress;
    private Calendar mCurrentCalendar;

    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public BottomDialogView(Context context, onConfirmClick listener) {
        super(context, R.style.MyDialogStyleBottom);
        this.context = context;
        this.mListener = listener;
    }

    public BottomDialogView(Context context) {
        super(context, R.style.MyDialogStyleBottom);
        this.context = context;
    }

    public void setCurrentDate(Calendar calendar) {
        mCurrentCalendar = calendar;
    }

    public Calendar getCurrentCalendar() {
        if (mCurrentCalendar == null) {
            mCurrentCalendar = Calendar.getInstance();
        }
        return mCurrentCalendar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custontime_select);//这行一定要写在前面
        setCancelable(true);//点击外部不可dismiss
        initView(); /**** 初始化控件 *****/
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }

    private void initView() {

        selectTimeAdapter = new SelectTimeAdapter();  // 日期
        hourAdapter = new HourAdapter();  // 小时  24进制
        minuteAdapter = new MinuteAdapter();  // 分
//        inflate = LayoutInflater.from(context).inflate(R.layout.dialog_custontime_select, null);
        timeWheel = (WheelView) findViewById(R.id.time_wheel);
        hourWheel = (WheelView) findViewById(R.id.hour_wheel);
        minuteWheel = (WheelView) findViewById(R.id.minute_wheel);
        lineWheel = (WheelView) findViewById(R.id.line_wheel);
        loadProgress = findViewById(R.id.load_progress);
        lineAdapter = new LineAdapter();
        selectTimeAdapter.setLoadDataFinishListener(new SelectTimeAdapter.LoadDataFinishListener() {
            @Override
            public void onLoadFinish() {
                if (timeWheel != null) {
                    timeWheel.setAdapter(selectTimeAdapter);
                    hourWheel.setAdapter(hourAdapter);
                    minuteWheel.setAdapter(minuteAdapter);
                    lineWheel.setAdapter(lineAdapter);
                    timeWheel.requestLayout();

                    loadProgress.setVisibility(View.GONE);

                    handleCurrentTime(getCurrentCalendar());
//                    handleCurrent();
                }
            }
        });


        findViewById(R.id.tv_cancel).setOnClickListener(this);// 取消
        findViewById(R.id.tv_finish).setOnClickListener(this);// 确定

        lineWheel.setOnTouchListener(new View.OnTouchListener() {   // ":"设置不可滑动
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        Calendar instance = Calendar.getInstance();
//        int YY = instance.get(Calendar.YEAR);
//        int MM = instance.get(Calendar.MONTH) + 1;
//        int DD = instance.get(Calendar.DATE);
        //当前时：HOUR_OF_DAY-24小时制；HOUR-12小时制
        int HH = instance.get(Calendar.HOUR_OF_DAY);
        int NN = instance.get(Calendar.MINUTE);

        mHour = HH < 10 ? "0" + HH : "" + HH;
        mMinute = NN < 10 ? "0" + NN : "" + NN;

        int i = selectTimeAdapter.indexOf("今天");
        timeWheel.setCurrentItem(i);
//        int i1 = hourAdapter.indexOf("" + mHour);
//        int i2 = minuteAdapter.indexOf("" + mMinute);
//        hourWheel.setCurrentItem(i1);
//        minuteWheel.setCurrentItem(i2);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:  // 取消
                this.dismiss();
                break;
            case R.id.tv_finish:  // 确认
                int currentitem = timeWheel.getCurrentItem();
                int currentItem2 = hourWheel.getCurrentItem();
                int currentItem3 = minuteWheel.getCurrentItem();
                String item = selectTimeAdapter.getDate(currentitem);
                String item1 = hourAdapter.getItem(currentItem2);
                String item2 = minuteAdapter.getItem(currentItem3);
                Log.e("时间==", "" + item + "指针==" + currentitem);
                Log.e("时间==", "" + item1 + "指针==" + currentItem2);
                Log.e("时间==", "" + item2 + "指针==" + currentItem3);
                String allTime = item + " " + item1 + ":" + item2;
                if (mListener != null) {
                    mListener.onClick(allTime);
                }
                this.dismiss();
                break;
        }
    }


    public interface onConfirmClick {
        void onClick(String time);
    }

    @Override
    public void show() {
        super.show();

    }

    /**
     * 选择今天
     */
    public void handleTodayCurrent() {
        if (hourAdapter == null || minuteAdapter == null) {
            return;
        }
        if (hourWheel == null || minuteWheel == null) {
            return;
        }
        int i = selectTimeAdapter.indexOf("今天");
        timeWheel.setCurrentItem(i);
        int hourIndex = hourAdapter.indexOf(mHour);
        int minuteIndex = minuteAdapter.indexOf(mMinute);
        hourWheel.setCurrentItem(hourIndex);
        minuteWheel.setCurrentItem(minuteIndex);
    }

    public void handleCurrentTime(Calendar instance) {
        if (selectTimeAdapter == null) {
            return;
        }

        List<SelectTimeAdapter.DayBean> list = selectTimeAdapter.getList();
        if (list == null) {
            return;
        }
        int YY = instance.get(Calendar.YEAR);
        int MM = instance.get(Calendar.MONTH) + 1;
        int DD = instance.get(Calendar.DATE);
        //当前时：HOUR_OF_DAY-24小时制；HOUR-12小时制
        int HH = instance.get(Calendar.HOUR_OF_DAY);
        int NN = instance.get(Calendar.MINUTE);

        String hour = HH < 10 ? "0" + HH : "" + HH;
        String minute = NN < 10 ? "0" + NN : "" + NN;

        String month = MM < 10 ? "0" + MM : "" + MM;
        String day = DD < 10 ? "0" + DD : "" + DD;
        String year = YY + "-" + month + "-" + day;

        int hourIndex = hourAdapter.indexOf(hour);
        int minuteIndex = minuteAdapter.indexOf(minute);
        hourWheel.setCurrentItem(hourIndex);
        minuteWheel.setCurrentItem(minuteIndex);
        int index = -1;
        for (SelectTimeAdapter.DayBean dayBean : list) {
            String formatDate = dayBean.getFormatDate();
            if (!TextUtils.isEmpty(formatDate) && formatDate.equals(year)) {
                index = list.indexOf(dayBean);
            }
        }

        if (index == -1) {
            index = selectTimeAdapter.indexOf("今天");
        }

        timeWheel.setCurrentItem(index);
    }

    /**
     * 设置选中
     *
     * @param hour   选中的小时
     * @param minute 选中的分钟
     */
    public void setDate(String hour, String minute) {
        this.mHour = hour;
        this.mMinute = minute;
    }

    public void setConfirmClickListener(onConfirmClick listener) {
        mListener = listener;
    }
}
