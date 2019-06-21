package com.sdxxtop.guardianapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.bumptech.glide.Glide;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.app.App;

import de.hdodenhof.circleimageview.CircleImageView;

public class GridMapAdapter implements AMap.InfoWindowAdapter {
    @Override
    public View getInfoWindow(Marker marker) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.item_grid_map_view, null);
        TextView titleView = (TextView) view.findViewById(R.id.tv_title);
        CircleImageView icon = (CircleImageView) view.findViewById(R.id.iv_icon);

        MarkerOptions options = marker.getOptions();
        if (options !=null){
            String[] snippet = options.getSnippet().split(",:,");
            Glide.with(App.getContext()).load(snippet[2]).into(icon);
            titleView.setText(snippet[1]);
        }
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
