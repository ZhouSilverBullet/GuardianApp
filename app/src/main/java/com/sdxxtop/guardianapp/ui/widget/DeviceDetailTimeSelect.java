package com.sdxxtop.guardianapp.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.ui.widget.timePicker.HomeTimeSelectView;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author :  lwb
 * Date: 2019/7/4
 * Desc:
 */
public class DeviceDetailTimeSelect extends LinearLayout {
    @BindView(R.id.iv_date_icon)
    ImageView ivDateIcon;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.rl_data_layout)
    RelativeLayout rlDataLayout;
    @BindView(R.id.iv_time_icon)
    ImageView ivTimeIcon;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rl_time_layout)
    RelativeLayout rlTimeLayout;

    private TimePickerView pvLeftTime;
    private TimePickerView pvTime;
    private HomeTimeSelectView homeTimeSelectView;

    public DeviceDetailTimeSelect(Context context) {
        this(context, null);
    }

    public DeviceDetailTimeSelect(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DeviceDetailTimeSelect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_device_detail_select, this, true);
        ButterKnife.bind(this);
        initLeftTimePicker();
        initTimePicker();
    }

    @OnClick({R.id.rl_data_layout, R.id.rl_time_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_data_layout:   // 日期选择
                if (pvLeftTime != null) {
                    pvLeftTime.show();
                }
                break;
            case R.id.rl_time_layout:   // 时间选择
                if (homeTimeSelectView == null) {
                    initHomeSelect();
                }
                homeTimeSelectView.show();
                break;
        }
    }

    /**
     * 初始化日期选择器
     */
    private void initLeftTimePicker() {//Dialog 模式下，在底部弹出
        pvLeftTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvDate.setText(getDate(date));
                if (mListener != null) {
                    mListener.onSelect(getFormatDate(date));
                }
            }
        }).setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
            @Override
            public void onTimeSelectChanged(Date date) {
                Log.i("pvLeftTime", "onTimeSelectChanged");
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
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
                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }

    private void initHomeSelect() {
        homeTimeSelectView = new HomeTimeSelectView(getContext(), new HomeTimeSelectView.OnConfirmClick() {
            @Override
            public void onClick(String time) {
                tvTime.setText(time);
            }
        });
    }

    /**
     * 初始化时间选择器
     */
    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvTime.setText(getTime(date));
            }
        }).setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
            @Override
            public void onTimeSelectChanged(Date date) {
                Log.i("pvLeftTime", "onTimeSelectChanged");
            }
        })
                .setType(new boolean[]{false, false, false, true, true, false})
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }

    private String getDate(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("HH~mm");
        return format.format(date);
    }

    public String getFormatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private OnDateSelect mListener;

    public void setOnDateSelect(OnDateSelect listener) {
        this.mListener = listener;
    }

    public interface OnDateSelect{
        void onSelect(String time);
    }
}
