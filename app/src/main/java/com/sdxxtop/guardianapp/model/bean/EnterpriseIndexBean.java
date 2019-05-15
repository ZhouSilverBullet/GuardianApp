package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/5/15
 * Desc:
 */
public class EnterpriseIndexBean {

    private String event_name;//返回的选中区域名称
    private int part_count;//企业数量
    private int user_count;//安全员管理数量
    private int trai_count;//学习考试培训次数
    private int report_info;//上报自查
    private int now_count;//在线人数数量

    private List<PartInfo> part_info;
    private List<UserInfo> user_info;

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public int getPart_count() {
        return part_count;
    }

    public void setPart_count(int part_count) {
        this.part_count = part_count;
    }

    public int getUser_count() {
        return user_count;
    }

    public void setUser_count(int user_count) {
        this.user_count = user_count;
    }

    public int getTrai_count() {
        return trai_count;
    }

    public void setTrai_count(int trai_count) {
        this.trai_count = trai_count;
    }

    public int getReport_info() {
        return report_info;
    }

    public void setReport_info(int report_info) {
        this.report_info = report_info;
    }

    public int getNow_count() {
        return now_count;
    }

    public void setNow_count(int now_count) {
        this.now_count = now_count;
    }

    public List<PartInfo> getPart_info() {
        return part_info;
    }

    public void setPart_info(List<PartInfo> part_info) {
        this.part_info = part_info;
    }

    public List<UserInfo> getUser_info() {
        return user_info;
    }

    public void setUser_info(List<UserInfo> user_info) {
        this.user_info = user_info;
    }

    public static class PartInfo {
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

    public static class UserInfo {
        private int userid;
        private String name;
        private String img;
        private int part_id;
        private String position;
        private String part_name;
        private String longitude;

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getPart_id() {
            return part_id;
        }

        public void setPart_id(int part_id) {
            this.part_id = part_id;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
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
