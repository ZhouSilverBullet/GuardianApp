package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/5/15
 * Desc:
 */
public class EnterpriseCompanyBean {
    private String event_name;
    private String parent_name;

    private List<PartData>user_part;
    private List<PartData>part_data;
    private List<PartInfo> part_info;

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public List<PartData> getUser_part() {
        return user_part;
    }

    public void setUser_part(List<PartData> user_part) {
        this.user_part = user_part;
    }

    public List<PartData> getPart_data() {
        return part_data;
    }

    public void setPart_data(List<PartData> part_data) {
        this.part_data = part_data;
    }

    public List<PartInfo> getPart_info() {
        return part_info;
    }

    public void setPart_info(List<PartInfo> part_info) {
        this.part_info = part_info;
    }

    public static class PartData{
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

    public static class PartInfo{
        private String part_name;
        private int part_id;
        private int train_count;
        private int report_info;
        private String parent_name;
        private int seu_count;
        private String name;

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

        public int getTrain_count() {
            return train_count;
        }

        public void setTrain_count(int train_count) {
            this.train_count = train_count;
        }

        public int getReport_info() {
            return report_info;
        }

        public void setReport_info(int report_info) {
            this.report_info = report_info;
        }

        public String getParent_name() {
            return parent_name;
        }

        public void setParent_name(String parent_name) {
            this.parent_name = parent_name;
        }

        public int getSeu_count() {
            return seu_count;
        }

        public void setSeu_count(int seu_count) {
            this.seu_count = seu_count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
