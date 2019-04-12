package com.sdxxtop.guardianapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.Marker;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.app.App;

import java.util.ArrayList;

public class GridMapAdapter implements AMap.InfoWindowAdapter {
    @Override
    public View getInfoWindow(Marker marker) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.item_grid_map_view, null);
        TextView titleView = (TextView) view.findViewById(R.id.tv_title);
        ImageView icon = (ImageView) view.findViewById(R.id.iv_icon);
        ArrayList<BitmapDescriptor> icons = marker.getIcons();
        if (icons != null && icons.size() > 0) {
            icon.setImageBitmap(icons.get(0).getBitmap());
        }

        titleView.setText(marker.getSnippet());
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
