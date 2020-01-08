package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2020/1/7
 * Desc:
 */
public class GzmxDateBean {

    public List<EventBean> event;

    public static class EventBean {
        public int status;
        public String add_time;
        public String title;
        public int category_id;
        public String category_name;
        public String score;
    }
}
