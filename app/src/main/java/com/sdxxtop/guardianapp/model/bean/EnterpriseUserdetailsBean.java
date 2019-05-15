package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/5/15
 * Desc:
 */
public class EnterpriseUserdetailsBean {

    private int parent_id;
    private List<UserInfo> userinfo;

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public List<UserInfo> getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(List<UserInfo> userinfo) {
        this.userinfo = userinfo;
    }

    public static class UserInfo{
        private String name;
        private int userid;
        private int sign_count;
        private int event_count;
        private int trai_count;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getSign_count() {
            return sign_count;
        }

        public void setSign_count(int sign_count) {
            this.sign_count = sign_count;
        }

        public int getEvent_count() {
            return event_count;
        }

        public void setEvent_count(int event_count) {
            this.event_count = event_count;
        }

        public int getTrai_count() {
            return trai_count;
        }

        public void setTrai_count(int trai_count) {
            this.trai_count = trai_count;
        }
    }
}
