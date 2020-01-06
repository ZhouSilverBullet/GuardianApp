package com.sdxxtop.guardianapp.ui.activity.kaoqin;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/12/31
 * Desc:
 */
public class MyAssessLayout extends LinearLayout {

    private ImageView ivIcon;
    private TextView tvText;
    private String titleTx;
    private Drawable iconRes;

    public MyAssessLayout(Context context) {
        this(context, null);
    }

    public MyAssessLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyAssessLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.cv_my_assess, this,true);

        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.MyAssessLayout);
        titleTx = ta.getString(R.styleable.MyAssessLayout_title_tx);
        iconRes = ta.getDrawable(R.styleable.MyAssessLayout_icon_res);
        ta.recycle();

        initView();
    }

    private void initView() {
        ivIcon = findViewById(R.id.iv_icon);
        tvText = findViewById(R.id.tv_text);

        if (!TextUtils.isEmpty(titleTx)){
            tvText.setText(titleTx);
        }

        if (iconRes != null) {
            ivIcon.setImageDrawable(iconRes);
        }
    }
}
