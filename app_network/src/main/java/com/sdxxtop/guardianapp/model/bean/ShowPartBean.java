package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @Author: zhousaito
 * @Date: 2019-04-24 21:15
 * @Version 1.0
 * @UserWhat what
 */
public class ShowPartBean {

    private List<PartBean> part;

    public List<PartBean> getPart() {
        return part;
    }

    public void setPart(List<PartBean> part) {
        this.part = part;
    }

    public static class PartBean {
        /**
         * part_id : 1
         * part_name : 罗庄区
         */

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
}
