package com.sdxxtop.guardianapp.ui.widget.imgservice;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.utils.UIUtils;

import androidx.annotation.Nullable;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-22 17:12
 * Version: 1.0
 * Description:
 */
public class ImageServiceView extends RelativeLayout implements ViewTreeObserver.OnGlobalLayoutListener, View.OnTouchListener {

    private static final String TAG = "ImageServiceView";

    private ImageView imgService;
    private boolean isFirst = true;
    private int imgWidth;
    private int mWidthScreen;
    private int mHeightScreen;
    private int lastX;
    private int lastY;
    private int tempX;
    private int tempY;
    private int height = UIUtils.dip2px(80);
    private int imgHeight;

    public ImageServiceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_image_service, this, true);
        imgService = findViewById(R.id.img_service);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        mWidthScreen = dm.widthPixels;
        mHeightScreen = dm.heightPixels;

        imgService.getViewTreeObserver().addOnGlobalLayoutListener(this);

        imgService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OnlineServiceActivity.class);
                intent.putExtra("href", "https://tb.53kf.com/code/client/b722216fa3d928f41a494d544ac54dcb/2?device=android");
                getContext().startActivity(intent);
            }
        });
        //设置屏幕触摸事件
        imgService.setOnTouchListener(this);
    }


    @Override
    public void onGlobalLayout() {
        toGlobal();
    }

    void toGlobal() {
        if (isFirst) {
            isFirst = false;
            imgWidth = imgService.getWidth();
            imgHeight = imgService.getHeight();
            //初始设置一个layoutParams
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = mWidthScreen - imgWidth;
            layoutParams.topMargin = mHeightScreen - height - imgService.getHeight();

            imgService.setLayoutParams(layoutParams);

            Log.e(TAG, "toGlobal: " + imgWidth);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                //将点下的点的坐标保存
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();

                tempX = (int) event.getRawX();
                tempY = (int) event.getRawY();

                break;

            case MotionEvent.ACTION_MOVE:
                //计算出需要移动的距离
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                //将移动距离加上，现在本身距离边框的位置
                int left = view.getLeft() + dx;
                int top = view.getTop() + dy;

                if (top < UIUtils.dip2px(50)) {
                    top = UIUtils.dip2px(50);
                } else if (top > mHeightScreen - height - imgHeight) {
                    top = mHeightScreen - height - imgHeight;
                }

                Log.e(TAG, "left_size" + left);
                Log.e(TAG, "top_size" + top);
//                Log.e("rl_temp_htight", "" + llMainBottom.getTop());
                //获取到layoutParams然后改变属性，在设置回去

                layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                layoutParams.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                layoutParams.leftMargin = left;
                layoutParams.topMargin = top;
                view.setLayoutParams(layoutParams);
                //记录最后一次移动的位置
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                //判断松手时View的横坐标是靠近屏幕哪一侧，将View移动到依靠屏幕
                int endX = (int) event.getRawX();
                int endY = (int) event.getRawY();
                if (endX < mWidthScreen / 2) {
//                    endX = 0;
                    endX = mWidthScreen - imgWidth;
                } else {
                    endX = mWidthScreen - imgWidth;
                }
//                获取到layoutParams然后改变属性，在设置回去
                layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                layoutParams.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                layoutParams.leftMargin = endX;
                view.setLayoutParams(layoutParams);

                Log.e(TAG, "onTouch: ");

                //如果初始落点与松手落点的坐标差值超过6个像素，则拦截该点击事件
                //否则继续传递，将事件交给OnClickListener函数处理
                if (Math.abs(endX - tempX) > 6 && Math.abs(endY - tempY) > 6) {
                    return true;
                }
                break;
            default:
                break;
        }
        //刷新界面
//        relativeLayout.invalidate();
        return false;
    }

}
