package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/9/5
 * Desc:
 */
public class WorkIndexBean {
    public int is_statistics;
    public int is_working;
    public int is_enterprise;
    public int is_sectoral;
    public int is_report;
    public int is_patrol;
    public int report;
    public int complete;
    public String part_name;

    public int pending_count;
    public List<MonthComplete> month_complete;
    public List<PendingEvent> pending_event;

    public class MonthComplete {
        public float complete_rate;
        public String time;
    }
    public class PendingEvent {
        public int event_id;
        public String title;
        public String end_date;
        public String day;
    }
}
