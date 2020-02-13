package com.sdxxtop.guardianapp.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author :  lwb
 * Date: 2019/6/1
 * Desc:
 */
public class MediaBean implements MultiItemEntity {

    private String path;
    private int type;  // 1:图片  2:视频

    public MediaBean(String path, int type) {
        this.path = path;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
