package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/7/23
 * Desc:
 */
public class EventChartBean {

    public List<ChartInfoBean> eventInfo;
    public List<ChartInfoBean> completeInfo;

    public List<ChartInfoBean> getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(List<ChartInfoBean> eventInfo) {
        this.eventInfo = eventInfo;
    }

    public List<ChartInfoBean> getCompleteInfo() {
        return completeInfo;
    }

    public void setCompleteInfo(List<ChartInfoBean> completeInfo) {
        this.completeInfo = completeInfo;
    }

    public static class ChartInfoBean {
        private int count;
        private int num;
        private String part_name;
        private int part_id;
        private String color;
        private int level;
        private List<ChartInfoBean> children;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
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

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public List<ChartInfoBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChartInfoBean> children) {
            this.children = children;
        }
    }

}
