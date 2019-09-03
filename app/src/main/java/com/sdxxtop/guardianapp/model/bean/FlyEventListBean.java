package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/9/3
 * Desc:
 */
public class FlyEventListBean {
    public List<MonthTash> month_uav;
    public List<DayTash> day_tash;

    public class DayTash{
        public int id;
        public String title;
        public String add_time;
        public int part_id;
        public int upload_type;
        public String part_name;
    }
    public class MonthTash{
        public String add_date;
        public int count;
    }
}
