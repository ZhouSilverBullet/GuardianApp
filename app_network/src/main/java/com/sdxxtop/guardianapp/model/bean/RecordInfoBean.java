package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2020/1/7
 * Desc:
 */
public class RecordInfoBean {

    public List<ListsBean> lists;

    public static class ListsBean{
        public String title;
        public String score;
        public int type;
        public String data;
        public String add_date;

        public String event_title;
        public String category_name;
    }
}
