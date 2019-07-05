package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/7/4
 * Desc:
 */
public class DeviceMapBean {

    private List<DeviceInfo> device_info;
    private DeviceCount device_count;

    public List<DeviceInfo> getDevice_info() {
        return device_info;
    }

    public void setDevice_info(List<DeviceInfo> device_info) {
        this.device_info = device_info;
    }

    public DeviceCount getDevice_count() {
        return device_count;
    }

    public void setDevice_count(DeviceCount device_count) {
        this.device_count = device_count;
    }

    public static class DeviceInfo {
        private int device_id;
        private String device_sn;
        private String device_name;
        private int part_id;
        private String longitude;
        private int count;
        private double tpfpm;
        private double tenpm;
        private int status;

        public int getDevice_id() {
            return device_id;
        }

        public void setDevice_id(int device_id) {
            this.device_id = device_id;
        }

        public String getDevice_sn() {
            return device_sn;
        }

        public void setDevice_sn(String device_sn) {
            this.device_sn = device_sn;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }

        public int getPart_id() {
            return part_id;
        }

        public void setPart_id(int part_id) {
            this.part_id = part_id;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public double getTpfpm() {
            return tpfpm;
        }

        public void setTpfpm(double tpfpm) {
            this.tpfpm = tpfpm;
        }

        public double getTenpm() {
            return tenpm;
        }

        public void setTenpm(double tenpm) {
            this.tenpm = tenpm;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class DeviceCount {
        private int exceed_count;
        private int abnormal_count;
        private int normal_count;

        public int getExceed_count() {
            return exceed_count;
        }

        public void setExceed_count(int exceed_count) {
            this.exceed_count = exceed_count;
        }

        public int getAbnormal_count() {
            return abnormal_count;
        }

        public void setAbnormal_count(int abnormal_count) {
            this.abnormal_count = abnormal_count;
        }

        public int getNormal_count() {
            return normal_count;
        }

        public void setNormal_count(int normal_count) {
            this.normal_count = normal_count;
        }
    }

}
