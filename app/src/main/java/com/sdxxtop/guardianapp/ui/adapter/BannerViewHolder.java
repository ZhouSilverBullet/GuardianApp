package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.AuthDataBean;
import com.sdxxtop.guardianapp.model.bean.DataEntry;
import com.sdxxtop.guardianapp.ui.activity.DeviceListActivity;
import com.sdxxtop.guardianapp.ui.activity.FlyDataListActivity;
import com.sdxxtop.guardianapp.ui.activity.MonitorMapActivity;
import com.sdxxtop.guardianapp.ui.widget.RoundRectImageView;
import com.sdxxtop.guardianapp.ui.widget.mzbanner.holder.MZViewHolder;

/**
 * @author :  lwb
 * Date: 2019/9/6
 * Desc:
 */
public class BannerViewHolder implements MZViewHolder<DataEntry>, View.OnClickListener {
    private RoundRectImageView mImageView;
    private TextView tvTitle;
    private TextView text_1, text_2, text_3, tvDesc;
    public static final int TYPE_YCJC = 0;  // 扬尘监测
    public static final int TYPE_WURENJI = 1;  // 无人机

    private AuthDataBean mBean;
    private int mType;

    @Override
    public View createView(Context context) {
        // 返回页面布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item_yingyong, null);
        mImageView = view.findViewById(R.id.banner_image);
        tvTitle = view.findViewById(R.id.tv_title);
        text_1 = view.findViewById(R.id.text_1);
        text_2 = view.findViewById(R.id.text_2);
        text_3 = view.findViewById(R.id.text_3);
        tvDesc = view.findViewById(R.id.tv_desc);
        view.findViewById(R.id.ll_layout_1).setOnClickListener(this);
        view.findViewById(R.id.ll_layout_2).setOnClickListener(this);
        view.findViewById(R.id.ll_layout_3).setOnClickListener(this);

        return view;
    }

    @Override
    public void onBind(Context context, int position, DataEntry data) {
        // 数据绑定
        mImageView.setImageResource(data.resId);
        tvTitle.setText(data.title);
        mType = data.type;
        switch (data.type) {
            case TYPE_YCJC:
                tvDesc.setText(context.getResources().getString(R.string.yc_tx));
                text_1.setText("地图");
                text_2.setText("列表");
                text_3.setText("报表");
                break;
            case TYPE_WURENJI:
                tvDesc.setText(context.getResources().getString(R.string.wurenji_tx));
                text_1.setText("热力图");
                text_2.setText("数据");
                text_3.setText("统计");
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (mBean == null) {
            return;
        }
        Context context = view.getContext();
        switch (view.getId()) {
            case R.id.ll_layout_1:
                if (mType == 0) {//扬尘
                    if (mBean.is_raise_dust == 1) {
                        context.startActivity(new Intent(context, MonitorMapActivity.class));
                    } else {
                        showToast("暂无权限",context);
                    }
                } else {
                    showToast("暂未开放",context);
                }
                break;
            case R.id.ll_layout_2:
                if (mType == 0) {//扬尘
                    if (mBean.is_raise_dust == 1) {
                        Intent intent = new Intent(context, DeviceListActivity.class);
                        intent.putExtra("status", "全部");
                        context.startActivity(intent);
                    } else {
                        showToast("暂无权限",context);
                    }
                } else {
                    if (mBean.is_uav == 1) {
                        context.startActivity(new Intent(context, FlyDataListActivity.class));
                    } else {
                       showToast("暂无权限",context);
                    }
                }
                break;
            case R.id.ll_layout_3:
                showToast("暂未开放",context);
                break;
        }
    }

    public void setPerMission(AuthDataBean bean) {
        this.mBean = bean;
    }

    public void showToast(String msg, Context context) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }
}
