package com.sdxxtop.guardianapp.app;

import java.io.File;

public interface Constants {
    String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    String PATH_CACHE = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "NetCache";

    public static String COMPANY_JIN_WEIDU = "company_jin_weidu";

}
