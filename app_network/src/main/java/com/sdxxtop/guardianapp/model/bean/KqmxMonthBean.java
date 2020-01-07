package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2020/1/7
 * Desc:
 */
public class KqmxMonthBean {

    public List<SignLogBean> sign_log;

    public static class SignLogBean {
        public String date;
        public String week;
        public String time;
        public String sign_log;
        public String desc;
    }
}
