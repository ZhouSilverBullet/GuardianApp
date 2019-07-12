package com.sdxxtop.guardianapp.ui.widget.timePicker;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.view.WheelView;
import com.sdxxtop.guardianapp.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @author :  lwb
 * Date: 2018/11/3
 * Desc:
 */

public class HomeTimeSelectView extends Dialog {
    private tempAdapter tempAdapter1, tempAdapter2;
    private tempAdapter hourStartAdapter, minuteStartAdapter, hourEndAdapter, minuteEndAdapter;
    private WheelView wheelStartHour, wheelStartMinute, wheelEndMinute, wheelEndHour;
    private OnConfirmClick mListener;

    public HomeTimeSelectView(Context context, OnConfirmClick listener) {
        super(context, R.style.MyDialogStyleBottom);
        this.mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_hometime_choose);//这行一定要写在前面
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
        hourStartAdapter = new tempAdapter(1);  // 时
        minuteStartAdapter = new tempAdapter(2);  // 分
        hourEndAdapter = new tempAdapter(1);  // 时
        minuteEndAdapter = new tempAdapter(2);  // 分
        tempAdapter1 = new tempAdapter(3);
        tempAdapter2 = new tempAdapter(3);

        wheelStartHour = (WheelView) findViewById(R.id.wheel_start_hour);
        wheelStartMinute = (WheelView) findViewById(R.id.wheel_start_minute);
        wheelEndHour = (WheelView) findViewById(R.id.wheel_end_hour);
        wheelEndMinute = (WheelView) findViewById(R.id.wheel_end_minute);

        WheelView wheel_temp_1 = (WheelView) findViewById(R.id.wheel_temp_1);
        WheelView wheel_temp_2 = (WheelView) findViewById(R.id.wheel_temp_2);
        WheelView wheel_temp = (WheelView) findViewById(R.id.wheel_temp);
        wheel_temp.setGravity(Gravity.CENTER);

        wheelStartHour.setAdapter(hourStartAdapter);
        wheelStartMinute.setAdapter(minuteStartAdapter);
        wheelEndHour.setAdapter(hourEndAdapter);
        wheelEndMinute.setAdapter(minuteEndAdapter);

        wheel_temp_1.setAdapter(tempAdapter1);
        wheel_temp_2.setAdapter(tempAdapter2);
        setDate();
//        wheel_temp.setAdapter(tempAdapter);
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.tv_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startHour = hourStartAdapter.getItem(wheelStartHour.getCurrentItem());
                String startMinute = minuteStartAdapter.getItem(wheelStartMinute.getCurrentItem());
                String endHour = hourEndAdapter.getItem(wheelEndHour.getCurrentItem());
                String endMinute = minuteEndAdapter.getItem(wheelEndMinute.getCurrentItem());
                if (mListener != null) {
                    mListener.onClick(startHour + ":" + startMinute + " ~ " + endHour + ":" + endMinute);
                    dismiss();
                }
            }
        });

    }

    public interface OnConfirmClick {
        void onClick(String time);
    }

    public void setDate() {
        wheelStartHour.setCurrentItem(0);
        wheelStartMinute.setCurrentItem(0);
        wheelEndHour.setCurrentItem(hourEndAdapter.getItemsCount() - 1);
        wheelEndMinute.setCurrentItem(minuteEndAdapter.getItemsCount() - 1);
    }

    public class tempAdapter implements WheelAdapter<String> {

        private List<String> list = new ArrayList<>();

        public tempAdapter(int type) {
            String temp = "";
            switch (type) {
                case 1:
                    for (int i = 0; i < 24; i++) {
                        if (i < 10) {
                            temp = "0" + i;
                        } else {
                            temp = "" + i;
                        }
                        list.add(temp);
                    }
                    break;
                case 3:
                case 2:
                    for (int i = 0; i <= 59; i++) {
                        if (i < 10) {
                            temp = "0" + i;
                        } else {
                            temp = "" + i;
                        }
                        if (type == 2) {
                            list.add(temp);
                        } else {
                            list.add(":");
                        }
                    }
                    break;
            }
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        public String getItem(int index) {
            return list.get(index);
        }

        @Override
        public int indexOf(String o) {
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                if (o.equals(s)) {
                    return i;
                }
            }
            return 0;
        }
    }
}
