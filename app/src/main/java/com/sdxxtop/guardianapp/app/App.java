package com.sdxxtop.guardianapp.app;


import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Process;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.register.HuaWeiRegister;
import com.alibaba.sdk.android.push.register.MiPushRegister;
import com.amap.api.track.AMapTrackClient;
import com.amap.api.track.AMapTrackService;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.sdxxtop.guardianapp.BuildConfig;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.app.base.BaseApp;
import com.sdxxtop.guardianapp.di.component.AppComponent;
import com.sdxxtop.guardianapp.di.component.DaggerAppComponent;
import com.sdxxtop.guardianapp.di.module.AppModule;
import com.sdxxtop.guardianapp.model.NetWorkSession;
import com.sdxxtop.guardianapp.service.ForegroundService;
import com.sdxxtop.sdkagora.AgoraSession;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.util.List;


public class App extends BaseApp {

    private static App instance;
    private static AppComponent appComponent;
    private static final String TAG = "Init";
    private static AMapTrackClient aMapTrackClient;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initLogger();
        AppSession.getInstance().init(this);
        initBaiduFace();
        CrashHandler.getInstance().init(this);
        initCloudChannel(this);
        initUM();
        initAMapTrackClient();
        startAlarm();
//        initWebViewServer();

        if (isZhidianProcess(getCurProcessName())) {
            AgoraSession.init(this);
        }

        NetWorkSession.init(this, BuildConfig.DEBUG);
    }

    private void initWebViewServer() {
//        startService(new Intent(this, OptimizationService.class));
//
//        if ("com.sdxxtop.guardianapp:remoteweb".equals(getProcessName(this))) {
//            new ProgressWebView(this);
//        }
    }

    private void initUM() {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */
        /**
         * 注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调
         * 用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
         * UMConfigure.init调用中appkey和channel参数请置为null）。
         */
        UMConfigure.init(this, "5d31b2930cafb20936000e67", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        if (BuildConfig.DEBUG) {
            MobclickAgent.setCatchUncaughtExceptions(false);// 关闭错误统计
        }
    }

    private void initBaiduFace() {
        FaceSDKManager.getInstance().initialize(this, "luozhuang-face-android", "idl-license.face-android");
    }

    //初始化logger
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)      //（可选）是否显示线程信息。 默认值为true
                .methodCount(2)               // （可选）要显示的方法行数。 默认2
                .methodOffset(7)               // （可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
//                .logStrategy(new LogcatLogStrategy())  //（可选）更改要打印的日志策略。 默认LogCat
                .tag("zhou-TAG")                  //（可选）每个日志的全局标记。 默认PRETTY_LOGGER（如上图）
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public static App getInstance() {
        return instance;
    }

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder().appModule(new AppModule(instance)).build();
        }
        return appComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        AppSession.getInstance().clear();
        AgoraSession.onTerminate();
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.color_F4F4F4, R.color.color_303030);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter classicsFooter = new ClassicsFooter(context);
                classicsFooter.setBackgroundColor(context.getResources().getColor(R.color.color_F4F4F4));
                return classicsFooter.setDrawableSize(20);
            }
        });
    }


    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
        // 注册方法会自动判断是否支持小米系统推送，如不支持会跳过注册。
        MiPushRegister.register(applicationContext, "2882303761518019696", "5601801991696");
        // 注册方法会自动判断是否支持华为系统推送，如不支持会跳过注册。
        HuaWeiRegister.register(applicationContext);

        PushServiceFactory.init(applicationContext);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.e(TAG, "init cloudchannel success");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.e(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
        initNotificationChannel();
    }

    //Android 8.0以上设备通知接收
    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // 通知渠道的id
            String id = "1";
            // 用户可以看到的通知渠道的名字.
            CharSequence name = "数字罗庄";
            // 用户可以看到的通知渠道的描述
            String description = "notification description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // 配置通知渠道的属性
            mChannel.setDescription(description);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(false);
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(false);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            //最后在notificationmanager中创建该通知渠道
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }

    public void startAlarm() {
        //首先获得系统服务
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //设置闹钟的意图，我这里是去调用一个服务，该服务功能就是获取位置并且上传
        Intent intent = new Intent(this, AMapTrackService.class);
        PendingIntent pendSender = PendingIntent.getService(this, 0, intent, 0);
        am.cancel(pendSender);
        //AlarmManager.RTC_WAKEUP ;这个参数表示系统会唤醒进程；设置的间隔时间是1分钟
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60 * 1000, pendSender);
    }

    /********** 猎鹰相关 ***********/

    private void initAMapTrackClient() {
        aMapTrackClient = new AMapTrackClient(getApplicationContext());
        aMapTrackClient.setInterval(5, 30);
    }

    public static AMapTrackClient getAMapTrackClient() {
        if (aMapTrackClient != null) {
            return aMapTrackClient;
        }
        return null;
    }

    private Intent intent;

    public void startTrackService() {
        if (!isServiceExisted(ForegroundService.class.getName())) {
            intent = new Intent(App.getInstance(), ForegroundService.class);
            startService(intent);
        } else {
            if (intent!=null){
                stopService(intent);
            }
            intent = new Intent(App.getInstance(), ForegroundService.class);
            startService(intent);
        }
    }

    /**
     * 判断service是否已经运行
     * 必须判断uid,因为可能有重名的Service,所以要找自己程序的Service,不同进程只要是同一个程序就是同一个uid,个人理解android系统中一个程序就是一个用户
     * 用pid替换uid进行判断强烈不建议,因为如果是远程Service的话,主进程的pid和远程Service的pid不是一个值,在主进程调用该方法会导致Service即使已经运行也会认为没有运行
     * 如果Service和主进程是一个进程的话,用pid不会出错,但是这种方法强烈不建议,如果你后来把Service改成了远程Service,这时候判断就出错了
     *
     * @param className Service的全名,例如PushService.class.getName()
     * @return true:Service已运行 false:Service未运行
     */
    public boolean isServiceExisted(String className) {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = am.getRunningServices(Integer.MAX_VALUE);
        int myUid = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE_1_1) {
            myUid = Process.myUid();
        }
        for (ActivityManager.RunningServiceInfo runningServiceInfo : serviceList) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                if (runningServiceInfo.uid == myUid && runningServiceInfo.service.getClassName().equals(className)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getCurProcessName() {
        try {
            int pid = android.os.Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                    .getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
        } catch (Exception e) {
            Log.e("getCurProcessName:", e.getMessage());
        }
        return getPackageName();
    }

    public boolean isZhidianProcess(String process) {
        return getPackageName().equals(process);
    }

}
