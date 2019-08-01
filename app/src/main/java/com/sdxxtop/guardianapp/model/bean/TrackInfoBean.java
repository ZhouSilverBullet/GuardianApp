package com.sdxxtop.guardianapp.model.bean;

/**
 * @author :  lwb
 * Date: 2019/7/11
 * Desc:
 */
public class TrackInfoBean {

    private String key;
    private long sid;
    private long tid;
    private long trid;
    private int userid;
    private String add_time;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public long getTrid() {
        return trid;
    }

    public void setTrid(long trid) {
        this.trid = trid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}

