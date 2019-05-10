package com.sdxxtop.guardianapp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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
public class CustomAreaSelectView extends LinearLayout {

    @BindView(R.id.tv_area)
    public TextView tvArea;
    @BindView(R.id.ll_area_layout)
    public LinearLayout llAreaLayout;

    public CustomAreaSelectView(Context context) {
        this(context, null);
    }

    public CustomAreaSelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_custom_areaselect, this, true);
        ButterKnife.bind(this);
    }
}
