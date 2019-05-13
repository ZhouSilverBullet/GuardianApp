package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/5/13
 * Desc:
 */
public class PartEventListBean {

    private int num;
    private List<ClData> cl_data;
    private List<PartName> part_name;

    public void setCl_data(List<ClData> cl_data) {
        this.cl_data = cl_data;
    }

    public List<PartName> getPart_name() {
        return part_name;
    }

    public void setPart_name(List<PartName> part_name) {
        this.part_name = part_name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<ClData> getCl_data() {
        return cl_data;
    }

    public static class ClData{
      private int event_id;
      private int part_id;
      private String title;
      private String add_time;
      private int status;

        public int getEvent_id() {
            return event_id;
        }

        public void setEvent_id(int event_id) {
            this.event_id = event_id;
        }

        public int getPart_id() {
            return part_id;
        }

        public void setPart_id(int part_id) {
            this.part_id = part_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class PartName{
        private String part_name;

        public String getPart_name() {
            return part_name;
        }

        public void setPart_name(String part_name) {
            this.part_name = part_name;
        }
    }

}
