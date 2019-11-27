package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/5/28
 * Desc:
 */
public class EventDiscretionListBean {

    private int num;
    List<PartolBean> partol;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<PartolBean> getPartol() {
        return partol;
    }

    public void setPartol(List<PartolBean> partol) {
        this.partol = partol;
    }

    public static class PartolBean{
        private int patrol_id;
        private int userid;
        private String title;
        private String add_date;
        private String rectify_date;
        private int status;
        private String category_name;

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public int getPatrol_id() {
            return patrol_id;
        }

        public void setPatrol_id(int patrol_id) {
            this.patrol_id = patrol_id;
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

        public String getAdd_date() {
            return add_date;
        }

        public void setAdd_date(String add_date) {
            this.add_date = add_date;
        }

        public String getRectify_date() {
            return rectify_date;
        }

        public void setRectify_date(String rectify_date) {
            this.rectify_date = rectify_date;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
