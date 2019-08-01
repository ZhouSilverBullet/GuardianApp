package com.sdxxtop.guardianapp.TrackService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.track.AMapTrackClient;
import com.amap.api.track.ErrorCode;
import com.amap.api.track.OnTrackLifecycleListener;
import com.amap.api.track.TrackParam;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.app.App;
import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.ui.activity.PatrolRecordActivity;
import com.sdxxtop.guardianapp.utils.SpUtil;

/**
 * @author :  lwb
 * Date: 2019/7/11
 * Desc:
 */
public class TrackServiceUtil {
    private Context mContext;
    private static final String CHANNEL_ID_SERVICE_RUNNING = "CHANNEL_ID_SERVICE_RUNNING";
    private AMapTrackClient aMapTrackClient;

    private OnTrackLifecycleListener onTrackListener = new SimpleOnTrackLifecycleListener() {
        @Override
        public void onBindServiceCallback(int status, String msg) {
            Log.w("TrackService", "onBindServiceCallback, status: " + status + ", msg: " + msg);
        }

        @Override
        public void onStartTrackCallback(int status, String msg) {
            if (status == ErrorCode.TrackListen.START_TRACK_SUCEE || status == ErrorCode.TrackListen.START_TRACK_SUCEE_NO_NETWORK) {
                // 成功启动
                Toast.makeText(mContext, "启动服务成功", Toast.LENGTH_SHORT).show();
                if (aMapTrackClient != null) {
                    aMapTrackClient.setTrackId(mTrackId);
                    aMapTrackClient.startGather(onTrackListener);
                }
            } else if (status == ErrorCode.TrackListen.START_TRACK_ALREADY_STARTED) {
                // 已经启动
//                Toast.makeText(mContext, "服务已经启动", Toast.LENGTH_SHORT).show();
            } else {
//                Log.w("TrackService", "error onStartTrackCallback, status: " + status + ", msg: " + msg);
                Toast.makeText(mContext,
                        "error onStartTrackCallback, status: " + status + ", msg: " + msg,
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onStopTrackCallback(int status, String msg) {
            if (status == ErrorCode.TrackListen.STOP_TRACK_SUCCE) {
                // 成功停止
//                Toast.makeText(mContext, "停止服务成功", Toast.LENGTH_SHORT).show();
            } else {
//                Log.w("TrackService", "error onStopTrackCallback, status: " + status + ", msg: " + msg);
                Toast.makeText(mContext,
                        "error onStopTrackCallback, status: " + status + ", msg: " + msg,
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onStartGatherCallback(int status, String msg) {
            if (status == ErrorCode.TrackListen.START_GATHER_SUCEE) {
                Toast.makeText(mContext, "定位采集开启成功", Toast.LENGTH_SHORT).show();
            } else if (status == ErrorCode.TrackListen.START_GATHER_ALREADY_STARTED) {
//                Toast.makeText(mContext, "定位采集已经开启", Toast.LENGTH_SHORT).show();
            } else {
//                Log.w("TrackService", "error onStartGatherCallback, status: " + status + ", msg: " + msg);
                Toast.makeText(mContext,
                        "error onStartGatherCallback, status: " + status + ", msg: " + msg,
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onStopGatherCallback(int status, String msg) {
            if (status == ErrorCode.TrackListen.STOP_GATHER_SUCCE) {
//                Toast.makeText(mContext, "定位采集停止成功", Toast.LENGTH_SHORT).show();
            } else {
//                Log.w("TrackService", "error onStopGatherCallback, status: " + status + ", msg: " + msg);
                Toast.makeText(mContext,
                        "error onStopGatherCallback, status: " + status + ", msg: " + msg,
                        Toast.LENGTH_LONG).show();
            }
        }
    };

    private long mTrackId;

    public TrackServiceUtil() {
        this.mContext = App.getContext();
        aMapTrackClient = App.getAMapTrackClient();
    }

    public void stsrtTrackService(long serviceId, long terminalId, long trackId, Notification notification) {
        if (aMapTrackClient == null) return;
        mTrackId = trackId;
        TrackParam trackParam = new TrackParam(serviceId, terminalId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            Notification notification = App.getNotification();
            if (notification != null) {
                trackParam.setNotification(notification);
            }
        }
        aMapTrackClient.startTrack(trackParam, onTrackListener);
    }

    public void stopTrackService() {
        if (aMapTrackClient!=null){
            long serviceId = SpUtil.getLong(Constants.SERVICE_ID, 0);
            long terminalId = SpUtil.getLong(Constants.TERMINAL_ID, 0);
            aMapTrackClient.stopTrack(new TrackParam(serviceId, terminalId), new SimpleOnTrackLifecycleListener());
        }
    }

    /**
     * 在8.0以上手机，如果app切到后台，系统会限制定位相关接口调用频率
     * 可以在启动轨迹上报服务时提供一个通知，这样Service启动时会使用该通知成为前台Service，可以避免此限制
     */
    private Notification createNotification() {
        Notification.Builder builder;
        NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_SERVICE_RUNNING, "app service", NotificationManager.IMPORTANCE_LOW);
            nm.createNotificationChannel(channel);
            builder = new Notification.Builder(mContext, CHANNEL_ID_SERVICE_RUNNING);

        } else {
            builder = new Notification.Builder(mContext);
        }
        Intent nfIntent = new Intent(mContext, PatrolRecordActivity.class);
        nfIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//FLAG_CANCEL_CURRENT ,FLAG_IMMUTABLE,FLAG_UPDATE_CURRENT
        builder.setContentIntent(PendingIntent.getActivity(mContext, 0, nfIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("猎鹰sdk运行中")
                .setContentText("猎鹰sdk运行中");
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        nm.notify(100, notification);
        return notification;
    }

}
