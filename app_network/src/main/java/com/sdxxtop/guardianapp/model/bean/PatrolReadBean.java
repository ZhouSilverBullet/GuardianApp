package com.sdxxtop.guardianapp.model.bean;

/**
 * @author :  lwb
 * Date: 2019/5/30
 * Desc:
 */
public class PatrolReadBean {

    private int patrol_id;
    private int userid;
    private int part_id;
    private String place;
    private String longitude;
    private String title;
    private String img;
    private String video;
    private String content;
    private String add_date;
    private String add_time;
    private String rectify_date;
    /****** 复查 *******/
    private String check_img;
    private String check_video;
    private String check_content;
    private String check_date;
    private String check_time;
    private int status;


    public String getCheck_video() {
        return check_video;
    }

    public void setCheck_video(String check_video) {
        this.check_video = check_video;
    }

    public int getPatrol_id() {
        return patrol_id;
    }

    public void setPatrol_id(int patrol_id) {
        this.patrol_id = patrol_id;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdd_date() {
        return add_date;
    }

    public void setAdd_date(String add_date) {
        this.add_date = add_date;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getRectify_date() {
        return rectify_date;
    }

    public void setRectify_date(String rectify_date) {
        this.rectify_date = rectify_date;
    }

    public String getCheck_img() {
        return check_img;
    }

    public void setCheck_img(String check_img) {
        this.check_img = check_img;
    }

    public String getCheck_content() {
        return check_content;
    }

    public void setCheck_content(String check_content) {
        this.check_content = check_content;
    }

    public String getCheck_date() {
        return check_date;
    }

    public void setCheck_date(String check_date) {
        this.check_date = check_date;
    }

    public String getCheck_time() {
        return check_time;
    }

    public void setCheck_time(String check_time) {
        this.check_time = check_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
