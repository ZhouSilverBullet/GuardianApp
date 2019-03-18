package com.sdxxtop.guardianapp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;

import androidx.annotation.Nullable;


/**
 * Created by Administrator on 2018/5/15.
 */

public class TextAndTextView extends LinearLayout {

    private String textRightHintValue;
    private TextView textNameText;
    private TextView textRightText;
    private TextView textRightImage;
    private View textLine;
    private boolean lineIsShow;
    private boolean imgIsShow;
    private String textViewValue;
    private String textRightValue;

    public TextAndTextView(Context context) {
        this(context, null);
    }

    public TextAndTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextAndTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextAndTextView, defStyleAttr, 0);
        lineIsShow = a.getBoolean(R.styleable.TextAndTextView_tatv_line_is_show, true);
        imgIsShow = a.getBoolean(R.styleable.TextAndTextView_tatv_text_img_is_show, true);
        textViewValue = a.getString(R.styleable.TextAndTextView_tatv_text_view);
        textRightValue = a.getString(R.styleable.TextAndTextView_tatv_text_right_value);
        textRightHintValue = a.getString(R.styleable.TextAndTextView_tatv_text_right_hint_value);
        a.recycle();
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_text_and_text, this, true);
        textNameText = (TextView) findViewById(R.id.text_and_text_name);
        textRightText = (TextView) findViewById(R.id.text_and_text_right);
        textRightImage = (TextView) findViewById(R.id.text_and_text_right_image);
        textLine = findViewById(R.id.text_and_text_line);

        if (!lineIsShow) {
            textLine.setVisibility(GONE);
        }

        if (!imgIsShow) {
            textRightImage.setVisibility(GONE);
        }

        textNameText.setText(textViewValue);

        textRightText.setTextColor(getResources().getColor(R.color.color_303030));
        textRightText.setText(textRightValue);
        textRightText.setHintTextColor(getResources().getColor(R.color.color_999999));
        textRightText.setHint(textRightHintValue);
    }

    public TextView getTextRightImage() {
        return textRightImage;
    }

    public TextView getTextRightText() {
        return textRightText;
    }

    public void setShowLine(boolean isShow) {
        textLine.setVisibility(isShow ? VISIBLE : GONE);
    }
}
