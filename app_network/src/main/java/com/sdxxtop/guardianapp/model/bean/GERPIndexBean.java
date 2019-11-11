package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/5/13
 * Desc:
 */
public class GERPIndexBean {

    private int wait_for;//待处理
    private int to_solved;//处理中
    private int adopt;//已处理
    private int pending;//已完成
    private int count;//已上报总条数
    private int reject;
    private int not_pass;
    private int operation;
    private int together;

    private List<EventInfoBean> eventInfo;
    private List<EventInfoBean> completeInfo;

    public int getWait_for() {
        return wait_for;
    }

    public void setWait_for(int wait_for) {
        this.wait_for = wait_for;
    }

    public int getTo_solved() {
        return to_solved;
    }

    public void setTo_solved(int to_solved) {
        this.to_solved = to_solved;
    }

    public int getAdopt() {
        return adopt;
    }

    public void setAdopt(int adopt) {
        this.adopt = adopt;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getReject() {
        return reject;
    }

    public void setReject(int reject) {
        this.reject = reject;
    }

    public int getNot_pass() {
        return not_pass;
    }

    public void setNot_pass(int not_pass) {
        this.not_pass = not_pass;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public int getTogether() {
        return together;
    }

    public void setTogether(int together) {
        this.together = together;
    }

    public List<EventInfoBean> getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(List<EventInfoBean> eventInfo) {
        this.eventInfo = eventInfo;
    }

    public List<EventInfoBean> getCompleteInfo() {
        return completeInfo;
    }

    public void setCompleteInfo(List<EventInfoBean> completeInfo) {
        this.completeInfo = completeInfo;
    }

    public class EventInfoBean{
        private int count;
        private String part_name;
        private int part_id;
        private String color;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getPart_name() {
            return part_name;
        }

        public void setPart_name(String part_name) {
            this.part_name = part_name;
        }

        public int getPart_id() {
            return part_id;
        }

        public void setPart_id(int part_id) {
            this.part_id = part_id;
        }
    }
}
