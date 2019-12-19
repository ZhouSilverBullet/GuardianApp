package com.sdxxtop.guardianapp.model.bean;

/**
 * @author :  lwb
 * Date: 2019/12/18
 * Desc:
 */
public class EventStreamDetailBean {


    /**
     * event_id : 4360
     * place : 北京汇诚汽车工程有限公司
     * add_time : 2019-12-18 16:53:58
     * status : 1
     * username : 张海波（测试）
     * mobile : 15136299469
     * part_name : 区环保分局
     * path_name : 盛庄街道指挥中心
     * patrol_type : 巡查
     * content : 京东快递回到家继续几点到几点海景大酒店快点快点酷酷的好的好的就地解决大姐夫基督
     * img : http://xuxingtest.oss-cn-hangzhou.aliyuncs.com/envir/20191218165358200314.png,http://xuxingtest.oss-cn-hangzhou.aliyuncs
     * .com/envir/20191218165358154818.png,http://xuxingtest.oss-cn-hangzhou.aliyuncs.com/envir/20191218165358194807.png
     * video : 
     * supplement : 几点了打个电话快点快点很喜欢吃韭菜鸡蛋成都饭店多久记得记得基督教大家都好大喊大叫的继续记得记得基督教大酒店坚持坚持江西南
     * category_name : 其他类
     * union : {"extra":"","part_name":"","add_time":""}
     * turn : {"part_name":"","extra":"","operate_time":""}
     * claim : {"send_name":"","send_part_name":"","send_time":""}
     * send : {"send_name":"","send_part_name":"","send_time":"","settle_name":"","settle_part":"","end_date":""}
     * solve : {"settle_time":"","settle_reason":"","finish_img":"","finish_video":"","important_type":""}
     * check : {"check_name":"","check_part":"","img":"","video":"","extra":"","appraise":"","check_time":""}
     * is_claim_auth : 2
     * is_Operate_auth : 2
     * unable_solve : 2
     * solveUnableDescribeNumber : 0
     * is_solve : 2
     * solveImg : 2
     * solveDescribeNumber : 0
     * solveImgNumber : 0
     * is_modify : 2
     * checkDescribeNumber : 0
     * unable_check : 2
     * checkNoNumber : 0
     * checkEvaluate : 2
     * checkEvaluateNumber : 0
     * checkImg : 2
     * checkImgNumber : 0
     * basicOperation : 1
     */

    public int event_id;
    public String place;
    public String add_time;
    public String title;
    public int status;
    public String username;
    public String mobile;
    public String part_name;
    public String path_name;
    public String patrol_type;
    public String content;
    public String img;
    public String video;
    public String supplement;
    public String category_name;
    public UnionBean union;
    public TurnBean turn;
    public ClaimBean claim;
    public SendBean send;
    public SolveBean solve;
    public CheckBean check;
    public int is_claim_auth;
    public int is_Operate_auth;
    public int unable_solve;
    public int solveUnableDescribeNumber;
    public int is_solve;
    public int solveImg;
    public int solveDescribeNumber;
    public int solveImgNumber;
    public int is_modify;
    public int checkDescribeNumber;
    public int unable_check;
    public int checkNoNumber;
    public int checkEvaluate;
    public int checkEvaluateNumber;
    public int checkImg;
    public int checkImgNumber;
    public int basicOperation;
    public int is_claim;

    public static class UnionBean {
        /**
         * extra : 
         * part_name : 
         * add_time : 
         */

        public String extra;
        public String part_name;
        public String add_time;

    }

    public static class TurnBean {
        /**
         * part_name : 
         * extra : 
         * operate_time : 
         */

        public String part_name;
        public String extra;
        public String operate_time;


    }

    public static class ClaimBean {//认领
        /**
         * send_name : 
         * send_part_name : 
         * send_time : 
         */

        public String send_name;
        public String send_part_name;
        public String send_time;
        public int status;

    }

    public static class SendBean {
        /**
         * send_name : 
         * send_part_name : 
         * send_time : 
         * settle_name : 
         * settle_part : 
         * end_date : 
         */

        public String send_name;
        public String send_part_name;
        public String send_time;
        public String settle_name;
        public String settle_part;
        public String end_date;
        public String important_type;
    }

    public static class SolveBean {
        /**
         * settle_time : 
         * settle_reason : 
         * finish_img : 
         * finish_video : 
         * important_type : 
         */

        public String settle_time;
        public String settle_reason;
        public String finish_img;
        public String finish_video;
        public String important_type;
    }

    public static class CheckBean {
        /**
         * check_name : 
         * check_part : 
         * img : 
         * video : 
         * extra : 
         * appraise : 
         * check_time : 
         */

        public String check_name;
        public String check_part;
        public String img;
        public String video;
        public String extra;
        public String appraise;
        public String check_time;
        public String appExtra;

    }
}
