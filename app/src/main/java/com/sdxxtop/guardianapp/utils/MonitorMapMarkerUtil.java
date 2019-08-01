package com.sdxxtop.guardianapp.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.EnterpriseIndexBean;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * @author :  lwb
 * Date: 2019/7/4
 * Desc:
 */
public class MonitorMapMarkerUtil {

    private Activity mContext;

    public MonitorMapMarkerUtil(Activity activity) {
        this.mContext = activity;
    }

    /**
     * by moos on 2018/01/12
     * func:添加单个自定义marker
     * 企业
     */
    public void addCustomMarker(EnterpriseIndexBean.UserInfo userInfo, final MarkerSign sign, OnMarkerListener listener) {
        LatLng latLng = getLatLng(userInfo.getLongitude());
        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.setFlat(true);
        customizeMarkerIcon(userInfo, new OnMarkerIconLoadListener() {
            @Override
            public void markerIconLoadingFinished(View view) {
                markerOptions.position(latLng);
                markerOptions.title(String.valueOf(userInfo.getUserid()));
                markerOptions.snippet(userInfo.getName());
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(view)));
                listener.showMarkerIcon(markerOptions, sign);
            }
        });
    }

    /**
     * by moos on 2018/01/12
     * func:定制化marker的图标
     * 企业
     *
     * @return
     */
    private void customizeMarkerIcon(EnterpriseIndexBean.UserInfo userInfo, final OnMarkerIconLoadListener listener) {
        final View markerView = LayoutInflater.from(mContext).inflate(R.layout.device_marker_bg, null);
        final CircleImageView icon = markerView.findViewById(R.id.img);
        final TextView tvCompany = markerView.findViewById(R.id.tv_company);
        final TextView name = markerView.findViewById(R.id.name);
        final TextView tv_jobs = markerView.findViewById(R.id.tv_jobs);

        tvCompany.setText(userInfo.getPart_name());
        name.setText(userInfo.getName());
        tv_jobs.setText(userInfo.getPosition());
        UIUtils.getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                Glide.with(mContext)
                        .load(userInfo.getImg())
                        .into(new SimpleTarget<Drawable>(100, 100) {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                if (icon==null||listener==null)return;
                                icon.setImageDrawable(resource);
                                listener.markerIconLoadingFinished(markerView);
                            }
                        });
            }
        });
    }

    /**
     * by moos on 2018/01/12
     * func:自定义监听接口,用来填充地图
     */
    public interface OnMarkerListener {
        void showMarkerIcon(MarkerOptions markerOptions, MarkerSign sign);
    }

    /**
     * by moos on 2018/01/12
     * func:自定义监听接口,用来marker的icon加载完毕后回调添加marker属性
     */
    public interface OnMarkerIconLoadListener {
        void markerIconLoadingFinished(View view);
    }

    /**
     * by mos on 2018.01.12
     * func:view转bitmap
     */
    public Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    public LatLng getLatLng(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split(",");
            double v = Double.parseDouble(split[1]);
            double v1 = Double.parseDouble(split[0]);
            return new LatLng(v, v1);
        }
        return null;
    }
}
