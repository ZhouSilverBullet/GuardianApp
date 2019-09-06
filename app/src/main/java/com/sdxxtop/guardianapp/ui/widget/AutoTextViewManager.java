package com.sdxxtop.guardianapp.ui.widget;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-22 14:17
 * Version: 1.0
 * Description:
 */
public class AutoTextViewManager {
    private AutoTextView autoTextView;
    private Runnable runnable;

    private List<String> data;

    public AutoTextViewManager(AutoTextView autoTextView) {
        this.autoTextView = autoTextView;
    }

    public void setData(List<String> data) {
        if (data != null) {
            this.data = data;
        } else {
            this.data = new ArrayList<>();
        }
    }

    /**
     * 开始轮播
     */
    public void start() {
        if (autoTextView == null) {
            return;
        }
        String rcmdName = getTitle();
        if (TextUtils.isEmpty(rcmdName)) {
            return;
        }
        autoTextView.setText(rcmdName);
        isStart = true;
        runnable = new Runnable() {
            @Override
            public void run() {
                start();
                autoTextView.next();
            }
        };
        autoTextView.postDelayed(runnable, 3000);
    }

    public boolean isStart;
    private int mLocation; //记录下次的滚动角标

    private String getTitle() {
        if (data != null && data.size() > 0) {
            int location = getRandomNumber(data.size());
            String entry = data.get(location);
            if (entry != null && !TextUtils.isEmpty(entry)) {
                return entry;
            }
        }
        return "";
    }

    private int getRandomNumber(int size) {
        if (size == 1) {
            return 0;
        } else {

            mLocation = (mLocation == size - 1) ? 0 : (mLocation + 1);
            return mLocation;
        }
    }

    /**
     * 暂停轮播
     */
    public void removeAutoTextRunnable() {
        if (autoTextView != null) {
            autoTextView.removeCallbacks(runnable);
            runnable = null;
        }
    }
}
