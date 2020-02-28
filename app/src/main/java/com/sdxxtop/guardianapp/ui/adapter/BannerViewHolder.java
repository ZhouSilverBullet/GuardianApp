package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.DataEntry;
import com.sdxxtop.guardianapp.ui.widget.RoundRectImageView;
import com.sdxxtop.guardianapp.ui.widget.mzbanner.holder.MZViewHolder;

/**
 * @author :  lwb
 * Date: 2019/9/6
 * Desc:
 */
public class BannerViewHolder implements MZViewHolder<DataEntry> {
    private RoundRectImageView mImageView;
    private TextView tvTitle;
    private TextView text_1, text_2, text_3, tvDesc;
    public static final int TYPE_YCJC = 0;  // 扬尘监测
    public static final int TYPE_WURENJI = 1;  // 无人机
    public static final int TYPE_REPORT_PROBLEM = 2;  // 问题上报

    private LinearLayout layout_1;
    private LinearLayout layout_2;
    private LinearLayout layout_3;

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
        layout_1 = view.findViewById(R.id.ll_layout_1);
        layout_2 = view.findViewById(R.id.ll_layout_2);
        layout_3 = view.findViewById(R.id.ll_layout_3);

        return view;
    }

    @Override
    public void onBind(Context context, int position, DataEntry data) {
        // 数据绑定
        mImageView.setImageResource(data.resId);
        tvTitle.setText(data.title);
        layout_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onTabClick(position, R.id.ll_layout_1);
                }
            }
        });
        layout_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onTabClick(position, R.id.ll_layout_2);
                }
            }
        });
        layout_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onTabClick(position, R.id.ll_layout_3);
                }
            }
        });
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
            case TYPE_REPORT_PROBLEM:
                tvDesc.setText(context.getResources().getString(R.string.report_problem));
                text_1.setText("问题添加");
                text_2.setText("问题列表");
                text_3.setText("统计");
                break;
        }
    }

    private OnTabClickListener mListener;

    public void setOnTabClick(OnTabClickListener listener) {
        this.mListener = listener;
    }

    public interface OnTabClickListener {
        void onTabClick(int index, int layoutId);
    }
}
