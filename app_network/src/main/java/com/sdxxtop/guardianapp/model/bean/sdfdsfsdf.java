package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/6/1
 * Desc:
 */
public class sdfdsfsdf {


    /**
     * code : 200
     * msg : 成功
     * data : {"settle_id":0,"check_id":0,"event_id":975,"title":"联办派发","userid":50396,"video":"","img":"http://xuxingtest.oss-cn-hangzhou.aliyuncs
     * .com/envir/20190601194949457999.png","add_time":"2019-06-01 19:49:49","patrol_type":2,"place":"西北旺镇北京晨奥高科技发展有限公司","path_type":24,"important_type":1,
     * "content":"测试联办","send_id":0,"part_id":24,"status":1,"operate_status":2,"settle_status":0,"end_date":"2019-06-07","extra_date":[{"event_id":975,
     * "settle_id":50396,"status":2,"send_id":50393,"send_time":"2019-06-01","send_name":"亮亮同学","operate_date":"2019-06-07","important_type":1}],"extra":[],
     * "solve":[],"extra_info":[],"completed":[],"is_modify":2,"is_finish":2,"send_name":"","settle_name":"","part_name":"盛庄街道"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * settle_id : 0
         * check_id : 0
         * event_id : 975
         * title : 联办派发
         * userid : 50396
         * video :
         * img : http://xuxingtest.oss-cn-hangzhou.aliyuncs.com/envir/20190601194949457999.png
         * add_time : 2019-06-01 19:49:49
         * patrol_type : 2
         * place : 西北旺镇北京晨奥高科技发展有限公司
         * path_type : 24
         * important_type : 1
         * content : 测试联办
         * send_id : 0
         * part_id : 24
         * status : 1
         * operate_status : 2
         * settle_status : 0
         * end_date : 2019-06-07
         * extra_date : [{"event_id":975,"settle_id":50396,"status":2,"send_id":50393,"send_time":"2019-06-01","send_name":"亮亮同学",
         * "operate_date":"2019-06-07","important_type":1}]
         * extra : []
         * solve : []
         * extra_info : []
         * completed : []
         * is_modify : 2
         * is_finish : 2
         * send_name :
         * settle_name :
         * part_name : 盛庄街道
         */

        private int settle_id;
        private int check_id;
        private int event_id;
        private String title;
        private int userid;
        private String video;
        private String img;
        private String add_time;
        private int patrol_type;
        private String place;
        private int path_type;
        private int important_type;
        private String content;
        private int send_id;
        private int part_id;
        private int status;
        private int operate_status;
        private int settle_status;
        private String end_date;
        private int is_modify;
        private int is_finish;
        private String send_name;
        private String settle_name;
        private String part_name;
        private List<ExtraDateBean> extra_date;
        private List<?> extra;
        private List<?> solve;
        private List<?> extra_info;
        private List<?> completed;

        public int getSettle_id() {
            return settle_id;
        }

        public void setSettle_id(int settle_id) {
            this.settle_id = settle_id;
        }

        public int getCheck_id() {
            return check_id;
        }

        public void setCheck_id(int check_id) {
            this.check_id = check_id;
        }

        public int getEvent_id() {
            return event_id;
        }

        public void setEvent_id(int event_id) {
            this.event_id = event_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
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

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public int getPatrol_type() {
            return patrol_type;
        }

        public void setPatrol_type(int patrol_type) {
            this.patrol_type = patrol_type;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public int getPath_type() {
            return path_type;
        }

        public void setPath_type(int path_type) {
            this.path_type = path_type;
        }

        public int getImportant_type() {
            return important_type;
        }

        public void setImportant_type(int important_type) {
            this.important_type = important_type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getSend_id() {
            return send_id;
        }

        public void setSend_id(int send_id) {
            this.send_id = send_id;
        }

        public int getPart_id() {
            return part_id;
        }

        public void setPart_id(int part_id) {
            this.part_id = part_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getOperate_status() {
            return operate_status;
        }

        public void setOperate_status(int operate_status) {
            this.operate_status = operate_status;
        }

        public int getSettle_status() {
            return settle_status;
        }

        public void setSettle_status(int settle_status) {
            this.settle_status = settle_status;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public int getIs_modify() {
            return is_modify;
        }

        public void setIs_modify(int is_modify) {
            this.is_modify = is_modify;
        }

        public int getIs_finish() {
            return is_finish;
        }

        public void setIs_finish(int is_finish) {
            this.is_finish = is_finish;
        }

        public String getSend_name() {
            return send_name;
        }

        public void setSend_name(String send_name) {
            this.send_name = send_name;
        }

        public String getSettle_name() {
            return settle_name;
        }

        public void setSettle_name(String settle_name) {
            this.settle_name = settle_name;
        }

        public String getPart_name() {
            return part_name;
        }

        public void setPart_name(String part_name) {
            this.part_name = part_name;
        }

        public List<ExtraDateBean> getExtra_date() {
            return extra_date;
        }

        public void setExtra_date(List<ExtraDateBean> extra_date) {
            this.extra_date = extra_date;
        }

        public List<?> getExtra() {
            return extra;
        }

        public void setExtra(List<?> extra) {
            this.extra = extra;
        }

        public List<?> getSolve() {
            return solve;
        }

        public void setSolve(List<?> solve) {
            this.solve = solve;
        }

        public List<?> getExtra_info() {
            return extra_info;
        }

        public void setExtra_info(List<?> extra_info) {
            this.extra_info = extra_info;
        }

        public List<?> getCompleted() {
            return completed;
        }

        public void setCompleted(List<?> completed) {
            this.completed = completed;
        }

        public static class ExtraDateBean {
            /**
             * event_id : 975
             * settle_id : 50396
             * status : 2
             * send_id : 50393
             * send_time : 2019-06-01
             * send_name : 亮亮同学
             * operate_date : 2019-06-07
             * important_type : 1
             */

            private int event_id;
            private int settle_id;
            private int status;
            private int send_id;
            private String send_time;
            private String send_name;
            private String operate_date;
            private int important_type;

            public int getEvent_id() {
                return event_id;
            }

            public void setEvent_id(int event_id) {
                this.event_id = event_id;
            }

            public int getSettle_id() {
                return settle_id;
            }

            public void setSettle_id(int settle_id) {
                this.settle_id = settle_id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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

            public String getOperate_date() {
                return operate_date;
            }

            public void setOperate_date(String operate_date) {
                this.operate_date = operate_date;
            }

            public int getImportant_type() {
                return important_type;
            }

            public void setImportant_type(int important_type) {
                this.important_type = important_type;
            }
        }
    }
}
