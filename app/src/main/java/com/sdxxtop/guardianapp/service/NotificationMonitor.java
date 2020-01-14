package com.sdxxtop.guardianapp.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NotificationMonitor extends NotificationListenerService {
    private static final String TAG = "SevenNLS";
    private static final String TAG_PRE = "[" + NotificationMonitor.class.getSimpleName() + "] ";
    private static final int EVENT_UPDATE_CURRENT_NOS = 0;
    public static final String ACTION_NLS_CONTROL = "com.seven.notificationlistenerdemo.NLSCONTROL";
    public static List<StatusBarNotification[]> mCurrentNotifications = new ArrayList<>();
    public static int mCurrentNotificationsCounts = 0;
    public static StatusBarNotification mPostedNotification;
    public static StatusBarNotification mRemovedNotification;
    private CancelNotificationReceiver mReceiver = new CancelNotificationReceiver();

    @SuppressLint("HandlerLeak")
    private Handler mMonitorHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_UPDATE_CURRENT_NOS:
                    updateCurrentNotifications();
                    break;
                default:
                    break;
            }
        }
    };

    class CancelNotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action;
            if (intent != null && intent.getAction() != null) {
                action = intent.getAction();
                // 更新通知数量
                updateCurrentNotifications();
                String command = intent.getStringExtra("command");
                switch (action) {
                    case ACTION_NLS_CONTROL:
                        if (TextUtils.equals(command, "cancel_last")) {
                            clearLocationNotice();
                        }
//                        else {
//                            cancelAllNotifications();
//                        }
                        break;
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        logNLS("onCreate...");
//        if (mReceiver != null) {
//            IntentFilter filter = new IntentFilter();
//            filter.addAction(ACTION_NLS_CONTROL);
//            registerReceiver(mReceiver, filter);
//        }
        mMonitorHandler.sendMessage(mMonitorHandler.obtainMessage(EVENT_UPDATE_CURRENT_NOS));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mReceiver != null) {
//            unregisterReceiver(mReceiver);
//        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        logNLS("onBind...");
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        updateCurrentNotifications();
        logNLS("onNotificationPosted...");
        logNLS("have " + mCurrentNotificationsCounts + " active notifications");
        mPostedNotification = sbn;
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        updateCurrentNotifications();
        logNLS("removed...");
        logNLS("have " + mCurrentNotificationsCounts + " active notifications");
        mRemovedNotification = sbn;
    }

    private void updateCurrentNotifications() {
        try {
            StatusBarNotification[] activeNos = getActiveNotifications();
            if (mCurrentNotifications.size() == 0) {
                mCurrentNotifications.add(null);
            }
            mCurrentNotifications.set(0, activeNos);
            mCurrentNotificationsCounts = activeNos.length;

            clearLocationNotice();
        } catch (Exception e) {
            logNLS("Should not be here!!");
            e.printStackTrace();
        }
    }

    public static StatusBarNotification[] getCurrentNotifications() {
        if (mCurrentNotifications.size() == 0) {
            logNLS("mCurrentNotifications size is ZERO!!");
            return null;
        }
        return mCurrentNotifications.get(0);
    }

    /**
     * 清除地理位置通知
     */
    @SuppressLint("NewApi")
    public void clearLocationNotice() {
        if (mCurrentNotifications != null && mCurrentNotificationsCounts >= 1) {
            StatusBarNotification[] notifyList = getCurrentNotifications();
            if (notifyList == null) {
                return;
            }
            try {
                for (int i = 0; i < notifyList.length; i++) {
                    StatusBarNotification sbnn = notifyList[i];
                    if (sbnn != null && sbnn.getNotification() != null) {
                        Bundle info = sbnn.getNotification().extras;
                        if (info != null) {
                            // 获取接收消息的抬头
                            String notificationTitle = info.getString(Notification.EXTRA_TITLE);
                            // 获取接收消息的内容
                            String notificationText = info.getString(Notification.EXTRA_TEXT);
                            if (notificationTitle != null && notificationText != null) {
                                Log.e("XSL_Test", "Notification posted " + notificationTitle + " & " + notificationText);
                                if (notificationTitle.equals("已通过GPS确定位置") && notificationText.equals(
                                        "数字罗庄")) {
                                    if (!TextUtils.isEmpty(sbnn.getKey())) {
                                        cancelNotification(sbnn.getKey());
                                    }
                                    return;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("XSL_Test", "Notification posted  exception");
            }
        }
    }

    private static void logNLS(Object object) {
        Log.i(TAG, TAG_PRE + object);
    }
}