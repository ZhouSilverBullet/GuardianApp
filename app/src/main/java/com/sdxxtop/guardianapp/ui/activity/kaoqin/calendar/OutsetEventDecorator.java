package com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import com.sdxxtop.guardianapp.utils.DateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Administrator on 2018/5/14.
 * 设置小红点的Decorator
 */

public class OutsetEventDecorator implements DayViewDecorator {
    private int color;
    private HashSet<CalendarDay> dates;

    public OutsetEventDecorator(int color) {
        this.color = color;
        this.dates = new HashSet<>();
    }

    public void setDates(List<String> abnormal) {
        //后台给予的日期需要解析一下
        Collection<CalendarDay> dates = new ArrayList<>();
        for (int i = 0; i < abnormal.size(); i++) {
            String s = abnormal.get(i);
            dates.add(new CalendarDay(new Date(DateUtil.convertTimeToLong(s, "yyyy-MM-dd"))));
        }
        if (this.dates == null) {
            this.dates = new HashSet<>(dates);
        } else {
            this.dates.addAll(dates);
        }
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(8, color) {
            @Override
            public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top, int baseline, int bottom, CharSequence charSequence,
                                       int start, int end, int lineNum) {
                int oldColor = paint.getColor();
                if (color != 0) {
                    paint.setColor(color);
                }
                canvas.drawCircle((left + right) / 2, bottom + baseline, 5, paint);
                paint.setColor(oldColor);
            }
        });
    }
}
