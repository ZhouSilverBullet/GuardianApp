package com.sdxxtop.guardianapp.model.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/7/18
 * Desc:
 */
public class EventShowBean {

    private List<NewPartBean> part;

    public List<NewPartBean> getPart() {
        return part;
    }

    public void setPart(List<NewPartBean> part) {
        this.part = part;
    }

    public static class NewPartBean implements IPickerViewData {

        @Override
        public String getPickerViewText() {
            return part_name;
        }

        private int part_id;
        private String part_name;
        private int parent_id;
        private int level;
        private List<ChildrenBean> children;

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

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }
    }

    public static class ChildrenBean {
        public ChildrenBean(int part_id, String part_name, List<ChildrenBean> children) {
            this.part_id = part_id;
            this.part_name = part_name;
            this.children = children;
        }

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

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        private int part_id;
        private String part_name;
        private int parent_id;
        private int level;
        private List<ChildrenBean> children;
    }
}
