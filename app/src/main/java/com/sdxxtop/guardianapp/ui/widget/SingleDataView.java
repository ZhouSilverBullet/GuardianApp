package com.sdxxtop.guardianapp.ui.widget;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import cn.qqtheme.framework.picker.SinglePicker;
import cn.qqtheme.framework.popup.BasicPopup;
import cn.qqtheme.framework.widget.WheelView;

public class SingleDataView {
    private List<String> mList;
    private Activity mActivity;
    private SinglePicker<String> singlePicker;
    private SinglePicker.OnItemPickListener<String> mListener;


    public SingleDataView(Activity activity, List<String> list) {
        mActivity = activity;
        mList = list;

        init();
    }

    public void show() {
        if (singlePicker != null) {
            Window window = singlePicker.getWindow();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;

            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setAttributes(layoutParams);
            singlePicker.show();
        }
    }

    public void init() {
        singlePicker = new SinglePicker<>(mActivity, mList);
        singlePicker.setTopBackgroundColor(0xFFEEEEEE);
        singlePicker.setTopHeight(52);
        singlePicker.setTopLineColor(0xFF33B5E5);
        singlePicker.setTopLineHeight(1);
        singlePicker.setTitleText("");
        singlePicker.setTitleTextColor(0xFF999999);
        singlePicker.setTitleTextSize(18);
        singlePicker.setCancelTextColor(0xFF999999);
        singlePicker.setCancelTextSize(18);
        singlePicker.setSubmitTextColor(0xFF33B5E5);
        singlePicker.setSubmitTextSize(18);
        singlePicker.setWidth(BasicPopup.MATCH_PARENT);

        WheelView.LineConfig config = new WheelView.LineConfig();
        config.setColor(0xFFD3D3D3);//线颜色
        config.setAlpha(120);//线透明度
        singlePicker.setLineConfig(config);
        singlePicker.setTextSize(23);
        singlePicker.setBackgroundColor(0xFFffffff);


    }

    public void setOnItemPickListener(SinglePicker.OnItemPickListener<String> listener) {
        mListener = listener;
        if (singlePicker != null) {
            singlePicker.setOnItemPickListener(mListener);
        }
    }


}
