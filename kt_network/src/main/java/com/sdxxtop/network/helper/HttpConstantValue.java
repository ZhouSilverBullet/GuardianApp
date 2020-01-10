package com.sdxxtop.network.helper;

import com.sdxxtop.network.NetworkSession;

import java.io.File;

public interface HttpConstantValue {

    //NetUtil处理得到
    String STR_SPLICE_SYMBOL = "&";
    String STR_EQUAL_OPERATION = "=";
    String APP_KEY = "60487FE91A0577ED60C4DC56A9EF3DB5";


    String PATH_DATA = NetworkSession.getContext().getCacheDir().getAbsolutePath() + File.separator + "data";
    String PATH_CACHE = NetworkSession.getContext().getCacheDir().getAbsolutePath() + File.separator + "NetCache";
    String PATH_IMG = NetworkSession.getContext().getCacheDir().getAbsolutePath() + File.separator + "img";

    public static String COMPANY_JIN_WEIDU = "company_jin_weidu";



    String COMPANY_ID = "company_id";
    String USER_ID = "user_id";
    String USER_NAME = "user_name";
    String DEVICE_NAME = "device_name";
    String DEVICE_NO = "device_no";

    String AUTO_TOKEN = "auto_token";
    String EXPIRE_TIME = "expire_time";
    String PART_ID = "part_id";
    String PART_NAME = "part_name";
    String MOBILE = "mobile";

    String PASSWORD = "password";

    String PLATFORM_ID = "1";

    /**
     *
     * 我认为图片地址不应该存本地
     */
    String USER_IMG = "user_img";


}
