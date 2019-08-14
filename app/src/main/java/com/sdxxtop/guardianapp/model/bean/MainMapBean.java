package com.sdxxtop.guardianapp.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainMapBean {
    private List<UserBean> user;
    private List<PartInfoBean> part;

    public List<PartInfoBean> getPart() {
        return part;
    }

    public void setPart(List<PartInfoBean> part) {
        this.part = part;
    }

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public static class UserBean {
        private int userid;
        private String img;
        private String address;
        private String name;
        private String middle;
        @SerializedName("longitude")
        private String lonLon;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMiddle() {
            return middle;
        }

        public void setMiddle(String middle) {
            this.middle = middle;
        }

        public String getLonLon() {
            return lonLon;
        }

        public void setLonLon(String lonLon) {
            this.lonLon = lonLon;
        }
    }

    public static class PartInfoBean{
        private int part_id;
        private String part_name;
        private String color;
        private int parent_id;
        private String longitude;
        private String middle;
        private int level;
        private List<PartInfoBean> children;

        public List<PartInfoBean> getChildren() {
            return children;
        }

        public void setChildren(List<PartInfoBean> children) {
            this.children = children;
        }

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

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getMiddle() {
            return middle;
        }

        public void setMiddle(String middle) {
            this.middle = middle;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }
}
