package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @Author: zhousaito
 * @Date: 2019-04-24 21:15
 * @Version 1.0
 * @UserWhat what
 */
public class ShowPartBean {
    public List<KeywordInfoBean> list;
    public CategoryInfoBean category;

    public class CategoryInfoBean {
        public int category_id;
        public String category_name;
    }

    public class KeywordInfoBean {
        public int classify_keyword_id;
        public int category_id;
        public int classify_id;
        public String classify_keyword;
        public String category_name;
    }
}
