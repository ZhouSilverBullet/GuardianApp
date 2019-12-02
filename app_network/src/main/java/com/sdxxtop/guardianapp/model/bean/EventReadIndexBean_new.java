package com.sdxxtop.guardianapp.model.bean;

/**
 * @author :  lwb
 * Date: 2019/6/1
 * Desc:
 */
public class EventReadIndexBean_new {

    public String title;
    public String video;
    public String img;
    public String add_time;
    public int patrol_type;
    public String place;
    public String patrol_name;
    public String longitude;
    public String content;
    public int status;


    public int operate_status;
    public int settle_status;

    public int is_modify;       //当前账号和状态是否能验收事件 1:有 2:否,
    public int is_finish;      //当前账号和状态是否能解决事件 1:有 2:否,
    public int is_claim_auth; //当前账号和状态是否能认领与流转事件 1:有 2:否,
    public int is_claim;     //是否是认领时间 1、是 2、否
    public String part_name;
    public String supplement;


    public UnionBean union;//联办信息
    public TurnBean turn;//流转信息
    public ExtraDateBean extra_date;//认领信息
    public SendBean send;//派发信息
    public SolveBean solve;//解决信息
    public CompletedBean completed;//已完成 == 验收
    public ClaimCompletedBean claim_completed;//认领事件完成
    public RifiutaBean rifiuta;//驳回

    public class RifiutaBean {
        public String extra;
        public String operate_time;
    }

    public class UnionBean {
        public String extra;
        public String part_name;
        public String add_time;
    }

    public class TurnBean {
        public String part_name;
        public String extra;
        public String operate_time;
    }

    public static class ExtraDateBean {
        public String send_time;
        public String operate_date;
        public String send_name;
        public int status;
        public String name;
        public int important_type;
    }

    public class SendBean {
        public String send_time;
        public String operate_date;
        public String send_name;
        public String name;
        public int important_type;
    }

    public static class SolveBean {//最近一条的解决信息
        public String extra;
        public String operate_time;
        public String video;
        public String img;
    }

    public class CompletedBean {//已完成
        public String extra;
        public String operate_time;
        public int status;
        public String video;
        public String img;
    }

    public class ClaimCompletedBean {
        public int appraise;
        public String extra;
        public String operate_time;

        public String getAppraiseStr() {
            String str = "满意";
            switch (appraise) {
                case 1:
                    str = "满意";
                    break;
                case 2:
                    str = "一般";
                    break;
                case 3:
                    str = "不满意";
                    break;
            }
            return str;
        }
    }
}
