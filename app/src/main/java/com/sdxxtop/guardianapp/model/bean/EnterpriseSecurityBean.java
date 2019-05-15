package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/5/15
 * Desc:
 */
public class EnterpriseSecurityBean {
    private String event_name;
    private int user_count;
    private int trai_count;
    private int report_info;
    private int part_count;
    private int part_id;

    private List<SignData> sign_data;

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
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

    public int getPart_count() {
        return part_count;
    }

    public void setPart_count(int part_count) {
        this.part_count = part_count;
    }

    public int getPart_id() {
        return part_id;
    }

    public void setPart_id(int part_id) {
        this.part_id = part_id;
    }

    public List<SignData> getSign_data() {
        return sign_data;
    }

    public void setSign_data(List<SignData> sign_data) {
        this.sign_data = sign_data;
    }

    public static class SignData{
        private String sign_time;
        private double distance;
        private double duration;

        public String getSign_time() {
            return sign_time;
        }

        public void setSign_time(String sign_time) {
            this.sign_time = sign_time;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public double getDuration() {
            return duration;
        }

        public void setDuration(double duration) {
            this.duration = duration;
        }
    }

}
