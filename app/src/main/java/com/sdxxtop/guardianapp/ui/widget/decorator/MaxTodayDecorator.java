package com.sdxxtop.guardianapp.ui.widget.decorator;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.sdxxtop.guardianapp.R;

import java.util.Calendar;

/**
 * Created by Administrator on 2018/5/14.
 */

public class MaxTodayDecorator implements DayViewDecorator {
    private final CalendarDay today;
    private final Drawable selectBackgroundDrawable;

    public MaxTodayDecorator(Context context) {
        today = CalendarDay.today();
        selectBackgroundDrawable = context.getResources().getDrawable(R.drawable.max_today_select_circle_background);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return compareToday2(day);
    }


    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(selectBackgroundDrawable);
        view.setDaysDisabled(true);
    }

    /**
     * 后面日期不进行选择
     *
     * @param date
     * @return
     */
    private boolean compareToday2(CalendarDay date) {
        int year = Calendar.getInstance().get(Calendar.YEAR); //年
        int month = Calendar.getInstance().get(Calendar.MONTH); //月
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH); //日
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
}
