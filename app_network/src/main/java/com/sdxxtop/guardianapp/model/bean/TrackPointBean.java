package com.sdxxtop.guardianapp.model.bean;

import android.text.TextUtils;

import com.amap.api.maps.model.LatLng;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/7/12
 * Desc:
 */
public class TrackPointBean {

    private int num;
    private double distance;

    private List<List<PointBean>> sign;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<List<PointBean>> getSign() {
        return sign;
    }

    public void setSign(List<List<PointBean>> sign) {
        this.sign = sign;
    }

    public static class PointBean {
        private String longitude;
        private String address;
        private String sign_time;
        private long sign_trid;
        private LatLng latLng;

        public LatLng getLatLng() {
            if (latLng == null) {
                if (!TextUtils.isEmpty(longitude)) {
                    String[] split = longitude.split(",");
                    latLng = new LatLng(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
                }
            }
            return latLng;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSign_time() {
            return sign_time;
        }

        public void setSign_time(String sign_time) {
            this.sign_time = sign_time;
        }

        public long getSign_trid() {
            return sign_trid;
        }

        public void setSign_trid(long sign_trid) {
            this.sign_trid = sign_trid;
        }
    }
}
