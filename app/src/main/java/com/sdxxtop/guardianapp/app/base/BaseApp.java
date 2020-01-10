package com.sdxxtop.guardianapp.app.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.sdxxtop.base.BaseApplication;

import java.util.List;

import androidx.multidex.MultiDex;

public abstract class BaseApp extends BaseApplication {

    //以下属性应用于整个应用程序，合理利用资源，减少资源浪费
    private static Context mContext;//上下文
    private Thread mMainThread;//主线程
    private long mMainThreadId;//主线程id
    private Looper mMainLooper;//循环队列
    private Handler mHandler;//主线程Handler

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //对全局属性赋值
        mContext = getApplicationContext();
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
        mHandler = new Handler();

    }

    /**
     * 重启当前应用
     */
    public static void restart() {
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
    }

    public static Context getContext() {
        return mContext;
    }

    public Thread getMainThread() {
        return mMainThread;
    }

    public void setMainThread(Thread mMainThread) {
        this.mMainThread = mMainThread;
    }

    public long getMainThreadId() {
        return mMainThreadId;
    }

    public void setMainThreadId(long mMainThreadId) {
        this.mMainThreadId = mMainThreadId;
    }

    public Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public void setMainThreadLooper(Looper mMainLooper) {
        this.mMainLooper = mMainLooper;
    }

    public Handler getMainHandler() {
        return mHandler;
    }

    public void setMainHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public static String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = am.getRunningAppProcesses();
        if (processes == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo process : processes) {
            if (process.pid == android.os.Process.myPid()) {
                if (process.processName != null) {
                    return process.processName;
                }
            }
        }
        return null;
    }
}
