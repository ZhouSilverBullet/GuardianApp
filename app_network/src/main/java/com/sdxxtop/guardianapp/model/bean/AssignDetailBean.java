package com.sdxxtop.guardianapp.model.bean;


import java.io.Serializable;
import java.util.List;

/**
 * Date:2020-02-14
 * author:lwb
 * Desc:
 */
public class AssignDetailBean implements Serializable {


    /**
     * list : {"assign_id":55,"title":"玲珑街道","type":2,"grade":3,"img":[""],"assign_part_id":19,"userid":50172,"cat_id":58,"content":"玲珑街道","update_time":"2020-02-15 09:45:05","add_date":"2020-02-15","due_time":"2020-02-21","cat_name":"测试你好","user_name":"张海波（测试）","assign_part_name":"区环保分局","child":[{"duty_id":0,"exec_id":46,"duty_part_id":1,"settle_time":"未确认","finish_time":"未解决","status":1,"content":"","img":[],"reject_desc":"暂无","status_name":"待部门确认","duty_name":"待部门确认","duty_part_name":"罗庄区"},{"duty_id":0,"exec_id":47,"duty_part_id":19,"settle_time":"未确认","finish_time":"未解决","status":1,"content":"","img":[],"reject_desc":"暂无","status_name":"待部门确认","duty_name":"待部门确认","duty_part_name":"区环保分局"}]}
     * dispaly_status : 2
     * dispaly_again : 1
     */

    public ListBean list;
    public int dispaly_status;
    public int dispaly_again;
    public int dispaly_urge;


    public static class ListBean implements Serializable {
        /**
         * assign_id : 55
         * title : 玲珑街道
         * type : 2
         * grade : 3
         * img : [""]
         * assign_part_id : 19
         * userid : 50172
         * cat_id : 58
         * content : 玲珑街道
         * update_time : 2020-02-15 09:45:05
         * add_date : 2020-02-15
         * due_time : 2020-02-21
         * cat_name : 测试你好
         * user_name : 张海波（测试）
         * assign_part_name : 区环保分局
         * child : [{"duty_id":0,"exec_id":46,"duty_part_id":1,"settle_time":"未确认","finish_time":"未解决","status":1,"content":"","img":[],"reject_desc":"暂无","status_name":"待部门确认","duty_name":"待部门确认","duty_part_name":"罗庄区"},{"duty_id":0,"exec_id":47,"duty_part_id":19,"settle_time":"未确认","finish_time":"未解决","status":1,"content":"","img":[],"reject_desc":"暂无","status_name":"待部门确认","duty_name":"待部门确认","duty_part_name":"区环保分局"}]
         */

        public int assign_id;
        public String title;
        public int type;
        public int grade;
        public int assign_part_id;
        public int userid;
        public int cat_id;
        public int due_day;
        public String content;
        public String update_time;
        public String add_date;
        public String due_time;
        public String cat_name;
        public String user_name;
        public String assign_part_name;
        public List<String> img;
        public List<String> video;
        public List<ChildBean> child;

        public String getLevelStr() {
            String str = "低";
            switch (grade) {
                case 1:
                    str = "低";
                    break;
                case 2:
                    str = "中";
                    break;
                case 3:
                    str = "高";
                    break;
                default:
                    str = "低";
                    break;
            }
            return str;
        }

        public static class ChildBean implements Serializable {
            /**
             * exec_id : 46
             * duty_id : 0  //执行id
             * duty_part_id : 1
             * settle_time : 未确认
             * finish_time : 未解决
             * status : 1
             * content :
             * img : []
             * reject_desc : 暂无
             * status_name : 待部门确认
             * duty_name : 待部门确认
             * duty_part_name : 罗庄区
             */

            public int duty_id;
            public int exec_id;
            public int duty_part_id;
            public String settle_time;
            public String finish_time;
            public int status;
            public String content;
            public String reject_desc;
            public String status_name;
            public String duty_name;
            public String duty_part_name;
            public List<String> img;
            public List<String> video;
            public List<String> files;
        }
    }
}
