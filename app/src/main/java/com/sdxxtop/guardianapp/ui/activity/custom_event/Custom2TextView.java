package com.sdxxtop.guardianapp.ui.activity.custom_event;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/12/18
 * Desc:
 */
public class Custom2TextView extends LinearLayout {

    private int leftColor;
    private int centerColor;
    private int rightColor;
    private float leftTxSize;
    private float centerTxSize;
    private float rightTxSize;
    private boolean rightTextIvShow;
    private String leftTxValue;
    private String centerTxValue;
    private String rightTxValue;

    private TextView tvLeft;
    private TextView tvCenter;
    private TextView tvRight;
    private ImageView ivRightTxLeft;

    public Custom2TextView(Context context) {
        this(context, null);
    }

    public Custom2TextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Custom2TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Custom2TextView);

        leftColor = ta.getColor(R.styleable.Custom2TextView_left_text_color, Color.parseColor("#292929"));
        centerColor = ta.getColor(R.styleable.Custom2TextView_center_text_color, Color.parseColor("#292929"));
        rightColor = ta.getColor(R.styleable.Custom2TextView_right_text_color, Color.parseColor("#292929"));
        leftTxSize = ta.getDimension(R.styleable.Custom2TextView_left_text_size, 14f);
        centerTxSize = ta.getDimension(R.styleable.Custom2TextView_center_text_size, 14f);
        rightTxSize = ta.getDimension(R.styleable.Custom2TextView_right_text_size, 14f);
        leftTxValue = ta.getString(R.styleable.Custom2TextView_left_text_value);
        centerTxValue = ta.getString(R.styleable.Custom2TextView_center_text_value);
        rightTxValue = ta.getString(R.styleable.Custom2TextView_right_text_value);
        rightTextIvShow = ta.getBoolean(R.styleable.Custom2TextView_right_text_iv_show, false);

        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_text_2_text, this, true);

        tvLeft = findViewById(R.id.tvLeft);
        tvCenter = findViewById(R.id.tvCenter);
        tvRight = findViewById(R.id.tvRight);
        ivRightTxLeft = findViewById(R.id.ivRightTxLeft);

        tvLeft.setTextColor(leftColor);
        tvCenter.setTextColor(centerColor);
        tvRight.setTextColor(rightColor);

        tvLeft.setText(leftTxValue);
        tvCenter.setText(centerTxValue);
        tvRight.setText(rightTxValue);

        ivRightTxLeft.setVisibility(rightTextIvShow ? View.VISIBLE : View.GONE);
    }

    public TextView getTvLeft() {
        return tvLeft;
    }

    public TextView getTvCenter() {
        return tvCenter;
    }

    public TextView getTvRight() {
        return tvRight;
    }
}
