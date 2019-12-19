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
//    public CompletedBean completed;//已完成 == 验收
//    public ClaimCompletedBean claim_completed;//认领事件完成
    public RifiutaBean rifiuta;//驳回
    public ClaimBean claim;//认领人
    public CheckBean check;//验收

    public class CheckBean {
        public String check_name;//验收人名称
        public String check_part;//验收人所属部门
        public String img;//验收图片
        public String video;//验收视频
        public String extra;//评价信息
        public String appraise;//评价满意度
        public String check_time;//验收时间
    }

    public class ClaimBean {
        public String send_name;//认领人
        public String send_part_name;//认领人所属部门
        public String send_time;//认领时间
    }

    public class RifiutaBean {
        public String extra;
        public String operate_time;
    }

    public class UnionBean {
        public String extra;//联办原因
        public String part_name;//联办部门
        public String add_time;//联办时间
    }

    public class TurnBean {
        public String part_name;//流转部门
        public String extra;//流转原因
        public String operate_time;//流转时间
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
        public String send_name;//派发人名称
        public String send_part_name;//派发人所属部门
        public String send_time;//派发时间
        public String settle_name;//解决人名称
        public String settle_part;//解决人所属部门
        public String important_type;//事件重要性
        public String end_date;//事件截止时间
    }

    public static class SolveBean {//解决信息
        public String settle_time;//解决时间
        public String settle_reason;//解决描述
        public String finish_img;//解决图片
        public String finish_video;//解决视频
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
