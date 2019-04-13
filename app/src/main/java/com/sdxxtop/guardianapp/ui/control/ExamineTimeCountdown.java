package com.sdxxtop.guardianapp.ui.control;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;

import com.sdxxtop.guardianapp.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ExamineTimeCountdown implements Handler.Callback {
    public static final int COUNT_DOWN = 100;
    private Handler mHandler;
    private TextView mTextView;

    private CompleteListener mCompleteListener;

    private SimpleDateFormat sdf;

    public ExamineTimeCountdown(TextView textView, CompleteListener completeListener) {
        mTextView = textView;
        mHandler = new Handler(Looper.getMainLooper(), this);
        mCompleteListener = completeListener;
    }

    public void start(String examTime) {
        if (TextUtils.isEmpty(examTime)) {
            return;
        }
        sdf = new SimpleDateFormat("HH:mm");
        Message obtain = Message.obtain();
        obtain.obj = Long.parseLong(examTime) * 60;
        obtain.what = COUNT_DOWN;
        if (mHandler != null) {
            mHandler.sendMessage(obtain);
        }
    }


    public void destroy() {
        mTextView = null;
        mHandler = null;
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what != COUNT_DOWN) {
            return true;
        }

        if (mTextView == null) {
            return true;
        }

        long longTime = (long) msg.obj;
        longTime = longTime - 1;
        String showTime = getShowTime(longTime);
        mTextView.setText("考试时间: " + showTime);

        if (longTime <= 0) { //考试结束
            if (mCompleteListener != null) {
                mCompleteListener.onComplete();
            }
        } else {
            if (mHandler != null) {
                Message obtain = Message.obtain();
                obtain.obj = longTime;
                obtain.what = COUNT_DOWN;
                //延迟1s中
                mHandler.sendMessageDelayed(obtain, 1000);
            }
        }

        return true;
    }

    /**
     * @param longTime
     * @return
     */
    private String getShowTime(long longTime) {
        long hh = longTime / 60 / 60 % 60;
        long mm = longTime / 60 % 60;
        long ss = longTime % 60;

        String hour = hh > 10 ? String.valueOf(hh) : "0" + hh;
        String minute = mm > 10 ? String.valueOf(mm) : "0" + mm;
//        String second = ss > 10 ? String.valueOf(ss) : "0" + ss;
        return hour + ":" + minute /*+ ":" + second*/;
    }

    /**
     * 时间倒计时完成
     */
    public interface CompleteListener {
        void onComplete();
    }

}
