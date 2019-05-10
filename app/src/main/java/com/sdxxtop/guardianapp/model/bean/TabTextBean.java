package com.sdxxtop.guardianapp.model.bean;

/**
 * @author :  lwb
 * Date: 2019/5/10
 * Desc:
 */
public class TabTextBean {
    private int id;
    private String title;
    private String desc;

    public TabTextBean(int id, String title, String desc) {
        this.id = id;
        this.title = title;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
