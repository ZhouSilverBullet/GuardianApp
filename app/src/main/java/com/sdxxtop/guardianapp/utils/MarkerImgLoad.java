package com.sdxxtop.guardianapp.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sdxxtop.guardianapp.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * @author :  lwb
 * Date: 2019/5/8
 * Desc:
 */
public class MarkerImgLoad {
    public static final String TAG = "MarkerImgLoad";

    private Activity mContext;
    private BitmapDescriptor bitmapDescriptor;
    private ArrayList<BitmapDescriptor> iconList = new ArrayList();

    public MarkerImgLoad(Activity context) {
        this.mContext = context;
    }

    /**
     * by moos on 2018/01/12
     * func:添加单个自定义marker
     *
     * @param latLng 位置
     * @param sign   marker标记
     */
    public void addCustomMarker(final LatLng latLng, final MarkerSign sign, OnMarkerListener listener) {
        String url = "http://ucardstorevideo.b0.upaiyun.com/test/e8c8472c-d16d-4f0a-8a7b-46416a79f4c6.png";
        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.setFlat(true);
        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.position(new LatLng(latLng.latitude, latLng.longitude));
        customizeMarkerIcon(url, new OnMarkerIconLoadListener() {
            @Override
            public void markerIconLoadingFinished(View view) {
                //bitmapDescriptor = BitmapDescriptorFactory.fromView(view);
                iconList.add(0,bitmapDescriptor);
                markerOptions.position(latLng);
                markerOptions.icon(bitmapDescriptor);

                String marker = "Marker";
                String json = "[{" +
                        "'title':'"+ marker +"',"+
                        "'url':'"+url+
                        "'}]";

                markerOptions.title(json);
                markerOptions.snippet("四川省成都市青羊区一环路二段靠近千百味冷锅串串,小吃姮好吃,成都地方气温热");
                listener.showMarkerIcon(markerOptions, sign);
            }
        });
    }

    /**
     * by moos on 2018/01/12
     * func:定制化marker的图标
     *
     * @return
     */
    private void customizeMarkerIcon(String url, final OnMarkerIconLoadListener listener) {
        final View markerView = LayoutInflater.from(mContext).inflate(R.layout.marker_bg, null);
        final CircleImageView icon = markerView.findViewById(R.id.marker_item_icon);
        Glide.with(mContext)
                .load(url)
                .into(new SimpleTarget<Drawable>(100, 100) {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        icon.setImageDrawable(resource);
                        bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(markerView));
                        listener.markerIconLoadingFinished(markerView);
                    }
                });
    }


    /**
     * by moos on 2018/01/12
     * func:自定义监听接口,用来marker的icon加载完毕后回调添加marker属性
     */
    public interface OnMarkerIconLoadListener {
        void markerIconLoadingFinished(View view);
    }

    /**
     * by moos on 2018/01/12
     * func:自定义监听接口,用来填充地图
     */
    public interface OnMarkerListener {
        void showMarkerIcon(MarkerOptions markerOptions, MarkerSign sign);
    }

    /**
     * by mos on 2018.01.12
     * func:view转bitmap
     */
    public static Bitmap convertViewToBitmap(View view) {

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.buildDrawingCache();

        Bitmap bitmap = view.getDrawingCache();

        return bitmap;

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
