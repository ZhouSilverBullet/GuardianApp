package com.sdxxtop.guardianapp.model.bean;

import android.text.TextUtils;

import com.amap.api.maps.model.LatLng;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2020/1/10
 * Desc:
 */
public class CheckTrajectoryBean {

    public List<LongitudeBean> longitude;

    public static class LongitudeBean {
        public String longitude;
        public LatLng latLng;

        public LatLng getLatLng() {
            if (latLng == null) {
                if (!TextUtils.isEmpty(longitude)) {
                    String[] split = longitude.split(",");
                    latLng = new LatLng(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
                }
            }
            return latLng;
        }
    }
}
