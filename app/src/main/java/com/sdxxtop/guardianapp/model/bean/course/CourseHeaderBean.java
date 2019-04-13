package com.sdxxtop.guardianapp.model.bean.course;

public class CourseHeaderBean extends BaseCourseDataBean {
    public String strHeader;

    @Override
    public int getItemType() {
        return TYPE_HEADER;
    }
}
