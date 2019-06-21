package com.sdxxtop.guardianapp.ui.widget;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;

public class SingleStyleView {
    private List<String> mList = new ArrayList<>();
    private Activity mActivity;
    private OnItemSelectLintener mLintener;
    private OptionsPickerView pvOptions;
    private List<ListDataBean> mData;

    public SingleStyleView(Activity activity, List<ListDataBean> list) {
        mActivity = activity;
        mData = list;
        mList.clear();
        if (list!=null&&list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                mList.add(list.get(i).name);
            }
        }
        initOptionPicker();
    }

    public void show() {
        if (pvOptions != null) {
            pvOptions.show();
        }
    }

    public void replaceData(List<ListDataBean> list) {
        mData = list;
        mList.clear();
        if (list!=null&&list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                mList.add(list.get(i).name);
            }
        }
        pvOptions.setPicker(mList);
    }

    public interface OnItemSelectLintener {
        void onItemSelect(int id,String result);
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
                ListDataBean listDataBean = mData.get(options1);
                mLintener.onItemSelect(listDataBean.id,listDataBean.name);
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

    public static class ListDataBean{
        public ListDataBean(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int id;
        public String name;
    }

}
