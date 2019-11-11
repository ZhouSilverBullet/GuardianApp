package com.sdxxtop.guardianapp.model.bean;

import java.util.ArrayList;
import java.util.List;

public class MainIndexBean {

    /**
     * name : 周周
     * position : 1
     * part_name : 罗庄区
     * is_face : 2
     * pending_event : [{"title":"指派11","end_date":"2019-04-11","status":2}]
     * add_event : [{"title":"指派22","end_date":"2019-04-11","status":5},{"title":"指派11","end_date":"2019-04-11","status":2},{"title":"test1","end_date":"","status":1},{"title":"test","end_date":"0000-00-00","status":1}]
     */

    private String name;
    private String img;
    private String rotation_img;
    private int position;
    private String part_name;
    private int is_face;
    private int unread_count;

    private List<PendingEventBean> pending_event;
    private List<AddEventBean> add_event;
    private List<AddPatrolBean>add_patrol;
    private List<PendingEventBean>part_event;

    private int is_clock;
    private int is_report;
    private int is_patrol;
    private int is_mail;
    private int is_map;
    private int is_part_event;

    public int getIs_part_event() {
        return is_part_event;
    }

    public void setIs_part_event(int is_part_event) {
        this.is_part_event = is_part_event;
    }

    public List<PendingEventBean> getPart_event() {
        return part_event;
    }

    public void setPart_event(List<PendingEventBean> part_event) {
        this.part_event = part_event;
    }

    public int getIs_clock() {
        return is_clock;
    }

    public void setIs_clock(int is_clock) {
        this.is_clock = is_clock;
    }

    public int getIs_report() {
        return is_report;
    }

    public void setIs_report(int is_report) {
        this.is_report = is_report;
    }

    public int getIs_patrol() {
        return is_patrol;
    }

    public void setIs_patrol(int is_patrol) {
        this.is_patrol = is_patrol;
    }

    public int getIs_mail() {
        return is_mail;
    }

    public void setIs_mail(int is_mail) {
        this.is_mail = is_mail;
    }

    public int getIs_map() {
        return is_map;
    }

    public void setIs_map(int is_map) {
        this.is_map = is_map;
    }

    public int getUnread_count() {
        return unread_count;
    }

    public void setUnread_count(int unread_count) {
        this.unread_count = unread_count;
    }

    public List<AddPatrolBean> getAdd_patrol() {
        return add_patrol;
    }

    public void setAdd_patrol(List<AddPatrolBean> add_patrol) {
        this.add_patrol = add_patrol;
    }

    public String getRotation_img() {
        return rotation_img;
    }

    public void setRotation_img(String rotation_img) {
        this.rotation_img = rotation_img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    public int getIs_face() {
        return is_face;
    }

    public void setIs_face(int is_face) {
        this.is_face = is_face;
    }

    public List<PendingEventBean> getPending_event() {
        return pending_event;
    }

    public void setPending_event(List<PendingEventBean> pending_event) {
        this.pending_event = pending_event;
    }

    public List<AddEventBean> getAdd_event() {
        return add_event;
    }

    public void setAdd_event(List<AddEventBean> add_event) {
        this.add_event = add_event;
    }

    public List<EventBean> getEventBean() {
        List<EventBean> eventBeans = new ArrayList<>();
        EventBean eventBean = new EventBean();
        eventBean.type = EventBean.TYPE_PENDING;
        eventBean.mPendingEventBean = pending_event;
        eventBeans.add(eventBean);
        eventBean = new EventBean();
        eventBean.type = EventBean.TYPE_ADD;
        eventBean.mAddEventBean = add_event;
        eventBeans.add(eventBean);
        return eventBeans;
    }


    public static class EventBean {
        public static final int TYPE_PENDING = 10;
        public static final int TYPE_ADD = 11;

        public List<PendingEventBean> mPendingEventBean;
        public List<AddEventBean> mAddEventBean;
        public int type;
    }

    public static class PendingEventBean {
        /**
         * title : 指派11
         * end_date : 2019-04-11
         * status : 2
         */
        private String title;
        private String end_date;
        private int status;
        private int event_id;
        private String place;
        private String add_time;
        private int userid;
        private int is_claim;

        public int getIs_claim() {
            return is_claim;
        }

        public void setIs_claim(int is_claim) {
            this.is_claim = is_claim;
        }

        public int getEvent_id() {
            return event_id;
        }

        public void setEvent_id(int event_id) {
            this.event_id = event_id;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class AddEventBean {
        /**
         * title : 指派22
         * end_date : 2019-04-11
         * status : 5
         */

        private String title;
        private int event_id;
        private String end_date;
        private int status;
        private String add_time;
        private String update_time;
        private int is_claim;

        public int getIs_claim() {
            return is_claim;
        }

        public void setIs_claim(int is_claim) {
            this.is_claim = is_claim;
        }

        public int getEvent_id() {
            return event_id;
        }

        public void setEvent_id(int event_id) {
            this.event_id = event_id;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
    public static class AddPatrolBean {
        private String title;
        private String rectify_date;
        private int patrol_id;
        private int status;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

}
