package com.sdxxtop.openlive;

import android.app.Application;
import android.content.SharedPreferences;

import com.sdxxtop.openlive.rtc.EngineConfig;
import com.sdxxtop.openlive.rtc.AgoraEventHandler;
import com.sdxxtop.openlive.rtc.EventHandler;
import com.sdxxtop.openlive.stats.StatsManager;
import com.sdxxtop.openlive.utils.FileUtil;
import com.sdxxtop.openlive.utils.PrefManager;
import com.sdxxtop.sdkagora.R;

import io.agora.rtc.RtcEngine;

/**
 * 直播的配置文件
 */
public class AgoraAppLiveConfig {
    private Application application;
    private RtcEngine mRtcEngine;
    private EngineConfig mGlobalConfig = new EngineConfig();
    private AgoraEventHandler mHandler = new AgoraEventHandler();
    private StatsManager mStatsManager = new StatsManager();

    public void onCreate() {
        try {
            mRtcEngine = RtcEngine.create(getApplicationContext(), application.getString(R.string.private_app_id), mHandler);
            mRtcEngine.setChannelProfile(io.agora.rtc.Constants.CHANNEL_PROFILE_LIVE_BROADCASTING);
            mRtcEngine.enableVideo();
            mRtcEngine.setLogFile(FileUtil.initializeLogFile(application));
        } catch (Exception e) {
            e.printStackTrace();
        }

        initConfig();
    }

    private void initConfig() {
        SharedPreferences pref = PrefManager.getPreferences(getApplicationContext());
        mGlobalConfig.setVideoDimenIndex(pref.getInt(
                Constants.PREF_RESOLUTION_IDX, Constants.DEFAULT_PROFILE_IDX));

        boolean showStats = pref.getBoolean(Constants.PREF_ENABLE_STATS, false);
        mGlobalConfig.setIfShowVideoStats(showStats);
        mStatsManager.enableStats(showStats);
    }

    public EngineConfig engineConfig() {
        return mGlobalConfig;
    }

    public RtcEngine rtcEngine() {
        return mRtcEngine;
    }

    public StatsManager statsManager() {
        return mStatsManager;
    }

    public void registerEventHandler(EventHandler handler) {
        mHandler.addHandler(handler);
    }

    public void removeEventHandler(EventHandler handler) {
        mHandler.removeHandler(handler);
    }

    public void onTerminate() {
        RtcEngine.destroy();
    }

    public Application getApplicationContext() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }


}
