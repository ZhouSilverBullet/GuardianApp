package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

public class ContactIndexBean {
    private List<ContactBean> user;

    public List<ContactBean> getUser() {
        return user;
    }

    public void setUser(List<ContactBean> user) {
        this.user = user;
    }

    /**
     *    "userid" : 2,
     *                   "name": "海波",
     *                   "part_id" : 1
     *                   "mobile" : 18618474505
     *                   "img" : http://xuxingtest.oss-cn-hangzhou.aliyuncs.com/app/head%402x.png
     *                   "part_name" : 罗庄区, //单位名称
     */
    public static class ContactBean {
        private int userid;
        private String name;
        private int part_id;
        private String mobile;
        private String img;
        private String part_name;

        public String sortLetters;

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

        public int getPart_id() {
            return part_id;
        }

        public void setPart_id(int part_id) {
            this.part_id = part_id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
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
    }

}
