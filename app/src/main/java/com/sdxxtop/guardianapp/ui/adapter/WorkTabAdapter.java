package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.model.bean.WorkIndexBean;
import com.sdxxtop.guardianapp.ui.activity.EventDiscretionListActivity;
import com.sdxxtop.guardianapp.ui.activity.EventReportListActivity;
import com.sdxxtop.guardianapp.ui.activity.GrantCompanyReportActivity;
import com.sdxxtop.guardianapp.ui.activity.GrantEventReportActivity;
import com.sdxxtop.guardianapp.ui.activity.GrantGridReportActivity;
import com.sdxxtop.guardianapp.ui.activity.GridEventActivity;
import com.sdxxtop.guardianapp.ui.activity.PatrolRecordActivity;
import com.sdxxtop.guardianapp.ui.activity.SectionEventActivity;
import com.sdxxtop.guardianapp.utils.SpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/9/5
 * Desc:
 */
public class WorkTabAdapter extends BaseAdapter {

    private int[] imgRes = {R.drawable.icon_shijian_work, R.drawable.icon_shuju_work, R.drawable.icon_qiye_work,
            R.drawable.icon_bumen_work, R.drawable.icon_shangbao_work, R.drawable.icon_xuncha_work, R.drawable.icon_bumen_work};
    private String[] titleRes = {"事件统计", "工作数据", "企业数据", "部门事件", "我的上报", "我的巡查", "网格事件"};

    private List<Integer> imgResValue = new ArrayList<>();
    private List<String> titleResValue = new ArrayList<>();

    public WorkTabAdapter() {
        for (int imgRe : imgRes) {
            imgResValue.add(imgRe);
        }
        for (String titleRe : titleRes) {
            titleResValue.add(titleRe);
        }
        long aLong = SpUtil.getLong(Constants.IS_TRACK, 2);
        if (aLong == 1) {
            imgResValue.add(R.drawable.icon_bumen_work);
            titleResValue.add("工作轨迹");
        }
    }


    @Override
    public int getCount() {
        return titleResValue.size();
    }

    @Override
    public Object getItem(int position) {
        return titleResValue.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            // 第一次加载创建View，其余复用 View
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.gridview_item_work, null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.iv_icon);
            holder.textView = convertView.findViewById(R.id.tv_title);
            holder.llLayout = convertView.findViewById(R.id.ll_layout);
            // 打标签
            convertView.setTag(holder);

        } else {
            // 从标签中获取数据
            holder = (ViewHolder) convertView.getTag();
        }

        // 根据key值设置不同数据内容
        holder.imageView.setImageResource(imgResValue.get(position));
        holder.textView.setText(titleResValue.get(position));
        holder.llLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(position, parent.getContext());
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;
        LinearLayout llLayout;
    }

    private void startActivity(int index, Context context) {
        if (mBean == null) {
            showToast(context);
            return;
        }
        Intent intent = null;
        switch (index) {
            case 0:   // 事件统计
                if (mBean.is_statistics == 1) {
                    intent = new Intent(context, GrantEventReportActivity.class);
                }
                break;
            case 1:   // 工作数据
                if (mBean.is_working == 1) {
                    intent = new Intent(context, GrantGridReportActivity.class);
                }
                break;
            case 2:   // 企业数据
                if (mBean.is_enterprise == 1) {
                    intent = new Intent(context, GrantCompanyReportActivity.class);
                }
                break;
            case 3:   // 部门事件
                if (mBean.is_sectoral == 1) {
                    intent = new Intent(context, SectionEventActivity.class);
                }
                break;
            case 4:   // 我的上报
                if (mBean.is_report == 1) {
                    intent = new Intent(context, EventReportListActivity.class);
                }
                break;
            case 5:   // 我的巡查
                if (mBean.is_patrol == 1) {
                    intent = new Intent(context, EventDiscretionListActivity.class);
                }
                break;
            case 6:   // 网格事件
                if (mBean.is_grid_event == 1) {
                    intent = new Intent(context, GridEventActivity.class);
                }
                break;
            case 7:   // 工作轨迹
                intent = new Intent(context, PatrolRecordActivity.class);
                break;
        }
        if (intent == null) {
            showToast(context);
            return;
        }
        context.startActivity(intent);
    }

    private WorkIndexBean mBean;

    public void setLimits(WorkIndexBean bean) {
        this.mBean = bean;

    }

    public void showToast(Context context) {
        Toast.makeText(context, "暂无权限", Toast.LENGTH_SHORT).show();
    }
}
