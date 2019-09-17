package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/9/17
 * Desc:
 */
public class GridEventListBean {

    public List<GridListBean> event;
    public List<GridListBean> partol;

    public class GridListBean {
        public int event_id;
        public int patrol_id;
        public String title;
        public String place;
        public int status;
        public String add_time;
    }
}
