package com.sdxxtop.guardianapp.model.bean;

/**
 * @author :  lwb
 * Date: 2019/9/6
 * Desc:
 */
public class DataEntry {
    public DataEntry(int resId, String title, String desc) {
        this.resId = resId;
        this.title = title;
        this.desc = desc;
    }

    public int resId;
    public String title;
    public String desc;

    public DataEntry() {
    }
}
