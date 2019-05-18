package com.sdxxtop.guardianapp.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author :  lwb
 * Date: 2019/5/9
 * Desc:
 */
public class GERTimeSelectView extends LinearLayout {
    @BindView(R.id.tv_start_time)
    public TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    public TextView tvEndTime;

    private TimePickerView pvLeftTime;
    private TimePickerView pvRightTime;

    private String startTime = "";
    private String endTime = "";

    private OnTimeChooseListener mLisenter;

    public GERTimeSelectView(Context context) {
        this(context, null);
    }

    public GERTimeSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GERTimeSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_time_select, this, true);
        ButterKnife.bind(this);
        initLeftTimePicker();
        initRightTimePicker();
    }

    @OnClick({R.id.tv_start_time, R.id.tv_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:
                if (pvLeftTime != null) {
                    pvLeftTime.setDate(getData(startTime));
                    pvLeftTime.show();
                }
                break;
            case R.id.tv_end_time:
                if (pvRightTime != null) {
                    pvLeftTime.setDate(getData(endTime));
                    pvRightTime.show();
                }
                break;
        }
    }

    private Calendar getData(String time) {
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = format.parse(time);
            instance.setTime(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return instance;
    }

    private void initLeftTimePicker() {//Dialog 模式下，在底部弹出
        pvLeftTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                startTime = getTime(date);
                if (!TextUtils.isEmpty(endTime)) {  // 已选择结束时间
                    if (compare_date(startTime, endTime)) {  // 时间规则可用
                        if (mLisenter == null) return;
                        mLisenter.onTimeSelect(getFormatTime(startTime), getFormatTime(endTime));
                    }
                } else {
                    tvStartTime.setText(startTime);
                    tvStartTime.setTextColor(getResources().getColor(R.color.black));
                }
            }
        }).setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
            @Override
            public void onTimeSelectChanged(Date date) {
                Log.i("pvLeftTime", "onTimeSelectChanged");
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .build();

        Dialog mDialog = pvLeftTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvLeftTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }

    private void initRightTimePicker() {//Dialog 模式下，在底部弹出
        pvRightTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                endTime = getTime(date);
                if (!TextUtils.isEmpty(startTime)) {  // 开始时间没选择  直接展示结束时间
                    if (compare_date(startTime, endTime)) {
                        if (mLisenter == null) return;
                        mLisenter.onTimeSelect(getFormatTime(startTime), getFormatTime(endTime));
                    }
                } else {
                    tvEndTime.setText(endTime);
                    tvEndTime.setTextColor(getResources().getColor(R.color.black));
                }
            }
        }).setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
            @Override
            public void onTimeSelectChanged(Date date) {
                Log.i("pvLeftTime", "onTimeSelectChanged");
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .build();


        Dialog mDialog = pvRightTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvRightTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(date);
    }

    public boolean compare_date(String date1, String date2) {
        String startTime = date1;
        String endTime = date2;

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date start = null;
        try {
            start = df.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        Date end = null;
        try {
            end = df.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        if (start.getTime() > end.getTime()) {
            UIUtils.showToast("开始时间不能大于结束时间");
            return false;
        }
        tvStartTime.setText(date1);
        tvEndTime.setText(date2);
        tvStartTime.setTextColor(getResources().getColor(R.color.black));
        tvEndTime.setTextColor(getResources().getColor(R.color.black));

        return true;
    }

    public String getFormatTime(String time) {
        return time.replaceAll("/", "-") + " 00:00:00";
    }

    public String getSelectTime(int type) {
        if (type == 1) {
            return startTime;
        } else {
            return endTime;
        }
    }

    public void setTime(String start_time, String end_time) {
        this.startTime = start_time;
        this.endTime = end_time;
    }

    public interface OnTimeChooseListener {
        void onTimeSelect(String startTime, String endTime);
    }

    public void setOnTimeSelectListener(OnTimeChooseListener listener) {
        this.mLisenter = listener;
    }

}
