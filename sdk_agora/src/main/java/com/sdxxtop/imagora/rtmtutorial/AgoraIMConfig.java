package com.sdxxtop.imagora.rtmtutorial;

import android.app.Application;


public class AgoraIMConfig {

    private ChatManager mChatManager;
    private Application application;

    private static AgoraIMConfig agoraIMConfig;

    public static AgoraIMConfig the() {
        return agoraIMConfig;
    }

    public void onCreate() {
        agoraIMConfig = this;

        mChatManager = new ChatManager(application);
        mChatManager.init();
    }

    public ChatManager getChatManager() {
        return mChatManager;
    }

    public void setApplication(Application app) {
        application = app;
    }
}

