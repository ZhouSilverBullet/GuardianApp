package com.sdxxtop.guardianapp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.MainIndexBeanNew;
import com.sdxxtop.guardianapp.ui.activity.EventDiscretionReportActivity;
import com.sdxxtop.guardianapp.ui.activity.EventReportActivity;
import com.sdxxtop.guardianapp.ui.activity.GridMapActivity;
import com.sdxxtop.guardianapp.ui.activity.HomeActivity;
import com.sdxxtop.guardianapp.ui.activity.MyFaceLivenessActivity;
import com.sdxxtop.guardianapp.ui.dialog.IosAlertDialog;
import com.sdxxtop.guardianapp.ui.widget.imgservice.OnlineServiceActivity;
import com.sdxxtop.guardianapp.utils.AMapFindLocation2;
import com.sdxxtop.guardianapp.utils.GpsUtils;
import com.sdxxtop.guardianapp.utils.UIUtils;

import androidx.cardview.widget.CardView;

/**
 * @author :  lwb
 * Date: 2019/9/5
 * Desc:
 */
public class MyHomeTabAdapter extends BaseAdapter {

    private int[] imgRes = {R.drawable.shijian_home_new, R.drawable.xuncha_home_new, R.drawable.qiandao_home_new,
            R.drawable.wurenji_home_new, R.drawable.map_home_new, R.drawable.service_home_new};
    private String[] titleRes = {"事件上报", "巡查上报", "签到打卡"};

    @Override
    public int getCount() {
        return titleRes.length;
    }

    @Override
    public Object getItem(int position) {
        return titleRes[position];
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
                    R.layout.gridview_item_img_tv, null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.iv_icon);
            holder.textView = convertView.findViewById(R.id.tv_title);
            holder.cardview = convertView.findViewById(R.id.cardview);
            // 打标签
            convertView.setTag(holder);

        } else {
            // 从标签中获取数据
            holder = (ViewHolder) convertView.getTag();
        }

        // 根据key值设置不同数据内容
        holder.imageView.setImageResource(imgRes[position]);
        holder.textView.setText(titleRes[position]);
        holder.cardview.setOnClickListener(new View.OnClickListener() {
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
        CardView cardview;
    }

    private void startActivity(int index, Context context) {
        if (mBean == null) {
            showToast(context);
            return;
        }
        Intent intent = null;
        switch (index) {
            case 0: // 事件上报
                if (mBean.is_report == 1) {
                    intent = new Intent(context, EventReportActivity.class);
                }
                break;
            case 1:  // 巡查上报
                if (mBean.is_patrol == 1) {
                    intent = new Intent(context, EventDiscretionReportActivity.class);
                }
                break;
            case 2:
                toFaceDaka(context);
                break;
            case 3: // 无人机检测
                if (mBean.is_uav == 1) {
                    if (mActivity != null) {
                        ((HomeActivity) mActivity).cbtView.llMenu2.performClick();
                    }
                }
                break;
            case 4:  //网格地图
                if (mBean.is_map == 1) {
                    intent = new Intent(context, GridMapActivity.class);
                }
                break;
            case 5:
                intent = new Intent(context, OnlineServiceActivity.class);
                intent.putExtra("href", "https://tb.53kf.com/code/client/b722216fa3d928f41a494d544ac54dcb/2?device=android");
                break;
        }
        if (intent == null) {
            if (index == 2) {
                return;
            }
            showToast(context);
            return;
        }
        context.startActivity(intent);
    }

    private void toFaceDaka(Context context) {
        //判断一次打卡，gps是否打开
        if (mBean.is_clock == 1) {
            if (mBean.is_face == 1) {
                if (mActivity != null) {
                    ((HomeActivity) mActivity).showLoadingDialog();
                }
                if (GpsUtils.isOPen(context)) {
                    AMapFindLocation2 instance = AMapFindLocation2.getInstance();
                    instance.location();
                    instance.setLocationCompanyListener(new AMapFindLocation2.LocationCompanyListener() {
                        @Override
                        public void onAddress(AMapLocation aMapLocation) {
                            if (mActivity != null) {
                                ((HomeActivity) mActivity).hideLoadingDialog();
                            }
                            String address = aMapLocation.getAddress();
                            if (TextUtils.isEmpty(address)) {
                                UIUtils.showToast("定位获取位置失败,请稍后重试");
                            } else {
                                instance.stopLocation();
                                Intent intent = new Intent(context, MyFaceLivenessActivity.class);
                                intent.putExtra("isFace", true);
                                intent.putExtra("address", address);
                                context.startActivity(intent);
                            }
                        }
                    });
                } else {
                    GpsUtils.showCode332ErrorDialog(context);
                }
            } else {
                toFace(context);
            }
        } else {
            showToast(context);
        }
    }

    private MainIndexBeanNew mBean;
    private Activity mActivity;

    public void setLimits(MainIndexBeanNew bean, Activity activity) {
        this.mBean = bean;
        this.mActivity = activity;
    }

    public void showToast(Context context) {
        Toast.makeText(context, "暂无权限", Toast.LENGTH_SHORT).show();
    }

    private void toFace(Context context) {

        new IosAlertDialog(context)
                .builder()
                .setTitle("提示")
                .setMsg("您还没注册人脸信息，是否去录入")
                .setPositiveButton("录入", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MyFaceLivenessActivity.class);
                        intent.putExtra("isFace", false);
                        mActivity.startActivityForResult(intent, 100);
                    }
                })
                .setNegativeButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }
}

