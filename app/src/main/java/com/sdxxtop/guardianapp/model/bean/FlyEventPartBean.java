package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/9/3
 * Desc:
 */
public class FlyEventPartBean {

    private UserInfo user;
    private List<EventShowBean.NewPartBean> part;
    private List<UavInfoBean> uav;


    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public List<EventShowBean.NewPartBean> getPart() {
        return part;
    }

    public void setPart(List<EventShowBean.NewPartBean> part) {
        this.part = part;
    }

    public List<UavInfoBean> getUav() {
        return uav;
    }

    public void setUav(List<UavInfoBean> uav) {
        this.uav = uav;
    }

    public class UserInfo {
        private int userid;
        private String name;
        private int part_id;

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
    }

    public class UavInfoBean {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
