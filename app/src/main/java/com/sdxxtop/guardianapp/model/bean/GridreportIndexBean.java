package com.sdxxtop.guardianapp.model.bean;

import android.text.TextUtils;

import com.amap.api.maps.model.LatLng;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/5/15
 * Desc:
 */
public class GridreportIndexBean {
    private int grid_count;
    private int grid_distance;
    private int grid_sign_time;
    private int grid_now_count;
    private String  event_name;

    private List<GridPartBean> part;
    private List<GridNowInfo> grid_now_info;

    public List<GridNowInfo> getGrid_now_info() {
        return grid_now_info;
    }

    public void setGrid_now_info(List<GridNowInfo> grid_now_info) {
        this.grid_now_info = grid_now_info;
    }

    public int getGrid_count() {
        return grid_count;
    }

    public void setGrid_count(int grid_count) {
        this.grid_count = grid_count;
    }

    public int getGrid_distance() {
        return grid_distance;
    }

    public void setGrid_distance(int grid_distance) {
        this.grid_distance = grid_distance;
    }

    public int getGrid_sign_time() {
        return grid_sign_time;
    }

    public void setGrid_sign_time(int grid_sign_time) {
        this.grid_sign_time = grid_sign_time;
    }

    public int getGrid_now_count() {
        return grid_now_count;
    }

    public void setGrid_now_count(int grid_now_count) {
        this.grid_now_count = grid_now_count;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public List<GridPartBean> getPart() {
        return part;
    }

    public void setPart(List<GridPartBean> part) {
        this.part = part;
    }

    public static class GridPartBean{
        private int part_id;
        private String part_name;

        public int getPart_id() {
            return part_id;
        }

        public void setPart_id(int part_id) {
            this.part_id = part_id;
        }

        public String getPart_name() {
            return part_name;
        }

        public void setPart_name(String part_name) {
            this.part_name = part_name;
        }
    }
    public static class GridNowInfo{
        private int userid;
        private int part_id;
        private String name;
        private String img;
        private String part_name;
        private String longitude;

        public LatLng getLatlng() {
            if (TextUtils.isEmpty(longitude)){
                return new LatLng(0,0);
            }
            return new LatLng(Double.parseDouble(longitude.split(",")[1]),Double.parseDouble(longitude.split(",")[0]));
        }
        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getPart_id() {
            return part_id;
        }

        public void setPart_id(int part_id) {
            this.part_id = part_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getPart_name() {
            return part_name;
        }

        public void setPart_name(String part_name) {
            this.part_name = part_name;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
