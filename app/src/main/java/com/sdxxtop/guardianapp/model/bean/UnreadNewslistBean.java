package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/6/1
 * Desc:
 */
public class UnreadNewslistBean {

    private int event_type;
    private List<MessageInfoBean> info;

    public int getEvent_type() {
        return event_type;
    }

    public void setEvent_type(int event_type) {
        this.event_type = event_type;
    }

    public List<MessageInfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<MessageInfoBean> info) {
        this.info = info;
    }

    public static class MessageInfoBean{
       private int event_id;
       private String title;
       private String update_time;


       private int patrol_id;
       private String rectify_date;

        public int getPatrol_id() {
            return patrol_id;
        }

        public void setPatrol_id(int patrol_id) {
            this.patrol_id = patrol_id;
        }

        public String getRectify_date() {
            return rectify_date;
        }

        public void setRectify_date(String rectify_date) {
            this.rectify_date = rectify_date;
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

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }

}
