package com.sdxxtop.guardianapp.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
    public static List<StatusBarNotification[]> mCurrentNotifications = new ArrayList<StatusBarNotification[]>();
    public static int mCurrentNotificationsCounts = 0;
    public static StatusBarNotification mPostedNotification;
    public static StatusBarNotification mRemovedNotification;
    private CancelNotificationReceiver mReceiver = new CancelNotificationReceiver();
    // String a;

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
        @SuppressLint("NewApi")
        @Override
        public void onReceive(Context context, Intent intent) {
            String action;
            if (intent != null && intent.getAction() != null) {
                action = intent.getAction();
                if (action.equals(ACTION_NLS_CONTROL)) {
                    String command = intent.getStringExtra("command");
                    if (TextUtils.equals(command, "cancel_last")) {
                        if (mCurrentNotifications != null && mCurrentNotificationsCounts >= 1) {
//                            StatusBarNotification sbnn = getCurrentNotifications()[mCurrentNotificationsCounts - 1];
                            StatusBarNotification[] notifyList = getCurrentNotifications();
                            if (notifyList == null) {
                                return;
                            }
                            for (int i = 0; i < notifyList.length; i++) {
                                StatusBarNotification sbnn = notifyList[i];
                                if (sbnn != null) {
                                    Bundle info = sbnn.getNotification().extras;
                                    if (info.getString(Notification.EXTRA_TITLE).equals("已通过GPS确定位置") && info.getString(Notification.EXTRA_TEXT).equals(
                                            "数字罗庄")) {
//                                        cancelNotification(sbnn.getPackageName(), sbnn.getTag(), sbnn.getId());
                                        cancelNotification(sbnn.getKey());
                                        return;
                                    }
                                }
                            }
                        }
                    } else if (TextUtils.equals(command, "cancel_all")) {
                        cancelAllNotifications();
                    }
                }
            }
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        logNLS("onCreate...");
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_NLS_CONTROL);
        registerReceiver(mReceiver, filter);
        mMonitorHandler.sendMessage(mMonitorHandler.obtainMessage(EVENT_UPDATE_CURRENT_NOS));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // a.equals("b");
        logNLS("onBind...");
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        updateCurrentNotifications();
        logNLS("onNotificationPosted...");
        logNLS("have " + mCurrentNotificationsCounts + " active notifications");
        mPostedNotification = sbn;

       /* Bundle extras = sbn.getNotification().extras;
        String
                notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        Bitmap notificationLargeIcon = ((Bitmap)
                extras.getParcelable(Notification.EXTRA_LARGE_ICON));
        Bitmap
                notificationSmallIcon = ((Bitmap)
                extras.getParcelable(Notification.EXTRA_SMALL_ICON));
        CharSequence
                notificationText = extras.getCharSequence(Notification.EXTRA_TEXT);
        CharSequence notificationSubText =
                extras.getCharSequence(Notification.EXTRA_SUB_TEXT);
        Log.i("SevenNLS", "notificationTitle:" + notificationTitle);
        Log.i("SevenNLS", "notificationText:" + notificationText);
        Log.i("SevenNLS", "notificationSubText:" + notificationSubText);
        Log.i("SevenNLS",
                "notificationLargeIcon is null:" + (notificationLargeIcon == null));
        Log.i("SevenNLS",
                "notificationSmallIcon is null:" + (notificationSmallIcon == null));*/
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        updateCurrentNotifications();
        logNLS("removed...");
        logNLS("have " + mCurrentNotificationsCounts + " active notifications");
        mRemovedNotification = sbn;

        // TODO Auto-generated method stub
        Bundle extras = sbn.getNotification().extras;
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        String notificationText = extras.getString(Notification.EXTRA_TEXT);
        Log.i("XSL_Test", "Notification posted " + notificationTitle + " & " + notificationText);
    }

    private void updateCurrentNotifications() {
        try {
            StatusBarNotification[] activeNos = getActiveNotifications();
            if (mCurrentNotifications.size() == 0) {
                mCurrentNotifications.add(null);
            }
            mCurrentNotifications.set(0, activeNos);
            mCurrentNotificationsCounts = activeNos.length;
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

    private static void logNLS(Object object) {
        Log.i(TAG, TAG_PRE + object);
    }

}