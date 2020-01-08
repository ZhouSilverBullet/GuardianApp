package com.sdxxtop.guardianapp.model.bean;

import com.sdxxtop.guardianapp.utils.GuardianUtils;

public class UcenterIndexBean {
    /**
     * "name" : 周周
     * "userid" : 2
     * "part_id" : 1
     * "position" : 1,/职位(1:网格员 2: 企业员工 3:街道管理员 4:区级管理员)
     * "img" : http://xuxingtest.oss-cn-hangzhou.aliyuncs.com/face/20190412100430281648.png
     * "part_name" : 罗庄区,//
     * "type":1,  //单位级别(1:区级 2: 乡镇 3:企业)
     */
    public String name;
    public int userid;
    public int part_id;
    public int position;
    public String img;
    public String part_name;
    public int type;
    public int is_mail;
    public int unread_count;
    public int grade;  // 星星个数

    /***** 权限 *******/
    private String is_uav_url;


    //1:网格员 2: 企业员工 3:街道管理员 4:区级管理员 5:执法人员
    public String getStringPosition() {
        return GuardianUtils.getJobName(position);
    }

}
