package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2020/1/7
 * Desc:
 */
public class XljlDateBean {

    public float total_distance;
    public String sign_date;

    public List<SignLogBean> sign_log;

    public static class SignLogBean {
        public String total_distance;
        public String sign_date;
    }
}
