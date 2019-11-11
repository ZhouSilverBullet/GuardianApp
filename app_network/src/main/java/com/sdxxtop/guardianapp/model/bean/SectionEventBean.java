package com.sdxxtop.guardianapp.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/7/29
 * Desc:
 */
public class SectionEventBean {
    private int num;
    private int count;
    private int unclaimed;
    private int claimed_count;
    private int completed_count;


    private List<ClaimInfoBean> claim;
    private List<String> numStr = new ArrayList<>();

    public List<String> getNumStr() {
        numStr.clear();
        numStr.add(String.valueOf(count));
        numStr.add(String.valueOf(unclaimed));
        numStr.add(String.valueOf(claimed_count));
        numStr.add(String.valueOf(completed_count));
        return numStr;
    }

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

    public int getUnclaimed() {
        return unclaimed;
    }

    public void setUnclaimed(int unclaimed) {
        this.unclaimed = unclaimed;
    }

    public int getClaimed_count() {
        return claimed_count;
    }

    public void setClaimed_count(int claimed_count) {
        this.claimed_count = claimed_count;
    }

    public int getCompleted_count() {
        return completed_count;
    }

    public void setCompleted_count(int completed_count) {
        this.completed_count = completed_count;
    }

    public List<ClaimInfoBean> getClaim() {
        return claim;
    }

    public void setClaim(List<ClaimInfoBean> claim) {
        this.claim = claim;
    }

    public static class ClaimInfoBean{
        private int event_id;
        private String title;
        private int status;
        private String place;
        private String add_time;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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
    }
}
