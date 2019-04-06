package com.sdxxtop.guardianapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.app.App;

public class PatrolMapAdapter implements AMap.InfoWindowAdapter {
    @Override
    public View getInfoWindow(Marker marker) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.item_patrol_map_view, null);
        TextView titleView = (TextView) view.findViewById(R.id.tv_exercise_item_title);
        titleView.setText(marker.getSnippet());
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
