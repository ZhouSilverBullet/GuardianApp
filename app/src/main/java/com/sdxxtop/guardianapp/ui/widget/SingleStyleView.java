package com.sdxxtop.guardianapp.ui.widget;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.List;

public class SingleStyleView {
    private List<String> mList;
    private Activity mActivity;
    private OnItemSelectLintener mLintener;
    private OptionsPickerView pvOptions;

    public SingleStyleView(Activity activity, List<String> list) {
        mActivity = activity;
        mList = list;

        initOptionPicker();
    }

    public void show() {
        if (pvOptions != null) {
            pvOptions.show();
        }
    }

    public void replaceData(List<String> queryData) {
        this.mList = queryData;
        pvOptions.setPicker(queryData);
    }

    public interface OnItemSelectLintener {
        void onItemSelect(String result);
    }

    public void setOnItemSelectLintener(OnItemSelectLintener lintener) {
        this.mLintener = lintener;
    }

    private void initOptionPicker() {//条件选择器初始化

        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */

        pvOptions = new OptionsPickerBuilder(mActivity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = mList.get(options1);
                mLintener.onItemSelect(tx);
            }
        })
                .setTitleText(" ")
                .setContentTextSize(23)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setBgColor(0xFFffffff)
                .setSelectOptions(0)//默认选中项
                .setTitleBgColor(Color.parseColor("#EEEEEE"))
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(0xFF999999)
                .setSubmitColor(0xFF33B5E5)
                .setTextColorCenter(0xFF33B5E5)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
                    }
                })
                .build();
        pvOptions.setPicker(mList);//二级选择器
    }
}
