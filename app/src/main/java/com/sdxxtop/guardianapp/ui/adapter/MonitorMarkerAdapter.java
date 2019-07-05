package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.sdxxtop.guardianapp.R;

/**
 * @author :  lwb
 * Date: 2019/5/8
 * Desc:
 */
public class MonitorMarkerAdapter implements AMap.InfoWindowAdapter {

    private Context mContext;
    private LatLng latLng;
    private TextView tvTitle, tvTpfpm, tvTenpm;
    private String snippet,title;


    public MonitorMarkerAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        initData(marker);
        View view = initView();
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private void initData(Marker marker) {
        latLng = marker.getPosition();
        MarkerOptions options = marker.getOptions();
        if (options!=null){
            snippet = options.getSnippet();
            title = options.getTitle();
        }
    }

    private View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.monitor_marker_bg, null);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTpfpm = view.findViewById(R.id.tv_tpfpm);
        tvTenpm = view.findViewById(R.id.tv_tenpm);

        if (!TextUtils.isEmpty(title)){
            String[] split = title.split(";");
            if (split.length==3){
                tvTitle.setText(split[0]);
                tvTpfpm.setText("PM2.5:"+split[1]);
                tvTenpm.setText("PM10 :"+split[2]);
            }
        }
        return view;
    }
}
