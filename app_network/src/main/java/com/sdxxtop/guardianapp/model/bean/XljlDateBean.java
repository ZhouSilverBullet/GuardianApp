package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2020/1/7
 * Desc:
 */
public class XljlDateBean {
    public float avg;
    public float max;

    public List<SignLogBean> distance;

    public static class SignLogBean {
        public float total_distance;
        public String sign_date;
    }
}
