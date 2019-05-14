package com.sdxxtop.guardianapp.ui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :  lwb
 * Date: 2019/5/10
 * Desc:
 */
public class TabTextView extends LinearLayout {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_line)
    public TextView tvLine;

    public TabTextView(Context context) {
        this(context, null);
    }

    public TabTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.view_detail_tab_text, this, true);
        ButterKnife.bind(this);
//        inflate.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (num==-1){
//                    return;
//                }
//                mListener.onTabClick(num);
//            }
//        });
    }

    public void setValue(String title, String description) {
        tvTitle.setText(TextUtils.isEmpty(title) ? "" : title);
        tvDescription.setText(TextUtils.isEmpty(description) ? "" : description);
    }
}
