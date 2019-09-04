package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/9/3
 * Desc:
 */
public class FlyEventDetailBean {

    public UavTask uav_task;
    public String uav_video;
    public List<UavExcel> uav_excel;

    public class UavTask {
        public int id;
        public String title;
        public String content;
        public int upload_type;
        public String longitude;
        public String place;
        public String add_date;
        public int user_id;
        public int uav_id;
        public String user_name;
        public String uav_name;
    }

    public class UavExcel {
        public String time;
        public String date;
        public float tpfpm;//PM2.5
        public float tenpm;//PM10
        public float temperature;//温度
        public float humidity;//湿度
        public String place;
        public String longitudes;
        public String add_time;
        public int sort;
    }
}
