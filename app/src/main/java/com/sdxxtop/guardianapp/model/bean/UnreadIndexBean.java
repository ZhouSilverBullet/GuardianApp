package com.sdxxtop.guardianapp.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/5/30
 * Desc:
 */
public class UnreadIndexBean {

    private OverdueBean solved;
    private OverdueBean whole;
    private OverdueBean overdue;
    private OverdueBean device;
    private OverdueBean claim;

    public OverdueBean getClaim() {
        return claim;
    }

    public void setClaim(OverdueBean claim) {
        this.claim = claim;
    }

    public OverdueBean getDevice() {
        return device;
    }

    public void setDevice(OverdueBean device) {
        this.device = device;
    }

    public OverdueBean getSolved() {
        return solved;
    }

    public void setSolved(OverdueBean solved) {
        this.solved = solved;
    }

    public OverdueBean getWhole() {
        return whole;
    }

    public void setWhole(OverdueBean whole) {
        this.whole = whole;
    }

    public OverdueBean getOverdue() {
        return overdue;
    }

    public void setOverdue(OverdueBean overdue) {
        this.overdue = overdue;
    }

    public static class OverdueBean {
        private int count;
        private String title;
        private String time;
        private String name;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public List<UnreadIndexAdapterBean> getUnreadIndexList() {
        List<UnreadIndexAdapterBean> list = new ArrayList<>();
        if (solved!=null){
            list.add(new UnreadIndexAdapterBean(solved.count,solved.name,solved.title,1,solved.time));
        }
        if (whole!=null){
            list.add(new UnreadIndexAdapterBean(whole.count,whole.name,whole.title,2,whole.time));
        }
        if (overdue!=null){
            list.add(new UnreadIndexAdapterBean(overdue.count,overdue.name,overdue.title,3,overdue.time));
        }
        if (device!=null){
            list.add(new UnreadIndexAdapterBean(device.count,device.name,device.title,4,device.time));
        }
        if (claim!=null){
            list.add(new UnreadIndexAdapterBean(claim.count,claim.name,claim.title,5,claim.time));
        }
        return list;
    }

}
