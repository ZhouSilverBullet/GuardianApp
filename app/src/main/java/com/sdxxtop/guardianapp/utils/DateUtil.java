package com.sdxxtop.guardianapp.utils;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Administrator on 2018/5/3.
 */

public class DateUtil {

    /**
     * 转换时间日期格式字串为long型
     *
     * @param time 格式为：yyyy-MM-dd HH:mm:ss的时间日期类型
     */
    public static Long convertTimeToLong(String time, String format) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(time);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    public static String getTitleDate(CalendarDay dateDay) {
        String weekOfDate = DateUtil.getWeekOfDate(dateDay.getDate());

        int i = dateDay.getMonth() + 1;
        String title = "";
        int day = dateDay.getDay();
        String stringMonth = "";
        String stringDay = "";
        if (i < 10) {
            stringMonth = "0" + i;
        } else {
            stringMonth = i + "";
        }

        if (day < 10) {
            stringDay = "0" + day;
        } else {
            stringDay = "" + day;
        }

        title = dateDay.getYear() + "-" + stringMonth + "-" + stringDay + "  " + weekOfDate;
        return title;
    }

    public static String getSelectorDate(CalendarDay dateDay) {
        int i = dateDay.getMonth() + 1;
        String title = "";
        int day = dateDay.getDay();
        String stringMonth = "";
        String stringDay = "";
        if (i < 10) {
            stringMonth = "0" + i;
        } else {
            stringMonth = i + "";
        }

        if (day < 10) {
            stringDay = "0" + day;
        } else {
            stringDay = "" + day;
        }

        title = dateDay.getYear() + "-" + stringMonth + "-" + stringDay;
        return title;
    }

    /**
     * 这个配合统计月
     * 这个只返回 2018-04
     *
     * @param dateDay
     * @return
     */
    public static String getSelector0Date(CalendarDay dateDay) {
        int i = dateDay.getMonth() + 1;
        String title = "";
        int day = dateDay.getDay();
        String stringMonth = "";
        String stringDay = "";
        if (i < 10) {
            stringMonth = "0" + i;
        } else {
            stringMonth = i + "";
        }

        if (day < 10) {
            stringDay = "0" + day;
        } else {
            stringDay = "" + day;
        }

        title = dateDay.getYear() + "-" + stringMonth;
        return title;
    }

    public static String getChineseSelectorDate(CalendarDay dateDay) {
        String title = dateDay.getYear() + "年" + (dateDay.getMonth() + 1) + "月" + dateDay.getDay() + "日";
        return title;
    }

    /**
     * 这个值有年和月
     *
     * @param dateDay
     * @return
     */
    public static String getChineseSelectorDate1(CalendarDay dateDay) {
        String title = dateDay.getYear() + "年" + (dateDay.getMonth() + 1) + "月";
        return title;
    }

    /**
     * 月份小于10 带0
     *
     * @param dateDay
     * @return
     */
    public static String getChineseSelector0Date(CalendarDay dateDay) {
        int i = dateDay.getMonth() + 1;
        String title = "";
        int day = dateDay.getDay();
        String stringMonth = "";
        String stringDay = "";
        if (i < 10) {
            stringMonth = "0" + i;
        } else {
            stringMonth = i + "";
        }

        if (day < 10) {
            stringDay = "0" + day;
        } else {
            stringDay = "" + day;
        }

        title = dateDay.getYear() + "年" + stringMonth + "月" + stringDay + "日";
        return title;
    }

    /**
     * 月份小于10 带0
     * student 学生考勤  xx月xx日
     * @param dateDay
     * @return
     */
    public static String getChineseSelector2Date(CalendarDay dateDay) {
        int i = dateDay.getMonth() + 1;
        String title = "";
        int day = dateDay.getDay();
        String stringMonth = "";
        String stringDay = "";
        if (i < 10) {
            stringMonth = "0" + i;
        } else {
            stringMonth = i + "";
        }

        if (day < 10) {
            stringDay = "0" + day;
        } else {
            stringDay = "" + day;
        }

        title = stringMonth + "月" + stringDay + "日";
        return title;
    }

    /**
     * 配合统计 年月 使用的
     *
     * @param dateDay
     * @return
     */
    public static String getChineseSelector1Date(CalendarDay dateDay) {
        int i = dateDay.getMonth() + 1;
        String title = "";
        int day = dateDay.getDay();
        String stringMonth = "";
        String stringDay = "";
        if (i < 10) {
            stringMonth = "0" + i;
        } else {
            stringMonth = i + "";
        }

        if (day < 10) {
            stringDay = "0" + day;
        } else {
            stringDay = "" + day;
        }

        title = dateDay.getYear() + "年" + stringMonth + "月";
        return title;
    }

    /**
     * 漏打卡 月日 使用的
     *
     * @param date
     * @return
     */
    public static String getChineseMonthAndDay(String date) {
        CalendarDay dateDay = new CalendarDay(new Date(DateUtil.convertTimeToLong(date, "yyyy-MM-dd")));
        int i = dateDay.getMonth() + 1;
        String title = "";
        int day = dateDay.getDay();
        String stringMonth = "";
        String stringDay = "";
        if (i < 10) {
            stringMonth = "0" + i;
        } else {
            stringMonth = i + "";
        }

        if (day < 10) {
            stringDay = "0" + day;
        } else {
            stringDay = "" + day;
        }

        title = stringMonth + "月" + stringDay + "日";
        return title;
    }

    //CalendarDay 数据
    public static String getChineseYearAndMonthDate(CalendarDay dateDay) {

        int i = dateDay.getMonth() + 1;
        String title = "";
        int day = dateDay.getDay();
        String stringMonth = "";
        String stringDay = "";
        if (i < 10) {
            stringMonth = "0" + i;
        } else {
            stringMonth = i + "";
        }

        if (day < 10) {
            stringDay = "0" + day;
        } else {
            stringDay = "" + day;
        }

        title = dateDay.getYear() + "年" + stringMonth + "月";
        return title;
//        String title = dateDay.getYear() + "年" + (dateDay.getMonth() + 1) + "月";
//        return title;
    }

    public static String getChineseYearAndMonthDate(Date date) {
        CalendarDay from = CalendarDay.from(date);
        String month = "";
        int i = from.getMonth() + 1;
        if (i < 10) {
            month = "0" + i;
        }
        String title = from.getYear() + "年" + month + "月";
        return title;
    }

    /**
     * 获取系统时间 24小时制
     *
     * @return
     */
    public static String show24Date() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm:ss");
        String date = sDateFormat.format(new Date());
        return date;
    }

    /**
     * 获取系统时间 24小时制
     *
     * @return
     */
    public static String show12Date() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new Date());
        return date;
    }

//    public static DateBean getDateBean(String time) {
//        Date date = null;
//        try {
//            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
//            Calendar now = Calendar.getInstance();
//            now.setTime(date);
//
//            DateBean dateBean = new DateBean();
//            int year = now.get(Calendar.YEAR);
//            int month = now.get(Calendar.MONTH) + 1; // 0-based!
//            int day = now.get(Calendar.DAY_OF_MONTH);
//            dateBean.day = day;
//            dateBean.month = month;
//            dateBean.year = year;
//            return dateBean;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }


    public static String getShowTime(String time) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            Calendar currentDate = Calendar.getInstance();
            currentDate.setTime(date);
            Calendar nowDay = Calendar.getInstance();

            int year = currentDate.get(Calendar.YEAR);
            int month = currentDate.get(Calendar.MONTH) + 1; // 0-based!
            int day = currentDate.get(Calendar.DAY_OF_MONTH);
            int nowYear = nowDay.get(Calendar.YEAR);
            int nowMonth = nowDay.get(Calendar.MONTH) + 1; // 0-based!
            int nowsDay = nowDay.get(Calendar.DAY_OF_MONTH);

            if (year != nowYear) {
                return year + "-" + month + "-" + day;
            }

            if (month != nowMonth) {
                return month + "-" + day;
            }

            if (day != nowsDay) {
                if (nowsDay - day == 1) {
                    return "昨天";
                }

                return month + "-" + day;
            }

            int hour = currentDate.get(Calendar.HOUR_OF_DAY);
            int nowHour = nowDay.get(Calendar.HOUR_OF_DAY);
            int minute = currentDate.get(Calendar.MINUTE);
            int nowMinute = nowDay.get(Calendar.MINUTE);
            if (nowHour == hour && nowMinute - minute <= 1) {
                return "刚刚";
            }

            String hourAnd = time.split(" ")[1];
            String substring = hourAnd.substring(0, hourAnd.lastIndexOf(":"));
            return substring;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }


    //ApplyList 漏打卡的显示
    public static String getApplyListLeaveCardTime(String time) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(time);
            Calendar currentDate = Calendar.getInstance();
            currentDate.setTime(date);
            Calendar nowDay = Calendar.getInstance();

            int year = currentDate.get(Calendar.YEAR);
            int month = currentDate.get(Calendar.MONTH) + 1; // 0-based!
            int day = currentDate.get(Calendar.DAY_OF_MONTH);
            int nowYear = nowDay.get(Calendar.YEAR);

            if (year != nowYear) {
                return year + "年" + month + "月" + day + "日";
            }

            return month + "月" + day + "日";

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 公告
     * <p>
     * 今天发布的：09:45
     * 今年发布的：3-20  09:45
     * 去年及以前的：2018-3-20  09:45
     *
     * @param time
     * @return
     */
    public static String getShowTime2(String time) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            Calendar currentDate = Calendar.getInstance();
            currentDate.setTime(date);
            Calendar nowDay = Calendar.getInstance();

            int year = currentDate.get(Calendar.YEAR);
            int month = currentDate.get(Calendar.MONTH) + 1; // 0-based!
            int day = currentDate.get(Calendar.DAY_OF_MONTH);

            int nowYear = nowDay.get(Calendar.YEAR);
            int nowMonth = nowDay.get(Calendar.MONTH) + 1; // 0-based!
            int nowsDay = nowDay.get(Calendar.DAY_OF_MONTH);

            int hour = currentDate.get(Calendar.HOUR_OF_DAY);
            int minute = currentDate.get(Calendar.MINUTE);

            String stringHour;
            String stringMinute;

            if (hour < 10) {
                stringHour = "0" + hour;
            } else {
                stringHour = hour + "";
            }

            if (minute < 10) {
                stringMinute = "0" + minute;
            } else {
                stringMinute = minute + "";
            }

            if (year != nowYear) {
                return year + "-" + month + "-" + day + " " + stringHour + ":" + stringMinute;
            }

            if (month != nowMonth || day != nowsDay) {
                return month + "-" + day + " " + stringHour + ":" + stringMinute;
            }

            return stringHour + ":" + stringMinute;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    //给申请提交完成 两个时间进行差值对比得出的一个字符串
    //先暂时不考虑年，因为UI图上面没给出具体跨年的显示
    public static String getApplyShowTime(String startTime, String endTime) {
        try {
            String startHourTime = startTime.split(" ")[1];
            startHourTime = startHourTime.substring(0, startHourTime.lastIndexOf(":"));
            String endHourTime = endTime.split(" ")[1];
            endHourTime = endHourTime.substring(0, endHourTime.lastIndexOf(":"));

            Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);

            Calendar startCurrentDate = Calendar.getInstance();
            startCurrentDate.setTime(startDate);

            Calendar endCurrentDate = Calendar.getInstance();
            endCurrentDate.setTime(endDate);


            int year = startCurrentDate.get(Calendar.YEAR);
            int month = startCurrentDate.get(Calendar.MONTH) + 1; // 0-based!
            int day = startCurrentDate.get(Calendar.DAY_OF_MONTH);
            int nowYear = endCurrentDate.get(Calendar.YEAR);
            int nowMonth = endCurrentDate.get(Calendar.MONTH) + 1; // 0-based!
            int nowsDay = endCurrentDate.get(Calendar.DAY_OF_MONTH);
//
//            if (year != nowYear) {
//                return year + "年" + month + "-" + day;
//            }

            if (month != nowMonth) {
                return month + "月" + day + "日(" + startHourTime + ")" + "-" + nowMonth + "月" + nowsDay + "日(" + endHourTime + ")";
            }

            if (day != nowsDay) {

                return month + "月" + day + "日(" + startHourTime + ")" + "-" + nowsDay + "日(" + endHourTime + ")";
            }

            return month + "月" + day + "日(" + startHourTime + "-" + endHourTime + ")";


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }


    public static String getHourTime(String time) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            Calendar currentDate = Calendar.getInstance();
            currentDate.setTime(date);
            int hour = currentDate.get(Calendar.HOUR_OF_DAY);
            int minute = currentDate.get(Calendar.MINUTE);

            if (minute < 10) {
                if (hour < 10) {
                    return "0" + hour + ":0" + minute;
                } else {
                    return hour + ":0" + minute;
                }
            } else {
                if (hour < 10) {
                    return "0" + hour + ":" + minute;
                } else {
                    return hour + ":" + minute;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getDayTime(String time) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(time);
            Calendar currentDate = Calendar.getInstance();
            currentDate.setTime(date);
            int year = currentDate.get(Calendar.YEAR);
            int month = currentDate.get(Calendar.MONTH) + 1; // 0-based!
            int day = currentDate.get(Calendar.DAY_OF_MONTH);

//            if (month < 10 && day < 10) {
//                return year + "-0" + month + "-0" + day;
//            }
//            if (month < 10 && day > 10) {
//                return year + "-0" + month + "-" + day;
//            }
//
//            if (month > 10 && day < 10) {
//                return year + "-" + month + "-0" + day;
//            }
//
//            return year + "-" + month + "-" + day;

            return getYearMonthDay(year, month, day);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getYearMonthDay(int year, int month, int day) {
        if (month < 10 && day < 10) {
            return year + "-0" + month + "-0" + day;
        }
        if (month < 10 && day > 10) {
            return year + "-0" + month + "-" + day;
        }

        if (month > 10 && day < 10) {
            return year + "-" + month + "-0" + day;
        }

        return year + "-" + month + "-" + day;
    }

    //加一天， 调班默认值使用
    public static String getDayOneTime() {
        Date date = null;
        try {

            date = new Date();//取时间
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
            date = calendar.getTime(); //这个时间就是日期往后推一天的结果
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(date);

            return dateString;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    //加一天， 调班默认值使用
    public static List<String> getDayOneTime(String value) {
        List<String> list = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月");
        try {
            Date date = formatter.parse(value);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
            date = calendar.getTime(); //这个时间就是日期往后推一天的结果
            String dateString = formatter.format(date);
            calendar.setTime(date);
            list.add(dateString);
            list.add(calendar.get(Calendar.YEAR) + "");
            list.add((calendar.get(Calendar.MONTH) + 1) + "");
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String[] getYearAndMonth(String dateString) {
        Date date = null;
        SimpleDateFormat dft = new SimpleDateFormat("yyyy年MM月");
        try {
            date = dft.parse(dateString);
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            int year = instance.get(Calendar.YEAR);
            int month = instance.get(Calendar.MONTH);
            return new String[]{year + "", (month + 1) + ""};
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取任意时间的下一个月
     * 描述:<描述函数实现的功能>.
     *
     * @param repeatDate
     * @return
     */
    public static String getPreMonth(String repeatDate) {
        String lastMonth = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy年MM月");
        int year = Integer.parseInt(repeatDate.substring(0, 4));
        String monthsString = repeatDate.substring(5, 7);
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2));
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2));
        }
        cal.set(year, month, Calendar.DATE);
        lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }

    /**
     * 获取任意时间的上一个月
     * 描述:<描述函数实现的功能>.
     *
     * @param repeatDate
     * @return
     */
    public static String getLastMonth(String repeatDate) {
        String lastMonth = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy年MM月");
        int year = Integer.parseInt(repeatDate.substring(0, 4));
        String monthsString = repeatDate.substring(5, 7);
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2));
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2));
        }
        cal.set(year, month - 2, Calendar.DATE);
        lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }

    public static String[] getStartEndYear(CalendarDay currentDate) {
        try {
            int year = currentDate.getYear();
            int month = currentDate.getMonth() + 1;
            int day = currentDate.getDay();
            String yearMonthDay = getYearMonthDay(year, month, day);
            int lastDay = getDateOfMonth(year, month);
            String lastYearMonthDay = getYearMonthDay(year, month, lastDay);
            String[] strings = new String[]{yearMonthDay, lastYearMonthDay};
            return strings;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getDateOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);//Java月份才0开始算
        int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
        return dateOfMonth;
    }

    public static String getReplaceLineTime(String time) {
        try {

            Long aLong = convertTimeToLong(time, "yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String format = formatter.format(new Date(aLong));
            return format;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getReplaceLineTime(long time) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String format = formatter.format(new Date(time));
            return format;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    //未超过24小时：已等待xx小时xx分钟
    // 超过24小时：已等待xxx天

    public static String getSubmissionData(String time) {
        long days = 0, hours = 0, minutes = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date d1 = Calendar.getInstance().getTime();
            Date d2 = df.parse(time);
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            days = diff / (1000 * 60 * 60 * 24);
            hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            if (days >= 1) {
                return "已等待" + days + "天";
            }

            if (hours == 0) {
                return "已等待" + minutes + "分钟";
            }

            return "已等待" + hours + "小时" + minutes + "分钟";

        } catch (Exception e) {
        }
        return "（请假时间共" + days + "天" + hours + "小时" + minutes + "分）";
    }

    //获取 月日 星期
    public static String getApplyData(String time) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(time);
            Calendar currentDate = Calendar.getInstance();
            currentDate.setTime(date);
            Long aLong = convertTimeToLong(time, "yyyy-MM-dd");
            SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
            String format = formatter.format(new Date(aLong));
            String weekOfDate = getWeekOfDate(date);
            return weekOfDate + "\n" + format;
        } catch (Exception e) {
        }
        return "";
    }

    //获取 月日 星期 不换行
    public static String getApplyData0(String time) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(time);
            Calendar currentDate = Calendar.getInstance();
            currentDate.setTime(date);
            Long aLong = convertTimeToLong(time, "yyyy-MM-dd");
            SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
            String format = formatter.format(new Date(aLong));
//            String weekOfDate = getWeekOfDate(date);
            return /*weekOfDate + "\t" +*/ format;
        } catch (Exception e) {
        }
        return "";
    }

    //调班申请时间
    public static String getApplyChangeShiftTime(String startTime, String endTime) {
        try {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);

            Calendar startCurrentDate = Calendar.getInstance();
            startCurrentDate.setTime(startDate);

            Calendar endCurrentDate = Calendar.getInstance();
            endCurrentDate.setTime(endDate);


            int year = startCurrentDate.get(Calendar.YEAR);
            int month = startCurrentDate.get(Calendar.MONTH) + 1; // 0-based!
            int day = startCurrentDate.get(Calendar.DAY_OF_MONTH);
            int nowYear = endCurrentDate.get(Calendar.YEAR);
            int nowMonth = endCurrentDate.get(Calendar.MONTH) + 1; // 0-based!
            int nowsDay = endCurrentDate.get(Calendar.DAY_OF_MONTH);

            if (month != nowMonth) {
                return month + "月" + day + "日" + "-" + nowMonth + "月" + nowsDay + "日";
            }

            if (day != nowsDay) {

                return month + "月" + day + "日" + "-" + nowsDay + "日";
            }

            return month + "月" + day + "日";


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }


    /**
     * 不论是当前时间，还是历史时间  皆是时间点的前天
     * repeatDate  任意时间
     */
    public static String getModify2DaysAgo(String repeatDate) {
        Calendar cal = Calendar.getInstance();
        String daysAgo = "";
        SimpleDateFormat dft = new SimpleDateFormat("yyyy年MM月dd日");
        if (repeatDate == null || "".equals(repeatDate)) {
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 2);

        } else {
            int year = Integer.parseInt(repeatDate.substring(0, 4));
            String monthsString = repeatDate.substring(4, 6);
            int month;
            if ("0".equals(monthsString.substring(0, 1))) {
                month = Integer.parseInt(monthsString.substring(1, 2));
            } else {
                month = Integer.parseInt(monthsString.substring(0, 2));
            }
            String dateString = repeatDate.substring(6, 8);
            int date;
            if ("0".equals(dateString.subSequence(0, 1))) {
                date = Integer.parseInt(dateString.substring(1, 2));
            } else {
                date = Integer.parseInt(dateString.substring(0, 2));
            }
            cal.set(year, month - 1, date - 1);
            System.out.println(dft.format(cal.getTime()));
        }
        daysAgo = dft.format(cal.getTime());
        return daysAgo;
    }

}
