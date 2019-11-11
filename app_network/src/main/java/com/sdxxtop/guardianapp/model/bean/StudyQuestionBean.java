package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

public class StudyQuestionBean {

    /**
     * question : ["第一题A","11","22","33"]
     * is_last : 2
     * anwer : A
     * attend_id : 61
     * question_num : 2
     * question_id : 1
     * type : 1
     * title : 题目标题1
     * number : 1
     * score : 5
     * right_answer : A
     */

    private int is_last;
    private String anwer;
    private String attend_id;
    private int question_num;
    private int question_id;
    private int type;
    private String title;
    private int number;
    private int score;
    private String right_answer;
    private List<String> question;

    public int getIs_last() {
        return is_last;
    }

    public void setIs_last(int is_last) {
        this.is_last = is_last;
    }

    public String getAnwer() {
        return anwer;
    }

    public void setAnwer(String anwer) {
        this.anwer = anwer;
    }

    public String getAttend_id() {
        return attend_id;
    }

    public void setAttend_id(String attend_id) {
        this.attend_id = attend_id;
    }

    public int getQuestion_num() {
        return question_num;
    }

    public void setQuestion_num(int question_num) {
        this.question_num = question_num;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRight_answer() {
        return right_answer;
    }

    public void setRight_answer(String right_answer) {
        this.right_answer = right_answer;
    }

    public List<String> getQuestion() {
        return question;
    }

    public void setQuestion(List<String> question) {
        this.question = question;
    }
}
