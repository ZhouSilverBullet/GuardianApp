package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/7/5
 * Desc:
 */
public class DeviceDataBean {

    private int device_id;
    private int num;
    private String device_sn;

    private List<DustDataBean> dust_data;

    public int getDevice_id() {
        return device_id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    public List<DustDataBean> getDust_data() {
        return dust_data;
    }

    public void setDust_data(List<DustDataBean> dust_data) {
        this.dust_data = dust_data;
    }

    public static class DustDataBean{
        private String device_sn;
        private float tpfpm;
        private float tenpm;
        private String add_time;

        public DustDataBean() {
        }

        public DustDataBean(String device_sn, float tpfpm, float tenpm, String add_time) {
            this.device_sn = device_sn;
            this.tpfpm = tpfpm;
            this.tenpm = tenpm;
            this.add_time = add_time;
        }

        public String getDevice_sn() {
            return device_sn;
        }

        public void setDevice_sn(String device_sn) {
            this.device_sn = device_sn;
        }

        public float getTpfpm() {
            return tpfpm;
        }

        public void setTpfpm(float tpfpm) {
            this.tpfpm = tpfpm;
        }

        public float getTenpm() {
            return tenpm;
        }

        public void setTenpm(float tenpm) {
            this.tenpm = tenpm;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
