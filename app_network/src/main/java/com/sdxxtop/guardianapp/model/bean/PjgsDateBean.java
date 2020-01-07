package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2020/1/7
 * Desc:
 */
public class PjgsDateBean {
    public float treno_date;
    public List<SignBean> sign;

    public static class SignBean{
        public String sign_date;
        public float treno_time;
    }
}
