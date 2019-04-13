package com.sdxxtop.guardianapp.model.bean.course;

public class ExamCellBean extends BaseCourseDataBean {
    private int exam_id;
    private String end_time;
    private String num;
    private int classify_id;
    private String title;
    private String exam_time;
    private int type;
    private String classify_name;
    private int is_exam;

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

    public int getIs_exam() {
        return is_exam;
    }

    public void setIs_exam(int is_exam) {
        this.is_exam = is_exam;
    }

    @Override
    public int getItemType() {
        return TYPE_CELL;
    }
}
