package com.sdxxtop.guardianapp.ui.activity.kaoqin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.sdxxtop.guardianapp.R;


/**
 * Created by Administrator on 2018/5/26.
 */

public class DashLineView extends View {

    private Paint mPaint;
    private Path mPath;

    public DashLineView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.green_lv));
        // 需要加上这句，否则画不出东西
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dp2px(getContext(), 3));
        mPaint.setPathEffect(new DashPathEffect(new float[]{15, 5}, 0));

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        int centerY = getHeight() / 2;
//        mPath.reset();
//        mPath.moveTo(0, centerY);
//        mPath.lineTo(getWidth(), centerY);
//        canvas.drawPath(mPath, mPaint);

        int centerX = getWidth() / 2;
        mPath.reset();
        mPath.moveTo(0, 0);
        mPath.lineTo(0, getHeight());
        canvas.drawPath(mPath, mPaint);
    }

    public static int dp2px(Context ctx, int dp) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}