package com.sdxxtop.guardianapp.utils;

public class GuardianUtils {

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
}
