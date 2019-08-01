package com.sdxxtop.guardianapp.app;

import java.io.File;

public interface Constants {
    String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    String PATH_CACHE = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "NetCache";
    String PATH_IMG = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "img";

    public static String COMPANY_JIN_WEIDU = "company_jin_weidu";

    String AUTO_TOKEN = "auto_token";
    String EXPIRE_TIME = "expire_time";
    String PART_ID = "part_id";
    String USER_ID = "user_id";
    String MOBILE = "mobile";

    /**
     *
     * 我认为图片地址不应该存本地
     */
    String USER_IMG = "user_img";


    /******* 猎鹰相关 ********/
    String TERMINAL_ID = "terminal_id";
    String TRACK_ID = "track_id";
    String SERVICE_ID = "service_id";

}
