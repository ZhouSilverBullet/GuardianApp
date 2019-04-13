package com.sdxxtop.guardianapp.app;

import java.io.File;

public interface Constants {
    String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    String PATH_CACHE = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "NetCache";

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

}
