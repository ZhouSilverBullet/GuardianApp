package com.sdxxtop.guardianapp.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Date:2020-02-13
 * author:lwb
 * Desc:
 */
public class PartAndUserBean implements Serializable {
    public List<AssignPartBean> part;
    public List<AssignUserBean> user;

    public static class AssignPartBean implements Serializable {
        public int part_id;
        public String part_name;
        public boolean isSelect;
    }

    public static class AssignUserBean implements Serializable {
        public int userid;
        public String name;
        public boolean isSelect;
        public String part_name;
        public String img;
    }
}
