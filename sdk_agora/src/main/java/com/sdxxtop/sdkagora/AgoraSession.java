package com.sdxxtop.sdkagora;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;
import com.sdxxtop.imagora.rtmtutorial.AgoraIMConfig;
import com.sdxxtop.openlive.AgoraAppLiveConfig;

import java.util.LinkedHashMap;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-11-07 19:32
 * Version: 1.0
 * Description:
 */
public class AgoraSession {

    /**
     * 是否已经在直播间了
     */
    public static boolean isLiving = false;

    private static RtmCallEventManager callEventManager;

    private static AgoraAppLiveConfig liveConfig;

    private static AgoraIMConfig imConfig;

    public static void init(Application application) {

        callEventManager = new RtmCallEventManager();

        if (liveConfig == null) {
            liveConfig = new AgoraAppLiveConfig();
        }

        liveConfig.setApplication(application);
        liveConfig.onCreate();


        if (imConfig == null) {
            imConfig = new AgoraIMConfig();
        }
        imConfig.setApplication(application);
        imConfig.onCreate();
    }

    public static void onTerminate() {
        getLiveConfig().onTerminate();
    }

    public static AgoraAppLiveConfig getLiveConfig() {
        return liveConfig;
    }

    public static RtmCallEventManager getCallEventManager() {
        return callEventManager;
    }

    public static void addCallback(RtmCallEventCallback rtmCallEventCallback) {
        getCallEventManager().addCallback(rtmCallEventCallback);
    }

    public static void removeCallback(RtmCallEventCallback rtmCallEventCallback) {
        getCallEventManager().removeCallback(rtmCallEventCallback);
    }

    private static LinkedTreeMap<String, String> message = new LinkedTreeMap<>();

    public static void setMessage(LinkedTreeMap<String, String> map) {
        if (map != null && map.size() > 0) {
            if (message.size() != 0) {
                message.clear();
            }
            message.putAll(map);
        }
    }

    public static LinkedTreeMap<String, String> getMessage() {
        return message;
    }

}
