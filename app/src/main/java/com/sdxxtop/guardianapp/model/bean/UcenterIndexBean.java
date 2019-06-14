package com.sdxxtop.guardianapp.model.bean;

import com.sdxxtop.guardianapp.utils.GuardianUtils;

public class UcenterIndexBean {
    /**
     * "name" : 周周
     * "userid" : 2
     * "part_id" : 1
     * "position" : 1,/职位(1:网格员 2: 企业员工 3:街道管理员 4:区级管理员)
     * "img" : http://xuxingtest.oss-cn-hangzhou.aliyuncs.com/face/20190412100430281648.png
     * "part_name" : 罗庄区,//
     * "type":1,  //单位级别(1:区级 2: 乡镇 3:企业)
     */
    private String name;
    private int userid;
    private int part_id;
    private int position;
    private String img;
    private String part_name;
    private int type;
    private int unread_count;

    /***** 权限 *******/
    private int is_report;
    private int is_event;
    private int is_guider;
    private int is_business;
    private int is_part;

    public int getIs_report() {
        return is_report;
    }

    public void setIs_report(int is_report) {
        this.is_report = is_report;
    }

    public int getIs_event() {
        return is_event;
    }

    public void setIs_event(int is_event) {
        this.is_event = is_event;
    }

    public int getIs_guider() {
        return is_guider;
    }

    public void setIs_guider(int is_guider) {
        this.is_guider = is_guider;
    }

    public int getIs_business() {
        return is_business;
    }

    public void setIs_business(int is_business) {
        this.is_business = is_business;
    }

    public int getIs_part() {
        return is_part;
    }

    public void setIs_part(int is_part) {
        this.is_part = is_part;
    }

    public int getUnread_count() {
        return unread_count;
    }

    public void setUnread_count(int unread_count) {
        this.unread_count = unread_count;
    }

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

    public int getPart_id() {
        return part_id;
    }

    public void setPart_id(int part_id) {
        this.part_id = part_id;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    //1:网格员 2: 企业员工 3:街道管理员 4:区级管理员
    public String getStringPosition() {
        return GuardianUtils.getJobName(getPosition());
    }
}
