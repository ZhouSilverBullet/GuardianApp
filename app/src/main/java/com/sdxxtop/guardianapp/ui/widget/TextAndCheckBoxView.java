package com.sdxxtop.guardianapp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/15.
 */

public class TextAndCheckBoxView extends LinearLayout {


    @BindView(R.id.text_and_text_name)
    TextView textAndTextName;
    @BindView(R.id.ll_containor_no)
    LinearLayout llContainorNo;
    @BindView(R.id.ll_containor_yes)
    LinearLayout llContainorYes;
    @BindView(R.id.text_and_text_line)
    View textAndTextLine;
    @BindView(R.id.ck_no)
    CheckBox ckNo;
    @BindView(R.id.ck_yes)
    CheckBox ckYes;

    public TextAndCheckBoxView(Context context) {
        this(context, null);
    }

    public TextAndCheckBoxView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextAndCheckBoxView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextAndCheckBoxView, defStyleAttr, 0);
//        textViewValue = a.getString(R.styleable.TextAndCheckBoxView_tacbv_text_right_value);
//        textViewValue = a.getString(R.styleable.TextAndCheckBoxView_tacbv_line_is_show);

        a.recycle();
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_text_and_checkbox, this, true);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_containor_no, R.id.ll_containor_yes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_containor_no:
                ckYes.setChecked(false);
                ckNo.setChecked(true);
                break;
            case R.id.ll_containor_yes:
                ckYes.setChecked(true);
                ckNo.setChecked(false);
                break;
        }
    }

    public boolean getEnableClick(){
        return ckNo.isChecked();
    }
}
