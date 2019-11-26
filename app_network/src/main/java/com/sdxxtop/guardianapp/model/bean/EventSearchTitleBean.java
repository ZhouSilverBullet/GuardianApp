package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/5/22
 * Desc:
 */
public class EventSearchTitleBean {

    public PartBean part;
    public List<EventShowBean.NewPartBean> part_info;
    public List<CategoryBean> category;
    private List<KeyInfo> key_info;

    public List<KeyInfo> getKey_info() {
        return key_info;
    }

    public void setKey_info(List<KeyInfo> key_info) {
        this.key_info = key_info;
    }

    public class PartBean {
        public int part_id;
        public String part_name;
    }

    public class CategoryBean {
        public int category_id;
        public String category_name;
    }


    public class KeyInfo{
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
