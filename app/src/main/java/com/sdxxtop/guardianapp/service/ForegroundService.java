package com.sdxxtop.guardianapp.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.TrackService.TrackServiceUtil;
import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.ui.activity.HomeActivity;
import com.sdxxtop.guardianapp.utils.SpUtil;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


public class ForegroundService extends Service {

    private static final String TAG = "ForegroundService";
    private static final int SERVICE_ID = 1;
    private Notification notification;
    private TrackServiceUtil trackUtil;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        trackUtil = new TrackServiceUtil();
        Log.e(TAG, "ForegroundService 服务创建了");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) { //Android4.3-->Android7.0
            //将service设置成前台服务
            startForeground(SERVICE_ID, new Notification());
            //删除通知栏消息
            startService(new Intent(this, InnerService.class));

            notification = new Notification.Builder(getApplicationContext()).build();
        } else { // 8.0 及以上
            //通知栏消息需要设置channel
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //NotificationManager.IMPORTANCE_MIN 通知栏消息的重要级别  最低，不让弹出
            //IMPORTANCE_MIN 前台时，在阴影区能看到，后台时 阴影区不消失，增加显示 IMPORTANCE_NONE时 一样的提示
            //IMPORTANCE_NONE app在前台没有通知显示，后台时有
            NotificationChannel channel = new NotificationChannel("channel", "app service", NotificationManager.IMPORTANCE_MIN);
            if (manager != null) {
                Intent notificationIntent = new Intent(this, HomeActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                        notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                manager.createNotificationChannel(channel);
                notification = new NotificationCompat.Builder(this, "channel")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setWhen(System.currentTimeMillis())
                        .setTicker("数字罗庄")
                        .setContentTitle("数字罗庄")
                        .setContentText("App使用中")
                        .setOngoing(true)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(false)
                        .build();
                //将service设置成前台服务，8.x退到后台会显示通知栏消息，9.0会立刻显示通知栏消息
                startForeground(SERVICE_ID, notification);
            }
        }

        trackUtil = new TrackServiceUtil();
        long serviceId = SpUtil.getLong(Constants.SERVICE_ID, 0);
        long terminalId = SpUtil.getLong(Constants.TERMINAL_ID, 0);
        long trackId = SpUtil.getLong(Constants.TRACK_ID, 0);
        if (notification != null) {
            trackUtil.stopTrackService();  // 1:先关闭猎鹰服务 2: 在开启猎鹰服务
            trackUtil.stsrtTrackService(serviceId, terminalId, trackId, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public static class InnerService extends Service {

        @Override
        public void onCreate() {
            super.onCreate();
            Log.e(TAG, "InnerService 服务创建了");
            // 让服务变成前台服务
            startForeground(SERVICE_ID, new Notification());
            // 关闭自己
            stopSelf();
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }
}
