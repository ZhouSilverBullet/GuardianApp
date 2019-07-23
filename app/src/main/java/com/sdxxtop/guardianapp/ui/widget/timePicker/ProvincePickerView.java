package com.sdxxtop.guardianapp.ui.widget.timePicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.EventShowBean;
import com.sdxxtop.guardianapp.model.bean.JsonBean;
import com.sdxxtop.guardianapp.utils.UIUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/7/18
 * Desc:
 */
public class ProvincePickerView {

    private Context mContext;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private Thread thread;
    private static boolean isLoaded = false;

    private List<EventShowBean.NewPartBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
//                    Toast.makeText(mContext, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
//                    Toast.makeText(mContext, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private List<EventShowBean.NewPartBean> mData;
    private OptionsPickerView pvOptions;

    public ProvincePickerView(Context context, List<EventShowBean.NewPartBean> data) {
        if (data == null) {
            return;
        }
        this.mContext = context;
        this.mData = data;
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
//        String JsonData = new GetJsonDataUtil().getJson(mContext, "province.json");//获取assets目录下的json文件数据
//        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
//        options1Items = jsonBean;
        options1Items = mData;

        for (int i = 0; i < mData.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            List<EventShowBean.ChildrenBean> children_1 = mData.get(i).getChildren();
            children_1.add(0, new EventShowBean.ChildrenBean(0, "全部", new ArrayList<EventShowBean.ChildrenBean>()));

            for (int c = 0; c < children_1.size(); c++) {//遍历该省份的所有城市
                String cityName = children_1.get(c).getPart_name();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/

                List<EventShowBean.ChildrenBean> children_2 = mData.get(i).getChildren().get(c).getChildren();
                if (children_2 != null) {
                    children_2.add(0, new EventShowBean.ChildrenBean(0, "全部", new ArrayList<EventShowBean.ChildrenBean>()));

                    for (EventShowBean.ChildrenBean child : children_2) {
                        city_AreaList.add(child.getPart_name());
                    }
                }
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public void show() {
        if (isLoaded) {
            showPickerView();
        } else {
            UIUtils.showToast("数据为加载完成,稍后再试...");
        }
    }

    private void showPickerView() {// 弹出选择器
        pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";
                int opt1Id = mData.size() > 0 ? mData.get(options1).getPart_id() : 0;

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                int opt2Id = mData.size() > 0
                        && mData.get(options1).getChildren().size() > 0 ?
                        mData.get(options1).getChildren().get(options2).getPart_id() : 0;

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                int opt3Id = mData.size() > 0
                        && mData.get(options1).getChildren().size() > 0 &&
                        mData.get(options1).getChildren().get(options2).getChildren().size() > 0 ?
                        mData.get(options1).getChildren().get(options2).getChildren().get(options3).getPart_id() : 0;

//                String tx = opt1tx + opt2tx + opt3tx;
//                String txId = "" + opt1Id + opt2Id + opt3Id;
//                Toast.makeText(mContext, tx, Toast.LENGTH_SHORT).show();
//                Toast.makeText(mContext, txId, Toast.LENGTH_SHORT).show();
                if (mlistener != null) {
                    String resultTx = "";
                    int resultId = 0;
                    if (options3 == 0) {
                        if (options2 == 0) {
                            resultTx = opt1tx;
                            resultId = opt1Id;
                        }else{
                            resultTx = opt2tx;
                            resultId = opt2Id;
                        }
                    }else{
                        resultTx = opt3tx;
                        resultId = opt3Id;
                    }
                    mlistener.confirmClick(resultTx, resultId);
                }
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_options_select, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView tvCancel = (TextView) v.findViewById(R.id.tv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvOptions.returnData();
                                pvOptions.dismiss();
                            }
                        });

                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvOptions.dismiss();
                            }
                        });
                    }
                })
                .setTitleText(" ")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

//        pvOptions.setPicker(options1Items);//一级选择器
//        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    public interface OnConfirmClick {
        void confirmClick(String resultTx, int resultId);
    }

    private OnConfirmClick mlistener;

    public void setOnConfirmClick(OnConfirmClick onConfirmClick) {
        this.mlistener = onConfirmClick;
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }
}
