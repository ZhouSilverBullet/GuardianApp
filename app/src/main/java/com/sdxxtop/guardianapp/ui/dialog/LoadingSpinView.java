package com.sdxxtop.guardianapp.ui.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.luck.picture.lib.dialog.PictureIndeterminate;
import com.sdxxtop.guardianapp.R;

import androidx.appcompat.widget.AppCompatImageView;

public class LoadingSpinView extends AppCompatImageView implements PictureIndeterminate {
    private float mRotateDegrees;
    private int mFrameTime;
    private boolean mNeedToUpdateView;
    private Runnable mUpdateViewRunnable;

    public LoadingSpinView(Context context) {
        super(context);
        this.init();
    }

    public LoadingSpinView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init() {
        this.setImageResource(R.drawable.loading);
        this.mFrameTime = 83;
        this.mUpdateViewRunnable = new Runnable() {
            public void run() {
                mRotateDegrees = mRotateDegrees + 30.0F;
                mRotateDegrees = mRotateDegrees < 360.0F ? mRotateDegrees : mRotateDegrees - 360.0F;
                invalidate();
                if (mNeedToUpdateView) {
                    postDelayed(this, (long) mFrameTime);
                }

            }
        };
    }

    public void setAnimationSpeed(float scale) {
        this.mFrameTime = (int) (83.0F / scale);
    }

    protected void onDraw(Canvas canvas) {
        canvas.rotate(this.mRotateDegrees, (float) (this.getWidth() / 2), (float) (this.getHeight() / 2));
        super.onDraw(canvas);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mNeedToUpdateView = true;
        this.post(this.mUpdateViewRunnable);
    }

    protected void onDetachedFromWindow() {
        this.mNeedToUpdateView = false;
        super.onDetachedFromWindow();
    }
}
