package com.sdxxtop.guardianapp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * @author :  lwb
 * Date: 2019/10/16
 * Desc:
 */
public class CustomNoSlideViewPager extends ViewPager {
    private boolean isCanScroll = true;

    public CustomNoSlideViewPager(Context context) {
        super(context);
    }

    public CustomNoSlideViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置其是否能滑动换页
     * @param isCanScroll false 不能换页， true 可以滑动换页
     */

    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isCanScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isCanScroll && super.onTouchEvent(ev);

    }
    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }
}
