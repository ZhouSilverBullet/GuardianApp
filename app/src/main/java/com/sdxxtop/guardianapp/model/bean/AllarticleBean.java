package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/9/6
 * Desc:
 */
public class AllarticleBean {
    public List<WheelPlanting> wheel_planting;
    public List<LearnNewsBean> all_article;

    public class WheelPlanting {
        public int news_id;
        public String title;
        public String cover_img;
        public String new_url;
    }
}
