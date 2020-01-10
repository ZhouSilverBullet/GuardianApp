package com.sdxxtop.guardianapp.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/6/14
 * Desc:
 */
public class ArticleIndexBean implements Serializable {
    private List<ShowBean> show;

    public List<ShowBean> getShow() {
        return show;
    }

    public void setShow(List<ShowBean> show) {
        this.show = show;
    }

    public static class ShowBean implements Serializable{
        private int is_show;
        private String title;

        public int getIs_show() {
            return is_show;
        }

        public void setIs_show(int is_show) {
            this.is_show = is_show;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
