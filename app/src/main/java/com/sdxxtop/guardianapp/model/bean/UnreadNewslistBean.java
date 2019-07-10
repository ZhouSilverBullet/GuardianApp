package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/6/1
 * Desc:
 */
public class UnreadNewslistBean {

    private int event_type;
    private List<EventItemBean> overdue_event;//超期事件
    private List<EventItemBean> event_expire;//到期事件
    private List<EventItemBean> whole_event;//待验收事件
    private List<EventItemBean> reject_data;//驳回事件

    public int getEvent_type() {
        return event_type;
    }

    public void setEvent_type(int event_type) {
        this.event_type = event_type;
    }

    public List<EventItemBean> getOverdue_event() {
        if (overdue_event != null && overdue_event.size() > 0) {
            for (EventItemBean eventItemBean : overdue_event) {
                eventItemBean.setClassify("超期事件");
            }
        }
        return overdue_event;
    }

    public void setOverdue_event(List<EventItemBean> overdue_event) {
        this.overdue_event = overdue_event;
    }

    public List<EventItemBean> getEvent_expire() {
        if (event_expire != null && event_expire.size() > 0) {
            for (EventItemBean eventItemBean : event_expire) {
                eventItemBean.setClassify("到期事件");
            }
        }
        return event_expire;
    }

    public void setEvent_expire(List<EventItemBean> event_expire) {
        this.event_expire = event_expire;
    }

    public List<EventItemBean> getWhole_event() {
        if (whole_event != null && whole_event.size() > 0) {
            for (EventItemBean eventItemBean : whole_event) {
                eventItemBean.setClassify("待验收事件");
            }
        }
        return whole_event;
    }

    public void setWhole_event(List<EventItemBean> whole_event) {
        this.whole_event = whole_event;
    }

    public List<EventItemBean> getReject_data() {
        if (reject_data != null && reject_data.size() > 0) {
            for (EventItemBean eventItemBean : reject_data) {
                eventItemBean.setClassify("驳回事件");
            }
        }
        return reject_data;
    }

    public void setReject_data(List<EventItemBean> reject_data) {
        this.reject_data = reject_data;
    }

    public static class EventItemBean {
        private String classify;
        private int event_id;
        private String title;
        private String update_time;

        private int patrol_id;
        private String rectify_date;

        //扬尘监测
        private int early_id;
        private String content;
        private String add_time;

        public int getEarly_id() {
            return early_id;
        }

        public void setEarly_id(int early_id) {
            this.early_id = early_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getRectify_date() {
            return rectify_date;
        }

        public void setRectify_date(String rectify_date) {
            this.rectify_date = rectify_date;
        }

        public int getPatrol_id() {
            return patrol_id;
        }

        public void setPatrol_id(int patrol_id) {
            this.patrol_id = patrol_id;
        }

        public String getClassify() {
            return classify;
        }

        public void setClassify(String classify) {
            this.classify = classify;
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
