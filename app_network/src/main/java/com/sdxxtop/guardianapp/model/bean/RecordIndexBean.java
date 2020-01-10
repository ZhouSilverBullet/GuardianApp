package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2020/1/8
 * Desc:
 */
public class RecordIndexBean {
    public ListsBean lists;
    public List<EchartsBean> echarts;

    public static class ListsBean {
        public float usually_score;
        public float patrol_score;
        public float matter_score;
        public float other_score;
        public String score;
    }

    public static class EchartsBean {
        public float score;
        public float month;
    }

}
