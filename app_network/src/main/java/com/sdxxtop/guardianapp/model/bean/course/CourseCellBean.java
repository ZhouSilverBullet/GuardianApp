package com.sdxxtop.guardianapp.model.bean.course;

public class CourseCellBean extends BaseCourseDataBean {
    /**
     * course_id : 1
     * classify_id : 1
     * title : 有多篇文章
     * class_time : 10
     * study_time : 10
     * exam_time : 2019-04-15 19:01:36
     * type : 1
     * classify_name : 党政课程
     * url : http://wap.sdxxtop.com/envir/envir/class_info?id=1&class_number=1
     */

    private int course_id;
    private int classify_id;
    private String title;
    private String class_time;
    private String study_time;
    private String exam_time;
    private int type;
    private String classify_name;
    private String url;

    private int exam_id;
    private String end_time;
    private String num;


    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getClassify_id() {
        return classify_id;
    }

    public void setClassify_id(int classify_id) {
        this.classify_id = classify_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClass_time() {
        return class_time;
    }

    public void setClass_time(String class_time) {
        this.class_time = class_time;
    }

    public String getStudy_time() {
        return study_time;
    }

    public void setStudy_time(String study_time) {
        this.study_time = study_time;
    }

    public String getExam_time() {
        return exam_time;
    }

    public void setExam_time(String exam_time) {
        this.exam_time = exam_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getClassify_name() {
        return classify_name;
    }

    public void setClassify_name(String classify_name) {
        this.classify_name = classify_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public int getItemType() {
        return TYPE_CELL;
    }
}
