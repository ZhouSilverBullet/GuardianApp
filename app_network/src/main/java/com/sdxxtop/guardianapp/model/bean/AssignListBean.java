package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * Date:2020-02-14
 * author:lwb
 * Desc:
 */
public class AssignListBean {

    public List<ListBean> list;


    public static class ListBean {
        public int assign_id;
        public String title;
        public int grade;
        public int status;
        public String update_time;
        public String add_date;
        public String due_time;
        public int due_day;
        public String due_day_desc;
        public int exec_id;
    }
}
