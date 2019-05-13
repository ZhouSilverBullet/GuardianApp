package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/5/13
 * Desc:
 */
public class EventListBean {

    private String event_name;
    private int count;
    private List<CompleteInfo> part;
    private List<CompleteInfo> completeInfo;

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CompleteInfo> getPart() {
        return part;
    }

    public void setPart(List<CompleteInfo> part) {
        this.part = part;
    }

    public List<CompleteInfo> getCompleteInfo() {
        return completeInfo;
    }

    public void setCompleteInfo(List<CompleteInfo> completeInfo) {
        this.completeInfo = completeInfo;
    }

    public static class CompleteInfo{

        public CompleteInfo(int count, String part_name, int part_id) {
            this.count = count;
            this.part_name = part_name;
            this.part_id = part_id;
        }

        private int count;
        private String part_name;
        private int part_id;

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
