package com.sdxxtop.guardianapp.ui.widget.timerselect;

import android.os.Handler;
import android.os.Message;

import com.contrarywind.adapter.WheelAdapter;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * @author :  lwb
 * Date: 2018/10/22
 * Desc:
 */

public class SelectTimeAdapter implements WheelAdapter<String> {

    private final List<DayBean> list = new ArrayList<>();
    private DataHandler mHandler;

    public SelectTimeAdapter() {
        mHandler = new DataHandler(this);

        if (list.size() == 0) {
            startThread();
        } else {
            if (mLoadDataFinishListener != null) {
                mLoadDataFinishListener.onLoadFinish();
            }
        }
    }

    private void startThread() {
        new Thread() {
            @Override
            public void run() {
                handleSelectTimeData();
            }
        }.start();
    }

    private void handleSelectTimeData() {
        Calendar instance = Calendar.getInstance();
        int yy = instance.get(Calendar.YEAR);
        int mm = instance.get(Calendar.MONTH) + 1;
        int dd = instance.get(Calendar.DATE);

        String todayMonth = mm < 10 ? "0" + mm : mm + "";
        String todayDay = dd < 10 ? "0" + dd : dd + "";

        String month = "";
        String day = "";
        String dayBeanDay = "";
        String dayBeanFormat = "";
        ArrayList<DayBean> dayBeanArrayList = new ArrayList<>();
        for (int k = -1; k <= 1; k++) {
            int tempYy = yy + k;
            for (int i = 1; i <= 12; i++) {
                int monthLastDay = getMonthLastDay(tempYy, i);
                for (int j = 1; j <= monthLastDay; j++) {
                    if (j < 10) {
                        day = "0" + j;
                    } else {
                        day = "" + j;
                    }
                    if (i < 10) {
                        month = "0" + i;
                    } else {
                        month = "" + i;
                    }
                    dayBeanFormat = tempYy + "-" + month + "-" + day;
                    dayBeanDay = tempYy + "年" + month + "月" + day + "日";
                    String week = getWeek(dayBeanFormat);
                    String sdayBeanDay = dayBeanDay + " 星期" + week;
                    if (dayBeanFormat.equals(yy + "-" + todayMonth + "-" + todayDay)) {
                        sdayBeanDay = "今天";
                    }
                    DayBean dayBean = new DayBean(dayBeanFormat, sdayBeanDay);
//                    list.add(dayBean);
                    dayBeanArrayList.add(dayBean);
                }
            }
        }

        mHandler.obtainMessage(100, dayBeanArrayList).sendToTarget();
    }

    @Override
    public int getItemsCount() {
        return list.size();
    }

    public List<DayBean> getList() {
        return list;
    }

    @Override
    public String getItem(int index) {
        return list.get(index).getDate();
    }

    @Override
    public int indexOf(String o) {
        for (int i = 0; i < list.size(); i++) {
            DayBean dayBean = list.get(i);
            if (dayBean.getDate().equals(o)) {
                return i;
            }
        }
        return 0;
    }

    public String getDate(int index) {
        return list.get(index).getFormatDate();
    }

    /**
     * 得到指定月的天数
     */
    public static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    private String getWeek(String pTime) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }
        return Week;
    }

    public class DayBean {
        private String formatDate;
        private String date;

        public DayBean(String formatDate, String date) {
            this.formatDate = formatDate;
            this.date = date;
        }

        public String getFormatDate() {
            return formatDate;
        }

        public void setFormatDate(String formatDate) {
            this.formatDate = formatDate;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    private LoadDataFinishListener mLoadDataFinishListener;

    public interface LoadDataFinishListener {
        void onLoadFinish();
    }

    public void setLoadDataFinishListener(LoadDataFinishListener loadDataFinishListener) {
        mLoadDataFinishListener = loadDataFinishListener;
    }

    private static class DataHandler extends Handler {
        private WeakReference<SelectTimeAdapter> mSelectTimeAdapterDef;

        public DataHandler(SelectTimeAdapter selectTimeAdapter) {
            mSelectTimeAdapterDef = new WeakReference<>(selectTimeAdapter);
        }

        @Override
        public void handleMessage(Message msg) {
            SelectTimeAdapter selectTimeAdapter = mSelectTimeAdapterDef.get();
            if (selectTimeAdapter != null) {
                selectTimeAdapter.list.addAll((ArrayList<DayBean>) msg.obj);
                if (selectTimeAdapter.mLoadDataFinishListener != null) {
                    selectTimeAdapter.mLoadDataFinishListener.onLoadFinish();
                }
//                selectTimeAdapter.notify();
            }
        }
    }

}
