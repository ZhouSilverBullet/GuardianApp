package com.sdxxtop.guardianapp.model.bean;

/**
 * @author :  lwb
 * Date: 2019/9/6
 * Desc:
 */
public class DataEntry {
    public DataEntry(int resId, String title, String desc, int type) {
        this.resId = resId;
        this.title = title;
        this.desc = desc;
        this.type = type;
    }

    public int resId;
    public int type;
    public String title;
    public String desc;

    public DataEntry() {
    }
}
