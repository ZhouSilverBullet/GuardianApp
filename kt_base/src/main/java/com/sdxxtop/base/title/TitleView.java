package com.sdxxtop.base.title;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdxxtop.base.R;
import com.sdxxtop.base.utils.UIUtils;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;

public class TitleView extends RelativeLayout {

    private ColorStateList rightTextDrawableColor;
    private Drawable rightImgSrc;
    private boolean layoutIconIsGray;
    private int rightTextColor;
    private String rightTextValue;
    private int bgColor;
    private int titleColor;
    private TextView tvTitle;
    private String titleValue;
    private TextView tvRight;
    private LinearLayout linearBack;
    private boolean layoutIsShow;
    private ImageButton ivRight;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TitleView, defStyleAttr, 0);
        titleValue = a.getString(R.styleable.TitleView_titleValue);
        titleColor = a.getColor(R.styleable.TitleView_titleColor, getResources().getColor(R.color.white));
        bgColor = a.getColor(R.styleable.TitleView_bgColor, getResources().getColor(R.color.colorPrimary));
        rightTextValue = a.getString(R.styleable.TitleView_rightTextValue);
        rightTextColor = a.getColor(R.styleable.TitleView_rightTextColor, getResources().getColor(R.color.white));
        rightTextDrawableColor = a.getColorStateList(R.styleable.TitleView_rightTextColor);
        rightImgSrc = a.getDrawable(R.styleable.TitleView_rightImgSrc);
        layoutIsShow = a.getBoolean(R.styleable.TitleView_leftLayoutIsShow, false);
        layoutIconIsGray = a.getBoolean(R.styleable.TitleView_leftLayoutIconIsGray, false);
        a.recycle();
        init();
    }

    private void init() {
        setClipToPadding(true);
        setFitsSystemWindows(true);
        setBackgroundColor(bgColor);

        LayoutInflater.from(getContext()).inflate(R.layout.view_title, this, true);
        tvTitle = findViewById(R.id.tview_title);
        tvTitle.setText(titleValue);
        tvTitle.setTextColor(titleColor);
        tvRight = findViewById(R.id.tview_right);
        tvRight.setText(rightTextValue);
        if (rightTextDrawableColor != null) {
            tvRight.setTextColor(rightTextDrawableColor);
        } else {
            tvRight.setTextColor(rightTextColor);
        }

        if (layoutIsShow || layoutIconIsGray) {

            if (layoutIconIsGray) {
                ImageView view = findViewById(R.id.iv_back);
//                view.setPadding(UIUtils.dip2px(10), UIUtils.dip2px(4), UIUtils.dip2px(4), UIUtils.dip2px(0));
                view.setImageResource(R.drawable.black_back);
            }
            linearBack = findViewById(R.id.ll_back);
            linearBack.setVisibility(VISIBLE);
            linearBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIUtils.hideInput(v);
                    if (getContext() instanceof Activity) {
                        ((Activity) getContext()).finish();
                    }
                }
            });
        }

        ivRight = findViewById(R.id.iv_right);
        if (rightImgSrc != null) {
            ivRight.setVisibility(VISIBLE);
            ivRight.setImageDrawable(rightImgSrc);
        }


    }

    public TextView getTvRight() {
        return tvRight;
    }

    public ImageButton getIvRight() {
        return ivRight;
    }

    public void setTitleValue(String titleValue) {
        tvTitle.setText(titleValue);
    }

    public void setTitleColor(@ColorRes int titleColor) {
        tvTitle.setTextColor(getResources().getColor(titleColor));
    }

    public void setBgColor(@ColorRes int bgColor) {
        setBackgroundColor(getResources().getColor(bgColor));
    }

    /**
     * 需要的时候，重新把返回键的监听改一下
     *
     * @param listener
     */
    public void resetBackListener(OnClickListener listener) {
        linearBack.setOnClickListener(listener);
    }
}
