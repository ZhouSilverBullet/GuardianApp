package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2020/1/7
 * Desc:
 */
public class GztjMonthBean {

    public List<SignLogBean> data;

    public static class SignLogBean {
        public String title;
        public String matter_score;
        public List<SignScoreBean> sign_score;
    }
    public static class SignScoreBean{
        public int category_id;
        public String score;
        public String category_name;
    }
}
