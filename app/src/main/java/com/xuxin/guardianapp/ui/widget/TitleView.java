package com.xuxin.guardianapp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuxin.guardianapp.R;

import androidx.annotation.Nullable;

public class TitleView extends RelativeLayout {

    private int bgColor;
    private int titleColor;
    private TextView tvTitle;
    private String titleValue;

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
        titleColor = a.getColor(R.styleable.TitleView_titleColor, getResources().getColor(R.color.color_303030));
        bgColor = a.getColor(R.styleable.TitleView_bgColor, getResources().getColor(R.color.white));

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
    }
}
