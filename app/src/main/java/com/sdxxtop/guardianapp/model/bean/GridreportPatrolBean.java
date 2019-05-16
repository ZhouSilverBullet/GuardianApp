package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/5/16
 * Desc:
 */
public class GridreportPatrolBean {

    private int num;
    private String event_name;
    private List<UserPart> user_part;
    private List<TrailUser> trail_user;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public List<UserPart> getUser_part() {
        return user_part;
    }

    public void setUser_part(List<UserPart> user_part) {
        this.user_part = user_part;
    }

    public List<TrailUser> getTrail_user() {
        return trail_user;
    }

    public void setTrail_user(List<TrailUser> trail_user) {
        this.trail_user = trail_user;
    }

    public static class UserPart {

        private int part_id;
        private String part_name;

        public int getPart_id() {
            return part_id;
        }

        public void setPart_id(int part_id) {
            this.part_id = part_id;
        }

        public String getPart_name() {
            return part_name;
        }

        public void setPart_name(String part_name) {
            this.part_name = part_name;
        }
    }

    public static class TrailUser {
        private int userid;
        private String name;
        private int part_id;
        private int trail_count;
        private int distance;
        private String part_name;
        private String p_name;
        private String pr_name;
        private int days;

        public String getPr_name() {
            return pr_name;
        }

        public void setPr_name(String pr_name) {
            this.pr_name = pr_name;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPart_id() {
            return part_id;
        }

        public void setPart_id(int part_id) {
            this.part_id = part_id;
        }

        public int getTrail_count() {
            return trail_count;
        }

        public void setTrail_count(int trail_count) {
            this.trail_count = trail_count;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getPart_name() {
            return part_name;
        }

        public void setPart_name(String part_name) {
            this.part_name = part_name;
        }

        public String getP_name() {
            return p_name;
        }

        public void setP_name(String p_name) {
            this.p_name = p_name;
        }

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }
    }
}
