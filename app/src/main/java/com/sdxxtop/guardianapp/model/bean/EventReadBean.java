package com.sdxxtop.guardianapp.model.bean;

public class EventReadBean {

    /**
     * event_id : 13
     * userid : 2
     * title : 哈哈哈
     * content : 好解决
     * place : 上地街道上地东里
     * end_date : 1000-01-01
     * status : 1
     * add_time : 2019-04-11 12:05:11
     * send_id : 0
     * important_type : 0
     * path_type : 1
     * patrol_type : 1
     * extra :
     * check_time : 1000-01-01 00:00:00
     * send_time : 1000-01-01 00:00:00
     * img :
     * is_modify : 2
     * is_finish : 2
     */

    private int event_id;
    private int userid;
    private String title;
    private String content;
    private String place;
    private String end_date;
    private int status;
    private String add_time;
    private int send_id;
    private int important_type;
    private int path_type;
    private int patrol_type;
    private String extra;
    private String check_time;
    private String send_time;
    private String finish_time;
    private String img;
    private int is_modify;
    private int is_finish;

    private String check_img;
    private String finish_img;
    private String finish_desc;
    private String longitude;
    private String send_name;

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getSend_id() {
        return send_id;
    }

    public void setSend_id(int send_id) {
        this.send_id = send_id;
    }

    public int getImportant_type() {
        return important_type;
    }

    public void setImportant_type(int important_type) {
        this.important_type = important_type;
    }

    public int getPath_type() {
        return path_type;
    }

    public void setPath_type(int path_type) {
        this.path_type = path_type;
    }

    public int getPatrol_type() {
        return patrol_type;
    }

    public void setPatrol_type(int patrol_type) {
        this.patrol_type = patrol_type;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getCheck_time() {
        return check_time;
    }

    public void setCheck_time(String check_time) {
        this.check_time = check_time;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getIs_modify() {
        return is_modify;
    }

    public void setIs_modify(int is_modify) {
        this.is_modify = is_modify;
    }

    public int getIs_finish() {
        return is_finish;
    }

    public void setIs_finish(int is_finish) {
        this.is_finish = is_finish;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getCheck_img() {
        return check_img;
    }

    public void setCheck_img(String check_img) {
        this.check_img = check_img;
    }

    public String getFinish_img() {
        return finish_img;
    }

    public void setFinish_img(String finish_img) {
        this.finish_img = finish_img;
    }

    public String getFinish_desc() {
        return finish_desc;
    }

    public void setFinish_desc(String finish_desc) {
        this.finish_desc = finish_desc;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }
}
