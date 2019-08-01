package com.sdxxtop.guardianapp.model.bean;

public class AutoLoginBean {
    /**
     * userid' => 30000013  //用户id
     *        'part_id' => 1  //覆盖本地的部门id
     *        'name' => 'zhouyang', //姓名
     *        'auto_token' => '2B8454948396505EB3220388A1A14D86'     //自动登录token
     *        'expire_time' => '1521708027'                         //失效时间戳
     */

    private int userid;
    private int part_id;
    private String name;
    private String auto_token;
    private int expire_time;

    public TrackInfoBean track_info;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
