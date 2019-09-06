package com.sdxxtop.guardianapp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author :  lwb
 * Date: 2019/9/5
 * Desc:
 */
public class UnScrolGridView extends GridView {

    public UnScrolGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnScrolGridView(Context context) {
        super(context);
    }

    public UnScrolGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}