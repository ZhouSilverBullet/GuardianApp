package com.xuxin.guardianapp.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author :  lwb
 * Date: 2019/3/18
 * Desc:
 */
public class LearnNewsBean implements MultiItemEntity {

    private int article_id;
    private String title;
    private String title_img;
    private String source;
    private int comment_num;
    private String content;
    private String add_time;
    private String article_path;
    private int type = 1;
    private String video_path;

    public String getVideo_path() {
        return video_path;
    }

    public void setVideo_path(String video_path) {
        this.video_path = video_path;
    }

    public String getArticle_path() {
        return article_path;
    }

    public void setArticle_path(String article_path) {
        this.article_path = article_path;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_img() {
        return title_img;
    }

    public void setTitle_img(String title_img) {
        this.title_img = title_img;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }
}

