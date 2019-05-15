package com.sdxxtop.guardianapp.model.bean;

import com.amap.api.maps.model.LatLng;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/5/15
 * Desc:
 */
public class EnterpriseTrailBean {
    private List<GridSign> grid_sign;

    private double distance;
    private double total_time;
    private List<TrailInfo> trail_info;

    public List<GridSign> getGrid_sign() {
        return grid_sign;
    }

    public void setGrid_sign(List<GridSign> grid_sign) {
        this.grid_sign = grid_sign;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getTotal_time() {
        return total_time;
    }

    public void setTotal_time(double total_time) {
        this.total_time = total_time;
    }

    public List<TrailInfo> getTrail_info() {
        return trail_info;
    }

    public void setTrail_info(List<TrailInfo> trail_info) {
        this.trail_info = trail_info;
    }

    public static class GridSign{
        private int userid;
        private String name;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class TrailInfo{
        private int userid;
        private String longitude;
        private String sign_time;

        public LatLng getLatLng() {
            return new LatLng(Double.parseDouble(longitude.split(",")[1]),Double.parseDouble(longitude.split(",")[0]));
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getSign_time() {
            return sign_time;
        }

        public void setSign_time(String sign_time) {
            this.sign_time = sign_time;
        }
    }
}
