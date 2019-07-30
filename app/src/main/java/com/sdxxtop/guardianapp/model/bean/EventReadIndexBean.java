package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/6/1
 * Desc:
 */
public class EventReadIndexBean {

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


    public List<ExtraDateBean> extra_date;//事件的所有派发信息
    public List<ExtraDateBean> claim_date;//事件的所有认领信息

    public List<ExtraBean> extra;//事件的所有无法解决信息
    public List<SolveBean> solve;//最近一条的解决信息
    public List<ExtraInfoBean> extra_info;//验收不通过
    public List<CompletedBean> completed;//已完成
    public List<CompletedBean> claim_completed;//评价已完成
    public List<CirculationBean> circulation;//流转信息

    public static class CirculationBean {
        private int event_id;
        private int part_id;
        private String reason;
        private String add_time;
        private String part_name;

        public int getEvent_id() {
            return event_id;
        }

        public void setEvent_id(int event_id) {
            this.event_id = event_id;
        }

        public int getPart_id() {
            return part_id;
        }

        public void setPart_id(int part_id) {
            this.part_id = part_id;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getPart_name() {
            return part_name;
        }

        public void setPart_name(String part_name) {
            this.part_name = part_name;
        }
    }

    public static class ExtraDateBean {//事件的所有派发信息
        private int userid;
        private int event_id;
        private int send_id;
        private int status;
        private String send_time;
        private String send_name;
        private String name;
        private String operate_date;
        private int important_type;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getEvent_id() {
            return event_id;
        }

        public void setEvent_id(int event_id) {
            this.event_id = event_id;
        }

        public int getImportant_type() {
            return important_type;
        }

        public void setImportant_type(int important_type) {
            this.important_type = important_type;
        }

        public String getOperate_date() {
            return operate_date;
        }

        public void setOperate_date(String operate_date) {
            this.operate_date = operate_date;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getSend_id() {
            return send_id;
        }

        public void setSend_id(int send_id) {
            this.send_id = send_id;
        }

        public String getSend_time() {
            return send_time;
        }

        public void setSend_time(String send_time) {
            this.send_time = send_time;
        }

        public String getSend_name() {
            return send_name;
        }

        public void setSend_name(String send_name) {
            this.send_name = send_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ExtraBean {//事件的所有无法解决信息
        private String extra;
        private String operate_time;

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }

        public String getOperate_time() {
            return operate_time;
        }

        public void setOperate_time(String operate_time) {
            this.operate_time = operate_time;
        }
    }

    public static class SolveBean {//最近一条的解决信息
        private String extra;
        private String operate_time;
        private String video;
        private String img;
        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }

        public String getOperate_time() {
            return operate_time;
        }

        public void setOperate_time(String operate_time) {
            this.operate_time = operate_time;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

    public static class ExtraInfoBean {//验收不通过
        private String extra;
        private String operate_time;
        private String video;
        private String img;
        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }

        public String getOperate_time() {
            return operate_time;
        }

        public void setOperate_time(String operate_time) {
            this.operate_time = operate_time;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

    public static class CompletedBean {//已完成
        private String extra;
        private String operate_time;
        private int appraise;
        private String video;
        private int status;
        private String img;

        public String getAppraiseStr(){
            String str = "满意";
            switch (appraise){
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

        public int getAppraise() {
            return appraise;
        }

        public void setAppraise(int appraise) {
            this.appraise = appraise;
        }

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }

        public String getOperate_time() {
            return operate_time;
        }

        public void setOperate_time(String operate_time) {
            this.operate_time = operate_time;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

}
