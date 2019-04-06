package com.sdxxtop.guardianapp.ui.widget.decorator;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.sdxxtop.guardianapp.R;

/**
 * Use a custom selector
 */
public class CalendarSelectorDecorator implements DayViewDecorator {

    private Drawable drawable;

    public CalendarSelectorDecorator(Context context) {
        drawable = context.getResources().getDrawable(R.drawable.calendar_selector);
    }

    public CalendarSelectorDecorator(Context context, int newDrawable) {
        drawable = context.getResources().getDrawable(newDrawable);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}
