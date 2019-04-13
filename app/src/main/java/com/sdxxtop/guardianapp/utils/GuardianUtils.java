package com.sdxxtop.guardianapp.utils;

import java.util.ArrayList;
import java.util.List;

public class GuardianUtils {

    public static final List<String> questionIndex = new ArrayList<>();
    static {
        questionIndex.add("A");
        questionIndex.add("B");
        questionIndex.add("C");
        questionIndex.add("D");
//        questionIndex.add("E");
//        questionIndex.add("F");
//        questionIndex.add("G");
    }


    public static String getJobName(int position) {
        //1:网格员 2: 企业员工 3:街道管理员 4:区级管理员
        String positionName = "";
        switch (position) {
            case 2:
                positionName = "企业员工";
                break;
            case 3:
                positionName = "街道管理员";
                break;
            case 4:
                positionName = "区级管理员";
                break;
            default:
                positionName = "网格员";
                break;
        }
        return positionName;
    }

    public static int getQuestionIndex(String anwer) {
        //1:网格员 2: 企业员工 3:街道管理员 4:区级管理员
       return questionIndex.indexOf(anwer);
    }
}
