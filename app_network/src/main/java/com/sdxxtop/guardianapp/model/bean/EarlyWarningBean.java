package com.sdxxtop.guardianapp.model.bean;

/**
 * @author :  lwb
 * Date: 2019/7/10
 * Desc:
 */
public class EarlyWarningBean {

    private int early_id;
    private int device_id;
    private int type;
    private int status;
    private String early_content;
    private String add_time;
    private String device_name;
    private String longitude;
    private int userid;
    private String site_name;
    private String liable_name;
    private String liable_mobile;
    private String address;
    private int is_finish;

    public int getEarly_id() {
        return early_id;
    }

    public void setEarly_id(int early_id) {
        this.early_id = early_id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEarly_content() {
        return early_content;
    }

    public void setEarly_content(String early_content) {
        this.early_content = early_content;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getLiable_name() {
        return liable_name;
    }

    public void setLiable_name(String liable_name) {
        this.liable_name = liable_name;
    }

    public String getLiable_mobile() {
        return liable_mobile;
    }

    public void setLiable_mobile(String liable_mobile) {
        this.liable_mobile = liable_mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIs_finish() {
        return is_finish;
    }

    public void setIs_finish(int is_finish) {
        this.is_finish = is_finish;
    }
}
