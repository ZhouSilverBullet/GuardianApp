package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/6/12
 * Desc:
 */
public class EventModeBean {
    private List<ModeDataBean> mode_data;

    public List<ModeDataBean> getMode_data() {
        return mode_data;
    }

    public void setMode_data(List<ModeDataBean> mode_data) {
        this.mode_data = mode_data;
    }

    public static class ModeDataBean{
        private int mode_id;
        private String name;

        public int getMode_id() {
            return mode_id;
        }

        public void setMode_id(int mode_id) {
            this.mode_id = mode_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
