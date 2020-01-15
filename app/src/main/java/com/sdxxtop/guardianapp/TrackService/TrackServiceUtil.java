package com.sdxxtop.guardianapp.TrackService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.amap.api.track.AMapTrackClient;
import com.amap.api.track.ErrorCode;
import com.amap.api.track.OnTrackLifecycleListener;
import com.amap.api.track.TrackParam;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.app.App;
import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.ui.activity.HomeActivity;
import com.sdxxtop.guardianapp.utils.SpUtil;
import com.sdxxtop.guardianapp.utils.UIUtils;

/**
 * @author :  lwb
 * Date: 2019/7/11
 * Desc:
 */
public class TrackServiceUtil {

    private static final String TAG = "TrackServiceUtil";

    private Context mContext;
    private static final String CHANNEL_ID_SERVICE_RUNNING = "CHANNEL_ID_SERVICE_RUNNING";
    private AMapTrackClient aMapTrackClient;

    private OnTrackLifecycleListener onTrackListener = new SimpleOnTrackLifecycleListener() {
        @Override
        public void onBindServiceCallback(int status, String msg) {
            Log.e(TAG, "onBindServiceCallback, status: " + status + ", msg: " + msg);
        }

        @Override
        public void onStartTrackCallback(int status, String msg) {
            if (status == ErrorCode.TrackListen.START_TRACK_SUCEE || status == ErrorCode.TrackListen.START_TRACK_SUCEE_NO_NETWORK) {
                // 成功启动
                UIUtils.showToast("启动服务成功");
                if (aMapTrackClient != null) {
                    aMapTrackClient.setTrackId(mTrackId);
                    aMapTrackClient.startGather(onTrackListener);
                }
            } else if (status == ErrorCode.TrackListen.START_TRACK_ALREADY_STARTED) {
                // 已经启动
                Log.e(TAG, "服务已经启动: " + status + ", msg: " + msg);
                stopTrackService();
                stsrtTrackService(null);
            } else {
                Log.e(TAG, "启动服务失败: " + status + ", msg: " + msg);
                if (aMapTrackClient != null && onTrackListener != null && trackParam != null) {
                    aMapTrackClient.startTrack(trackParam, onTrackListener);
                }
            }
        }

        @Override
        public void onStopTrackCallback(int status, String msg) {
            if (status == ErrorCode.TrackListen.STOP_TRACK_SUCCE) {
                // 成功停止
                Log.e(TAG, "停止服务成功: " + status + ", msg: " + msg);
            } else {
                Log.e(TAG, "停止服务失败: " + status + ", msg: " + msg);
            }
        }

        @Override
        public void onStartGatherCallback(int status, String msg) {
            if (status == ErrorCode.TrackListen.START_GATHER_SUCEE) {
                Log.e(TAG, "定位采集开启成功: " + status + ", msg: " + msg);
            } else if (status == ErrorCode.TrackListen.START_GATHER_ALREADY_STARTED) {
                Log.e(TAG, "定位采集已经开启: " + status + ", msg: " + msg);
            } else {
                Log.e(TAG, "定位采集失败重新开启: " + status + ", msg: " + msg);
                if (aMapTrackClient != null && onTrackListener != null) {   // 开启定位采集失败重新开启
                    aMapTrackClient.setTrackId(mTrackId);
                    aMapTrackClient.startGather(onTrackListener);
                }
            }
        }

        @Override
        public void onStopGatherCallback(int status, String msg) {
            if (status == ErrorCode.TrackListen.STOP_GATHER_SUCCE) {
//                Toast.makeText(mContext, "定位采集停止成功", Toast.LENGTH_SHORT).show();
            } else {
//                Log.w("TrackService", "error onStopGatherCallback, status: " + status + ", msg: " + msg);
            }
        }
    };

    private long mTrackId;
    private TrackParam trackParam;

    private TrackServiceUtil() {
        this.mContext = App.getContext();
        aMapTrackClient = App.getAMapTrackClient();
    }

    public void stsrtTrackService(Notification notification) {
        long serviceId = SpUtil.getLong(Constants.SERVICE_ID, 0);
        long terminalId = SpUtil.getLong(Constants.TERMINAL_ID, 0);
        long trackId = SpUtil.getLong(Constants.TRACK_ID, 0);

        Log.e(TAG, "开启猎鹰--   serviceId = " + serviceId + "    terminalId = " + terminalId + "    trackId = " + trackId);
        mTrackId = trackId;
        if (trackParam == null) {
            trackParam = new TrackParam(serviceId, terminalId);  // 开启的track
            trackParam.setTrackId(trackId);
        }

        if (aMapTrackClient == null) {
            aMapTrackClient = App.getAMapTrackClient();
        }
        if (notification == null) {
            notification = createNotification();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notification != null) {
                trackParam.setNotification(notification);
            }
        }
        aMapTrackClient.startTrack(trackParam, onTrackListener);
    }

    public void stopTrackService() {
        if (aMapTrackClient != null) {
            long serviceId = SpUtil.getLong(Constants.SERVICE_ID, 0);
            long terminalId = SpUtil.getLong(Constants.TERMINAL_ID, 0);
            long trackId = SpUtil.getLong(Constants.TRACK_ID, 0);
            Log.e(TAG, "退出猎鹰--   serviceId = " + serviceId + "    terminalId = " + terminalId);
            aMapTrackClient.stopTrack(new TrackParam(serviceId, terminalId), onTrackListener);
            aMapTrackClient.stopGather(onTrackListener);
            trackParam = null;
            aMapTrackClient = null;
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
        Intent nfIntent = new Intent(mContext, HomeActivity.class);
        nfIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //FLAG_CANCEL_CURRENT ,FLAG_IMMUTABLE,FLAG_UPDATE_CURRENT
        builder.setContentIntent(PendingIntent.getActivity(mContext, 0, nfIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("数字罗庄")
                .setContentText("数字罗庄运行中");
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        nm.notify(100, notification);
        return notification;
    }


    public static TrackServiceUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final TrackServiceUtil INSTANCE = new TrackServiceUtil();
    }
}
