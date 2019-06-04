package com.sdxxtop.guardianapp.model.bean;

/**
 * @author :  lwb
 * Date: 2019/5/30
 * Desc:
 */
public class UnreadIndexAdapterBean {

    private int count;
    private String name;
    private String title;
    private int type;
    private String time;


    public UnreadIndexAdapterBean(int count, String name, String title, int type, String time) {
        this.count = count;
        this.name = name;
        this.title = title;
        this.type = type;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
