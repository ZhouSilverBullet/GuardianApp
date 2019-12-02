package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/12/2
 * Desc:
 */
public class RecordLogBean {

    public List<EventLogBean> event_log;

    public class EventLogBean {
        public String name;
        public String part_name;
        public String operazione;
        public int type;

        /**
         * type : 1 = 流转
         **/
        public String path_name;
        public String operate_time;
        public String extra;

        /**
         * type : 2 = 联办
         **/
        public String union_name;

        /**
         * type : 3 = 派发
         **/
        public String settle_part;
        public String settle_name;

        /**
         * type : 4 = 解决
         * type : 5 = 验收
         **/
        public String img;
        public String video;

        /**
         * type : 6 = 验收不通过
         * type : 7 = 无法解决
         * type : 8 = 删除
         * type : 9 = 驳回
         * type : 10 = 撤销删除
         **/
    }

}
