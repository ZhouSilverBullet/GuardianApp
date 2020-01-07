package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2020/1/7
 * Desc:
 */
public class KqstDayBean {

    /**
     * is_leak : 2
     * sign_num : 2
     * sign_name : 上班,下班
     * work_type : 1
     * sign_date : 2020-0-1 09:00:00,2020-0-1 18:00:00
     * is_need : 1
     * is_rest : 2
     * sign_log : []
     * sign_count : 0
     * data : 09:00,18:00
     * treno_time : 0
     */

    public int is_leak;
    public int sign_num;
    public String sign_name;
    public int work_type;
    public String sign_date;
    public int is_need;
    public int is_rest;
    public int sign_count;
    public String data;
    public String treno_time;
    public List<SignLogBean> sign_log;

    public static class SignLogBean{
        public String sign_time;
        public int status;
        public String sys_date;
        public String sign_name;
        public String address;
    }

}
