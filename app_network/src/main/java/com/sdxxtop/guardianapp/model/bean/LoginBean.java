package com.sdxxtop.guardianapp.model.bean;

public class LoginBean {

    /**
     * auto_token : BF991ACC6A9802BF9F81B3D23985DC44
     * expire_time : 1556162821
     * name : 周周
     * userid : 2
     * mobile : 18618474505
     * position : 1
     * part_name : 罗庄区
     * part_id : 1
     */

    private String auto_token;
    private int expire_time;
    private String name;
    private int userid;
    private String mobile;
    private int position;
    private String part_name;
    private int part_id;
    private String img;
    private int is_track;  //是否有查看个人轨迹权限  1是  2否


    public int getIs_track() {
        return is_track;
    }

    public void setIs_track(int is_track) {
        this.is_track = is_track;
    }

    public TrackInfoBean track_info;

    public String getAuto_token() {
        return auto_token;
    }

    public void setAuto_token(String auto_token) {
        this.auto_token = auto_token;
    }

    public int getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(int expire_time) {
        this.expire_time = expire_time;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
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
}
