package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/5/22
 * Desc:
 */
public class EventSearchTitleBean {

    private String keyword;
    private List<KeyInfo> key_info;
    private List<ShowPartBean.PartBean> part_info;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<KeyInfo> getKey_info() {
        return key_info;
    }

    public void setKey_info(List<KeyInfo> key_info) {
        this.key_info = key_info;
    }

    public List<ShowPartBean.PartBean> getPart_info() {
        return part_info;
    }

    public void setPart_info(List<ShowPartBean.PartBean> part_info) {
        this.part_info = part_info;
    }

    public static class KeyInfo {
        private int keyword_id;
        private String part_id;
        private String keyword;

        public int getKeyword_id() {
            return keyword_id;
        }

        public void setKeyword_id(int keyword_id) {
            this.keyword_id = keyword_id;
        }

        public String getPart_id() {
            return part_id;
        }

        public void setPart_id(String part_id) {
            this.part_id = part_id;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }
    }


}
