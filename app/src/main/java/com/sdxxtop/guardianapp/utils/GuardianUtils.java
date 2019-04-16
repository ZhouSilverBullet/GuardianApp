package com.sdxxtop.guardianapp.utils;

import java.util.ArrayList;
import java.util.List;

public class GuardianUtils {

    private final static String[] colorList = new String[]{"#7ECEF4", "#8C97CB", "#89C997",
            "#52C67A", "#3296FA", "#9396FA", "#FCBA28", "#AD8EA3", "#4E76E4", "#9D7DFA", "#D37FF1", "#4D97F0", "#FC716C", "#55BEF5"};

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

    public static String getColor(int position) {
        if (position >= colorList.length) {
            position = colorList.length - 1;
        }

        if (position < 0) {
            position = 0;
        }
        return colorList[position];
    }
}
