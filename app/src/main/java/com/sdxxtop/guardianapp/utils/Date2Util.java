package com.sdxxtop.guardianapp.utils;

import android.text.TextUtils;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/5/17.
 */

public class Date2Util {


    /**
     * 获取日期带0
     *
     * @return
     */
    public static String getDate() {
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        String time = year + "-" + getZeroTime(month) + "-" + getZeroTime(date);
        return time;
    }


    public static String getClassroomFormatterTime(String sDate) {
        String formatDate = "";
        String formatMonth = "";
        String formatDay = "";
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat dft2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dft2.parse(sDate);
            instance.setTime(date);
            int day = instance.get(Calendar.DATE);
            int month = instance.get(Calendar.MONTH) + 1;
            formatMonth = month < 10 ? "0" + month : "" + month;
            formatDay = day < 10 ? "0" + day : "" + day;
            formatDate = " " + formatMonth + "." + formatDay + " ";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatDate;
    }

    /**
     * 获取日期带0
     *
     * @return
     */
    public static String getLeaveTime(String leaveTime) {
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        String time = year + "-" + getZeroTime(month) + "-" + getZeroTime(date) + " " + leaveTime + ":" + getZeroTime(second);
        return time;
    }

    /**
     * 获取日期带0
     *
     * @return
     */
    public static String getDateToDay(int type) {
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        String time = "";
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        if (type == 1) {
            time = year + "-" + getZeroTime(month) + "-" + getZeroTime(date) + " ";
        } else {
            time = year + "-" + getZeroTime(month) + "-" + getZeroTime(date) + " " + getZeroTime(hour) + ":" + getZeroTime(minute);
        }
        return time;
    }

    /**
     * @param dayDate 2018年1月5日
     * @param day     前一天是负值
     */
    public static String[] getUpOrDownDay(String dayDate, int day) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat dft2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dft2.parse(dayDate);
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            instance.add(Calendar.DAY_OF_MONTH, day);
            Date time = instance.getTime();
            String upOrDownDay = dft.format(time);
            String format = dft2.format(time);
            return new String[]{upOrDownDay, format};
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param dayDate 2018年1月5日
     */
    public static String getUpOrDownMonth(String dayDate) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy年MM月");
        SimpleDateFormat dft2 = new SimpleDateFormat("yyyy-MM");
        try {
            Date date = dft.parse(dayDate);
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            Date time = instance.getTime();
            String format = dft2.format(time);
            return format;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSubmissionTime(String sign_time) {
        try {
            Date date = new Date(DateUtil.convertTimeToLong(sign_time, "yyyy-MM-dd HH:mm:ss"));
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            int hour = instance.get(Calendar.HOUR_OF_DAY);
            int minute = instance.get(Calendar.MINUTE);
            String hourString = "";
            String minuteString = "";
            if (hour < 10) {
                hourString = "0" + hour;
            } else {
                hourString = "" + hour;
            }
            if (minute < 10) {
                minuteString = "0" + minute;
            } else {
                minuteString = "" + minute;
            }
            return hourString + ":" + minuteString;
        } catch (Exception e) {
        }
        return "";
    }


    public static List getWeekendInMonth(int year, int month) {
        List<Integer> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);// 不设置的话默认为当年
        calendar.set(Calendar.MONTH, month - 1);// 设置月份
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为当月第一天
        int daySize = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);// 当月最大天数
        for (int i = 0; i < daySize; i++) {
            calendar.add(Calendar.DATE, 1);//在第一天的基础上加1
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            if (week == Calendar.SATURDAY || week == Calendar.SUNDAY) {// 1代表周日，7代表周六 判断这是一个星期的第几天从而判断是否是周末
                list.add(calendar.get(Calendar.DAY_OF_MONTH));// 得到当天是一个月的第几天
            }
        }
        return list;
    }


    public static List<String> getWeekendInMonth(String[] split, int year, int month) {
        List<Integer> weekList = new ArrayList<>();
        for (String s : split) {
            if (!TextUtils.isEmpty(s)) {
                weekList.add(Integer.parseInt(s));
            }
        }

        List<Integer> weekDayList = new ArrayList<>();
        for (Integer integer : weekList) {
            switch (integer) {
                case 1:
                    weekDayList.add(Calendar.MONDAY);
                    break;
                case 2:
                    weekDayList.add(Calendar.TUESDAY);
                    break;
                case 3:
                    weekDayList.add(Calendar.WEDNESDAY);
                    break;
                case 4:
                    weekDayList.add(Calendar.THURSDAY);
                    break;
                case 5:
                    weekDayList.add(Calendar.FRIDAY);
                    break;
                case 6:
                    weekDayList.add(Calendar.SATURDAY);
                    break;
                case 7:
                    weekDayList.add(Calendar.SUNDAY);
                    break;
            }
        }

        //用于存放没有上班的日期
        List<String> dateList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);// 不设置的话默认为当年
        calendar.set(Calendar.MONTH, month - 1);// 设置月份
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为当月第一天

        String stringMonth;
        if (month < 10) {
            stringMonth = "0" + month;
        } else {
            stringMonth = "" + month;
        }


        int daySize = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);// 当月最大天数
        for (int i = 0; i < daySize; i++) {
            calendar.add(Calendar.DATE, 1);//在第一天的基础上加1
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            if (!weekDayList.contains(week)) { //不包含这一天就进行添加
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                String stringDay;
                if (day < 10) {
                    stringDay = "0" + day;
                } else {
                    stringDay = "" + day;
                }
                String tempStringDate = year + "-" + stringMonth + "-" + stringDay;
                dateList.add(tempStringDate);
            }

        }
        return dateList;
    }

    /**
     * 取得当前日期所在周的第一天
     *
     * @return
     */
    public static String getFirstDayOfWeek(Date date) {
        String firstTime = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Sunday

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        firstTime = "" + year + "-" + getZeroTime(month) + "-" + getZeroTime(day);

        return firstTime;
    }

    /**
     * 得到本周周一
     *
     * @return yyyy-MM-dd
     */
    public static Date getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        return c.getTime();
    }

    /**
     * 得到本周周日
     *
     * @return yyyy-MM-dd
     */
    public static Date getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        return c.getTime();
    }

    /**
     * 得到本周周日
     *
     * @return yyyy-MM-dd
     */
    public static Date getSaturdayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 6);
        return c.getTime();
    }

    /**
     * 后面日期不进行选择
     *
     * @param date
     * @return
     */
    public static boolean compareToday2(CalendarDay date) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR); //年
        int month = calendar.get(Calendar.MONTH); //月
        int day = calendar.get(Calendar.DAY_OF_MONTH); //日
        int year1 = date.getYear();
        int month1 = date.getMonth();
        int day1 = date.getDay();
        if (year1 > year) {
            return true;
        } else if (year1 == year && month1 > month) {
            return true;
        } else if (year1 == year && month1 == month && day1 > day) {
            return true;
        }

        return false;
    }

    public static String getWeixinShowTime(String time) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = dft.parse(time);
            return getWeixinShowTime(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取本月的最后一天
     *
     * @return
     */
    public static Date getLastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDayOfMonth = calendar.getTime();
        return lastDayOfMonth;
    }

    public static String getWeixinShowTime(Date time) {
        try {
            Calendar currentTime = Calendar.getInstance();
            currentTime.setTime(time);
            Calendar nowTime = Calendar.getInstance();

            int nowYear = nowTime.get(Calendar.YEAR);
            int nowMonth = nowTime.get(Calendar.MONTH);
            int nowDay = nowTime.get(Calendar.DAY_OF_MONTH);
            int nowHour = nowTime.get(Calendar.HOUR_OF_DAY);
            int nowMinute = nowTime.get(Calendar.MINUTE);

            int currentYear = currentTime.get(Calendar.YEAR);
            int currentMonth = currentTime.get(Calendar.MONTH);
            int currentDay = currentTime.get(Calendar.DAY_OF_MONTH);
            int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
            int currentMinute = currentTime.get(Calendar.MINUTE);

            if (nowYear != currentYear) {
                //不是同一年  6月30日 20：10
                return currentYear + "/" + getZeroTime(currentMonth + 1) + "/" + getZeroTime(currentDay) + "日" /*+ " " + getZeroTime(currentHour) + ":" +
                getZeroTime(currentMinute)*/;
            }

            if (nowMonth != currentMonth) {
                //不是同一月  6月30日 20：10
                return getZeroTime(currentMonth + 1) + "月" + getZeroTime(currentDay) + "日" + " " + getZeroTime(currentHour) + ":" + getZeroTime(currentMinute);
            }

            int totalDay = Math.abs(nowDay - currentDay);
            //小于等于六天 要带星期几
            //如：星期四  20：10  不能是昨天和今天
            if (totalDay <= 6 && totalDay >= 2) {
                int i = currentTime.get(Calendar.DAY_OF_WEEK);
                String tempWeekName = "";
                switch (i) {
                    case Calendar.MONDAY:
                        tempWeekName = "星期一";
                        break;

                    case Calendar.TUESDAY:
                        tempWeekName = "星期二";
                        break;

                    case Calendar.WEDNESDAY:
                        tempWeekName = "星期三";
                        break;

                    case Calendar.THURSDAY:
                        tempWeekName = "星期四";
                        break;

                    case Calendar.FRIDAY:
                        tempWeekName = "星期五";
                        break;

                    case Calendar.SATURDAY:
                        tempWeekName = "星期六";
                        break;
                    default:
                        //Calendar.SUNDAY
                        tempWeekName = "星期日";
                        break;
                }

                return tempWeekName + " " + getZeroTime(currentHour) + ":" + getZeroTime(currentMinute);
            }

            if (totalDay == 1) {
                return "昨天" + " " + getZeroTime(currentHour) + ":" + getZeroTime(currentMinute);
            }

            if (totalDay == 0) {
                if (currentHour == nowHour) {
                    if (Math.abs(currentMinute - nowMinute) <= 1) {
                        return "刚刚";
                    }
                }
                return getZeroTime(currentHour) + ":" + getZeroTime(currentMinute);
            }

            //不可能有这种情况了,暂时写着容错处理 和 最开始的那种相同
            return getZeroTime(currentMonth + 1) + "月" + getZeroTime(currentDay) + "日" + " " + getZeroTime(currentHour) + ":" + getZeroTime(currentMinute);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDateTime() {
        Calendar now = Calendar.getInstance();
        String time = "" + now.get(Calendar.YEAR) + "-" + Date2Util.getZeroTime((now.get(Calendar.MONTH) + 1)) + "-" + Date2Util.getZeroTime(now.get(Calendar
                .DAY_OF_MONTH));
        return time;

    }


    /**
     * 产品需要修改的时间
     * 举例如下图（3号是今天）：
     * <p>
     * 1小时内：23分钟前
     * 超过一小时：20:23
     * 2号发的：昨天
     * 6.27号~7.1号：星期三~星期日
     * 26号及之前：7/26
     * 去年及去年之前：2017/7/25
     *
     * @param time
     * @return
     */
    public static String getWeixinShowTime0(Date time) {
        try {
            Calendar currentTime = Calendar.getInstance();
            currentTime.setTime(time);
            Calendar nowTime = Calendar.getInstance();

            int nowYear = nowTime.get(Calendar.YEAR);
            int nowMonth = nowTime.get(Calendar.MONTH);
            int nowDay = nowTime.get(Calendar.DAY_OF_MONTH);
            int nowHour = nowTime.get(Calendar.HOUR_OF_DAY);
            int nowMinute = nowTime.get(Calendar.MINUTE);

            int currentYear = currentTime.get(Calendar.YEAR);
            int currentMonth = currentTime.get(Calendar.MONTH);
            int currentDay = currentTime.get(Calendar.DAY_OF_MONTH);
            int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
            int currentMinute = currentTime.get(Calendar.MINUTE);

            if (nowYear != currentYear) {
                //不是同一年  6月30日 20：10
                return currentYear + "/" + getZeroTime(currentMonth + 1) + "/" + getZeroTime(currentDay) + "日" /*+ " " + getZeroTime(currentHour) + ":" +
                getZeroTime(currentMinute)*/;
            }

            if (nowMonth != currentMonth) {
                //不是同一月  07/30
                return getZeroTime(currentMonth + 1) + "/" + getZeroTime(currentDay) /*+ "日" + " " + getZeroTime(currentHour) + ":" + getZeroTime
                (currentMinute)*/;
            }

            int totalDay = Math.abs(nowDay - currentDay);

            if (totalDay > 7) {
                return getZeroTime(currentMonth + 1) + "/" + getZeroTime(currentDay);
            } else {
                //小于等于六天 要带星期几
                //如：星期四  20：10  不能是昨天和今天
                if (totalDay <= 6 && totalDay >= 2) {
                    int i = currentTime.get(Calendar.DAY_OF_WEEK);
                    String tempWeekName = "";
                    switch (i) {
                        case Calendar.MONDAY:
                            tempWeekName = "星期一";
                            break;

                        case Calendar.TUESDAY:
                            tempWeekName = "星期二";
                            break;

                        case Calendar.WEDNESDAY:
                            tempWeekName = "星期三";
                            break;

                        case Calendar.THURSDAY:
                            tempWeekName = "星期四";
                            break;

                        case Calendar.FRIDAY:
                            tempWeekName = "星期五";
                            break;

                        case Calendar.SATURDAY:
                            tempWeekName = "星期六";
                            break;
                        default:
                            //Calendar.SUNDAY
                            tempWeekName = "星期日";
                            break;
                    }

                    return tempWeekName /*+ " " + getZeroTime(currentHour) + ":" + getZeroTime(currentMinute)*/;
                }

                if (totalDay == 1) {
                    return "昨天" /*+ " " + getZeroTime(currentHour) + ":" + getZeroTime(currentMinute)*/;
                }

                if (totalDay == 0) {
                    int abs = nowMinute - currentMinute;
                    if (currentHour - nowHour == 0 && abs < 1 && abs > 0) {
                        return "刚刚";
                    }
                    if (abs < 60 && abs > 0) {
                        return abs + "分钟前";
                    }
                    return getZeroTime(currentHour) + ":" + getZeroTime(currentMinute);
                }
            }

            //不可能有这种情况了,暂时写着容错处理 和 最开始的那种相同
            return getZeroTime(currentMonth + 1) + "月" + getZeroTime(currentDay) + "日" + " " + getZeroTime(currentHour) + ":" + getZeroTime(currentMinute);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getZeroTime(int time) {
        String value = "";
        if (time >= 10) {
            value = time + "";
        } else {
            value = "0" + time;
        }
        return value;
    }


    public static String getTime(String starttime, String endtime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = "";
        try {
//            Date startTime = df.parse("2006-05-26 12:00:00");
//            Date endTime = df.parse("2006-05-27 11:20:00");

            Date startTime = df.parse(starttime);
            Date endTime = df.parse(endtime);


            long diff = endTime.getTime() - startTime.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);

            System.out.println("" + days + "天" + hours + "小时" + minutes + "分");


            if (days != 0) {
                time = "" + days + "天" + hours + "小时" + minutes + "分";
            } else {
                if (hours != 0) {
                    time = hours + "小时" + minutes + "分";
                } else {
                    time = minutes + "分";
                }
            }
            return time;
        } catch (Exception e) {
            return time;
        }
    }


    public static String getTimeForMonth(String starttime, String endtime) {
        SimpleDateFormat dftStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dftEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = "";
        try {
            Date parseStart = dftStart.parse(starttime);
            Date parseEnd = dftEnd.parse(endtime);
            //开始时间
            Calendar startTime = Calendar.getInstance();
            startTime.setTime(parseStart);
            //结束时间
            Calendar endTime = Calendar.getInstance();
            endTime.setTime(parseEnd);

            int startTimeYear = startTime.get(Calendar.YEAR);
            int startTimeMonth = startTime.get(Calendar.MONTH) + 1;
            int startTimeDay = startTime.get(Calendar.DAY_OF_MONTH);
//            int startTimeHour = startTime.get(Calendar.HOUR_OF_DAY);
//            int startTimeMinute = startTime.get(Calendar.MINUTE);

            int endTimeYear = endTime.get(Calendar.YEAR);
            int endTimeMonth = endTime.get(Calendar.MONTH) + 1;
            int endTimeDay = endTime.get(Calendar.DAY_OF_MONTH);
//            int endTimeHour = endTime.get(Calendar.HOUR_OF_DAY);
//            int endTimeMinute = endTime.get(Calendar.MINUTE);

            if (startTimeMonth == endTimeMonth) {
                if (startTimeDay == endTimeDay) {
                    time = "" + startTimeMonth + "月" + startTimeDay + "日";
                } else {
                    time = "" + startTimeMonth + "月" + startTimeDay + "日" + "-" + endTimeDay + "日";
                }
            } else {
                time = "" + startTimeMonth + "月" + startTimeDay + "日" + "-" + endTimeMonth + "月" + endTimeDay + "日";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String getShowApplyTime(String applyTime) {
        Calendar currentDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int currentHour = currentDate.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentDate.get(Calendar.MINUTE);
        String currentTime = "" + currentYear + "-" + currentMonth + "-" + currentDay + " " + currentHour + ":" + currentMinute + ":" + "00";
        long minutesTime = getMinutesTime(applyTime, currentTime) < 0 ? -getMinutesTime(applyTime, currentTime) : getMinutesTime(applyTime, currentTime);

        try {
            Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(applyTime);
            Calendar applyDate = Calendar.getInstance();
            applyDate.setTime(parse);

            int applyYear = applyDate.get(Calendar.YEAR);
            int applyMonth = applyDate.get(Calendar.MONTH) + 1;
            int applyDay = applyDate.get(Calendar.DAY_OF_MONTH);
            int applyHour = applyDate.get(Calendar.HOUR_OF_DAY);
            int applyMinute = applyDate.get(Calendar.MINUTE);

//            Log.e("当前时间=currentTime=", "" + currentYear + "-" + currentMonth + "-" + currentDay + " " + currentHour + ":" + currentMinute);
//            Log.e("当前时间=applytime=", "" + applyYear + "-" + applyMonth + "-" + applyDay + " " + applyHour + ":" + applyMinute);

            if (applyYear > currentYear) {
                return "";
            }
            if (applyMonth < currentMonth) {
                return "" + applyYear + "-" + getZeroTime(applyMonth) + "-" + getZeroTime(applyDay);
            }
            if (applyDay < currentDay) {
                return "" + getZeroTime(applyMonth) + "-" + getZeroTime(applyDay);
            }
            if (applyHour < currentHour) {
                return "" + getZeroTime(applyMonth) + "-" + getZeroTime(applyDay);
            }

            if (minutesTime <= 30) {
                return "刚刚";
            } else {
                return "" + getZeroTime(applyMonth) + "-" + getZeroTime(applyDay);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long getMinutesTime(String starttime, String endtime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = 0;
        try {
//            Date startTime = df.parse("2006-05-26 12:00:00");
//            Date endTime = df.parse("2006-05-27 11:20:00");

            Date startTime = df.parse(starttime);
            Date endTime = df.parse(endtime);

            long diff = endTime.getTime() - startTime.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);

            System.out.println("" + days + "天" + hours + "小时" + minutes + "分");

//            if (days == 0) {
//                time = hours + "小时" + minutes + "分";
//            } else {
//                time = "" + days + "天" + hours + "小时" + minutes + "分";
//            }
            time = minutes;

            return time;
        } catch (Exception e) {
            return time;
        }
    }

    public static String getPMorAMTime(String startTime, String endTime) {
        Calendar mCalendarStart = Calendar.getInstance();
        Calendar mCalendarEnd = Calendar.getInstance();
        try {
            Date dfStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
            Date dfEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);

            mCalendarStart.setTime(dfStart);
            mCalendarEnd.setTime(dfEnd);


            String[] splitStart = startTime.split(" ");
            String[] splitEnd = endTime.split(" ");
            String timeStartResult;
            String timeEndResult;
            if (mCalendarStart.get(Calendar.AM_PM) == 0) {
                timeStartResult = "" + splitStart[0] + " " + "上午";
            } else {
                timeStartResult = "" + splitStart[0] + " " + "下午";
            }

            if (mCalendarEnd.get(Calendar.AM_PM) == 0) {
                timeEndResult = "" + splitEnd[0] + " " + "上午";
            } else {
                timeEndResult = "" + splitEnd[0] + " " + "下午";
            }
            if (TextUtils.isEmpty(timeStartResult) || TextUtils.isEmpty(timeEndResult)) {
                return "";
            }
            return timeStartResult + " -- " + timeEndResult;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 格式化日期
     *
     * @param sDate 2018-02-08
     */
    public static String getFormatDate(String sDate) {
        if (sDate.equals(getDate())) {
            return "今天";
        }
        String formatDate = "";
        String formatMonth = "";
        String formatDay = "";
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat dft2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dft2.parse(sDate);
            instance.setTime(date);
            int day = instance.get(Calendar.DATE);
            int month = instance.get(Calendar.MONTH) + 1;
            formatMonth = month < 10 ? "0" + month : "" + month;
            formatDay = day < 10 ? "0" + day : "" + day;
            formatDate = formatMonth + "月" + formatDay + "日";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatDate;
    }

    /**
     * 格式化日期
     *
     * @param sDate 2018-02-08
     */
    public static String getFormatDateNoChinese(String sDate) {
        String formatDate = "";
        String formatMonth = "";
        String formatDay = "";
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat dft2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dft2.parse(sDate);
            instance.setTime(date);
            int day = instance.get(Calendar.DATE);
            int month = instance.get(Calendar.MONTH) + 1;
            formatMonth = month < 10 ? "0" + month : "" + month;
            formatDay = day < 10 ? "0" + day : "" + day;
            formatDate = formatMonth + "月" + formatDay + "日";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatDate;
    }

    /**
     * 困难申请的时间规则
     * 一分钟以内:刚刚
     * 一分钟以外:时间  06:20
     * 昨天: 昨天
     * 昨天之前:11-12
     * 去年: 2017-11-11
     *
     * @param time
     * @return
     */
    public static String getWeixinShowTimeForDifficultApply(String time) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = dft.parse(time);

            Calendar currentTime = Calendar.getInstance();
            currentTime.setTime(parse);
            Calendar nowTime = Calendar.getInstance();

            int nowYear = nowTime.get(Calendar.YEAR);
            int nowMonth = nowTime.get(Calendar.MONTH);
            int nowDay = nowTime.get(Calendar.DAY_OF_MONTH);
            int nowHour = nowTime.get(Calendar.HOUR_OF_DAY);
            int nowMinute = nowTime.get(Calendar.MINUTE);

            int currentYear = currentTime.get(Calendar.YEAR);
            int currentMonth = currentTime.get(Calendar.MONTH);
            int currentDay = currentTime.get(Calendar.DAY_OF_MONTH);
            int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
            int currentMinute = currentTime.get(Calendar.MINUTE);

            if (nowYear != currentYear) {
                //不是同一年  6月30日 20：10
                return currentYear + "-" + getZeroTime(currentMonth + 1) + "-" + getZeroTime(currentDay) /*+ " " + getZeroTime(currentHour) + ":" +
                getZeroTime(currentMinute)*/;
            }

            if (nowMonth != currentMonth) {
                //不是同一月  6月30日 20：10
                return getZeroTime(currentMonth + 1) + "-" + getZeroTime(currentDay);
            }

            int totalDay = Math.abs(nowDay - currentDay);
            if (totalDay == 1) {
                return "昨天";
            }

            if (totalDay == 0) {
                if (Math.abs(currentHour - nowHour) < 1) {
                    if (Math.abs(currentMinute - nowMinute) <= 1) {
                        return "刚刚";
                    }
                    return getZeroTime(currentHour) + ":" + getZeroTime(currentMinute);
                }
                return getZeroTime(currentHour) + ":" + getZeroTime(currentMinute);
            }

            //不可能有这种情况了,暂时写着容错处理 和 最开始的那种相同
            return getZeroTime(currentMonth + 1) + "-" + getZeroTime(currentDay);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取 时间规则  上午 05:00
     *
     * @return
     */
    public static String getMorningAndAfternoon() {
        String time = "";
        Calendar currentTime = Calendar.getInstance();

        int nowYear = currentTime.get(Calendar.YEAR);
        int nowMonth = currentTime.get(Calendar.MONTH);
        int nowDay = currentTime.get(Calendar.DAY_OF_MONTH);
        int nowHour = currentTime.get(Calendar.HOUR_OF_DAY);
        int nowMinute = currentTime.get(Calendar.MINUTE);
        if (currentTime.get(Calendar.AM_PM) == 0) {
            time = "上午" + nowHour + ":" + getZeroTime(nowMinute);
        } else {
            time = "下午" + nowHour + ":" + getZeroTime(nowMinute);
        }
        return time;
    }

    public static long date2TimeStamp(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(date).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getIsEmptyTime(String birthday) {
        String time = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = sdf.parse(birthday);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            int nowYear = calendar.get(Calendar.YEAR);
            int nowMonth = calendar.get(Calendar.MONTH) + 1;
            int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
            if (nowYear <= 2100 && nowYear >= 1900) {
                if (nowDay != 0 && nowMonth != 0) {
                    time = "" + getZeroTime(nowYear) + "-" + getZeroTime(nowMonth) + "-" + getZeroTime(nowDay);
                } else {
                    time = "";
                }
            } else {
                time = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return time;
    }

    public static String getHourMinuteTime(String time) {
        String result = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            result = getZeroTime(hour) + ":" + getZeroTime(minute);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String handleTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(time);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getTodayDay() {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(time);
    }

    public static String getToday() {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(time);
    }

    public static int getDateInt(String date, int index) {
        if (TextUtils.isEmpty(date)) {
            return 0;
        }
        String[] split = date.split("-");
        if (split.length == 3) {
            return Integer.parseInt(split[index]);
        }
        return 0;
    }
}
