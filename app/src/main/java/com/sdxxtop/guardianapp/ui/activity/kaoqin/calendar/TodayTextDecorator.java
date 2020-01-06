package com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.sdxxtop.guardianapp.R;

/**
 * Created by Administrator on 2018/5/14.
 */

public class TodayTextDecorator implements DayViewDecorator {
    private final CalendarDay today;
    private final Drawable selectBackgroundDrawable;

    public TodayTextDecorator(Context context) {
        today = CalendarDay.today();
        selectBackgroundDrawable = context.getResources().getDrawable(R.drawable.today_select_circle_background);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return today.equals(day);
    }


    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(selectBackgroundDrawable);
    }
}
