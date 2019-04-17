package com.sdxxtop.guardianapp.utils;

import java.util.ArrayList;
import java.util.List;

public class GuardianUtils {

    private final static String[] colorList = new String[]{"#7ECEF4", "#8C97CB", "#89C997",
            "#52C67A", "#3296FA", "#9396FA", "#FCBA28", "#AD8EA3", "#4E76E4", "#9D7DFA", "#D37FF1", "#4D97F0", "#FC716C", "#55BEF5"};

    private final static String[] colorHalfList = new String[]{"#557ECEF4", "#558C97CB", "#5589C997",
            "#5552C67A", "#553296FA", "#559396FA", "#55FCBA28", "#55AD8EA3", "#554E76E4", "#559D7DFA", "#55D37FF1", "#554D97F0", "#55FC716C", "#5555BEF5"};


    public static final List<String> questionIndex = new ArrayList<>();

    static {
        questionIndex.add("A");
        questionIndex.add("B");
        questionIndex.add("C");
        questionIndex.add("D");
        questionIndex.add("E");
        questionIndex.add("F");
        questionIndex.add("G");
        questionIndex.add("H");
        questionIndex.add("I");
        questionIndex.add("J");
        questionIndex.add("K");
        questionIndex.add("L");
        questionIndex.add("M");
        questionIndex.add("N");
        questionIndex.add("O");
        questionIndex.add("P");
        questionIndex.add("Q");
        questionIndex.add("R");
        questionIndex.add("S");
        questionIndex.add("T");
        questionIndex.add("U");
        questionIndex.add("V");
        questionIndex.add("W");
        questionIndex.add("X");
        questionIndex.add("Y");
        questionIndex.add("Z");
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

    public static String getQuestionIndex(int position) {
        if (position >= questionIndex.size()) {
            return "Z";
        }

        if (position < 0) {
            return "A";
        }
        return questionIndex.get(position);
    }

    public static int getQuestionIndex(String anwer) {
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

    public static String getHalfColor(int position) {
        return colorHalfList[position % colorHalfList.length];
    }
}
