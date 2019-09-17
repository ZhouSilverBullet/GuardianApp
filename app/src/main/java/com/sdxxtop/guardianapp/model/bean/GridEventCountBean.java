package com.sdxxtop.guardianapp.model.bean;

/**
 * @author =  lwb
 * Date= 2019/9/17
 * Desc=
 */
public class GridEventCountBean {

    public GridEventCount solve;
    public GridEventCount report;
    public GridEventCount claim;
    public GridEventCount partol;

    public class GridEventCount {
        public int stay_solve;//待解决数量
        public int stay_check;//待验收数量
        public int complete;//已完成数量
        public int count;//总数


        public int distribute; //待派发
        public int stay_review; //待复查

    }
}
