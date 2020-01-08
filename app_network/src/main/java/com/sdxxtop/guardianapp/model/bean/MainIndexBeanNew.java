package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

public class MainIndexBeanNew {
    public int is_clock;//打卡权限  1：是  2：否
    public int is_report;
    public int is_patrol;
    public int is_map;
    public int is_uav;
    public int is_face;//是否注册人脸 1:是 2:否
    public int avg_tpfpm;
    public int avg_tenpm;
    public int unread_count;
    public int pending_count;
    public int max_temperature;
    public int min_temperature;

    public String temperature_img;
    public String air_quality;

    public List<PendingEvent> pending_event;
    public List<WheelPlantingVideo> wheel_planting_video;

    public class PendingEvent {
        public int event_id;
        public String title;
        public String end_date;
    }
    public class WheelPlantingVideo {
        public int video_id;
        public String title;
        public String cover_img;
        public String video_link;
    }

}
