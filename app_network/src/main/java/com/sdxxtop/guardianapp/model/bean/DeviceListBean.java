package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/7/9
 * Desc:
 */
public class DeviceListBean {
    private List<DeviceInfoBean> device_info;
    private List<PartInfoBean> part_info;

    public List<DeviceInfoBean> getDevice_info() {
        return device_info;
    }

    public void setDevice_info(List<DeviceInfoBean> device_info) {
        this.device_info = device_info;
    }

    public List<PartInfoBean> getPart_info() {
        return part_info;
    }

    public void setPart_info(List<PartInfoBean> part_info) {
        this.part_info = part_info;
    }

    public static class DeviceInfoBean {
        private int device_id;
        private String device_sn;
        private String device_name;
        private int part_id;
        private String longitude;
        private int tpfpm;
        private int tenpm;
        private int real_tpfpm;
        private int real_tenpm;
        private int status;

        public int getReal_tpfpm() {
            return real_tpfpm;
        }

        public void setReal_tpfpm(int real_tpfpm) {
            this.real_tpfpm = real_tpfpm;
        }

        public int getReal_tenpm() {
            return real_tenpm;
        }

        public void setReal_tenpm(int real_tenpm) {
            this.real_tenpm = real_tenpm;
        }

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

        public int getTpfpm() {
            return tpfpm;
        }

        public void setTpfpm(int tpfpm) {
            this.tpfpm = tpfpm;
        }

        public int getTenpm() {
            return tenpm;
        }

        public void setTenpm(int tenpm) {
            this.tenpm = tenpm;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class PartInfoBean {
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
