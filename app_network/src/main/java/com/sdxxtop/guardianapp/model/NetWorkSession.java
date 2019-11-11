package com.sdxxtop.guardianapp.model;

import android.app.Application;
import android.content.Context;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-11-11 17:38
 * Version: 1.0
 * Description:
 */
public class NetWorkSession {
    private static Application application;
    private static boolean isBuildConfig;

    public static void init(Application applicationContext, boolean isBuildConfig) {
        application =  applicationContext;
        NetWorkSession.isBuildConfig = isBuildConfig;
    }

    public static Context getContext() {
        return application;
    }

    public static boolean DEBUG() {
        return isBuildConfig;
    }
}
