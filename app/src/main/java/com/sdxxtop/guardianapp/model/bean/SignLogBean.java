package com.sdxxtop.guardianapp.model.bean;

import android.text.TextUtils;

import com.amap.api.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignLogBean {
    /**
     * num : 1
     * distance : 0
     * sign : [{"longitude":"116.313584,40.034945","address":"北京市海淀区创业路靠近颐泉汇","sign_time":"2019-04-12 11:14:00"}]
     */

    private int num;
    private double distance;
    private List<SignBean> sign;

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

    public List<SignBean> getSign() {
        return sign;
    }

    public void setSign(List<SignBean> sign) {
        this.sign = sign;
    }

    public static class SignBean {
        /**
         * longitude : 116.313584,40.034945
         * address : 北京市海淀区创业路靠近颐泉汇
         * sign_time : 2019-04-12 11:14:00
         */

        @SerializedName("longitude")
        private String lonLog;
        private LatLng latLng;
        private String address;
        private String sign_time;

        public LatLng getLatLng() {
            if (latLng == null) {
                if (!TextUtils.isEmpty(lonLog)) {
                    String[] split = lonLog.split(",");
                    latLng = new LatLng(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
                }
            }
            return latLng;
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
    }


}
