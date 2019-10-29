package com.sdxxtop.guardianapp.utils;

import android.view.View;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-31 11:38
 * Version: 1.0
 * Description:
 */
public abstract class SingleClickListener implements View.OnClickListener {
    private long mLastClickTime;
    private long timeInterval = 3000L;

    public SingleClickListener() {

    }

    public SingleClickListener(long interval) {
        this.timeInterval = interval;
    }

    @Override
    public final void onClick(View v) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastClickTime > timeInterval) {
            // 单次点击事件
            onSingleClick(v);
            mLastClickTime = nowTime;
        }

//        else {
//            // 快速点击事件
//            onFastClick();
//        }
    }

    protected abstract void onSingleClick(View v);
//    protected abstract void onFastClick();
}
