package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2020/1/8
 * Desc:
 */
public class CategoryStatusBean {

    public List<CategoryBean> category;
    public List<StatusBean> status;

    public static class CategoryBean {
        public int category_id;
        public String category_name;
    }
    public static class StatusBean {
        public int status_id;
        public String status_name;
    }

}
