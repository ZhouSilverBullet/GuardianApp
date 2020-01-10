package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2020/1/8
 * Desc:
 */
public class RecordDetailBean {

    public List<ListsBean> lists;

    public int is_clock;
    public int is_face;

    public static class ListsBean {
        /**
         * sign_time : 09:00
         * sign_name : 上班
         * sign_info : {"sign_id":1,"sign_time":"14:54","minute":0,"status":5,"sign_name":"上班","sys_date":"09:00","sign_data":"","address":"",
         * "status_name":"迟到"}
         */

        public String sign_time;
        public String sign_name;
        public SignInfoBean sign_info;
    }

    public static class SignInfoBean {
        /**
         * sign_id : 1
         * sign_time : 14:54
         * minute : 0
         * status : 5
         * sign_name : 上班
         * sys_date : 09:00
         * sign_data :
         * address :
         * status_name : 迟到
         */
        public int sign_id;
        public String sign_time;
        public int minute;
        public int status;
        public String sign_name;
        public String sys_date;
        public String sign_data;
        public String address;
        public String status_name;
    }
}
