package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/12/17
 * Desc:
 */
public class EventStreamBean {
    public int num;
    public List<SerringsBean> serrings;

    public static class SerringsBean{
        public int event_settings_id;
        public String event_settings_name;
    }
}
