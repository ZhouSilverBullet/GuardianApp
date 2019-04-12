package com.sdxxtop.guardianapp.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainMapBean {
    private List<UserBean> user;

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

}
