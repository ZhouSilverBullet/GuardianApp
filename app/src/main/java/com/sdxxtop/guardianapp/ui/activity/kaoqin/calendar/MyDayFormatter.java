package com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar;

import android.text.TextUtils;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import androidx.annotation.NonNull;

/**
 * Created by Administrator on 2018/9/1.
 */

public class MyDayFormatter implements DayFormatter {
    private final DateFormat dateFormat;
    private HashMap<String, String> dayMap;
    private String ruleName;
    private CalendarDay todayCalendar = new CalendarDay(Calendar.getInstance());

    /**
     * Format using a default format
     */
    public MyDayFormatter() {
        this.dateFormat = new SimpleDateFormat("d", Locale.getDefault());
    }

    public void setDayMap(HashMap<String, String> dayMap) {
        this.dayMap = dayMap;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    /**
     * Format using a specific {@linkplain DateFormat}
     *
     * @param format the format to use
     */
    public MyDayFormatter(@NonNull DateFormat format) {
        this.dateFormat = format;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @NonNull
    public String format(@NonNull CalendarDay day) {
        String name = "";
        if (dayMap != null) {
            name = dayMap.get(day.toString());
            if (TextUtils.isEmpty(name)) {
                name = ruleName;
            }
        }
        if (day.equals(todayCalendar)) {
            return "今" + "\n" + name;
        }
        return dateFormat.format(day.getDate()) + "\n" + name;
    }
}
