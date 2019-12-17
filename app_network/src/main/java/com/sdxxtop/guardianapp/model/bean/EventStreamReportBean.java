package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/12/17
 * Desc:
 */
public class EventStreamReportBean {


    /**
     * reportPath : {"title":16,"username":2,"userPhone":2,"userPart":2,"reportPart":2,"reportFind":2,"reportDescribe":100,"reportImg":2,"img":0,
     * "supplement":2,"supplementNumber":30,"eventClassification":2,"basicReview":2}
     * part_info : []
     * part : {}
     * category : []
     * user : {"userid":50172,"name":"张海波（测试）","mobile":"15136299469","part_id":19,"part_name":"区环保分局","parent_id":90}
     */

    public ReportPathBean reportPath;
    public PartBean part;
    public UserBean user;
    public List<?> part_info;
    public List<?> category;


    public static class ReportPathBean {
        /**
         * title : 16
         * username : 2
         * userPhone : 2
         * userPart : 2
         * reportPart : 2
         * reportFind : 2
         * reportDescribe : 100
         * reportImg : 2
         * img : 0
         * supplement : 2
         * supplementNumber : 30
         * eventClassification : 2
         * basicReview : 2
         */

        public int title;
        public int username;
        public int userPhone;
        public int userPart;
        public int reportPart;
        public int reportFind;
    }

    public static class PartBean {
    }

    public static class UserBean {
        /**
         * userid : 50172
         * name : 张海波（测试）
         * mobile : 15136299469
         * part_id : 19
         * part_name : 区环保分局
         * parent_id : 90
         */

        public int userid;
        public String name;
        public String mobile;
        public int part_id;
        public String part_name;
        public int parent_id;
    }
}
