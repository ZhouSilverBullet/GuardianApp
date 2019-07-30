package com.sdxxtop.guardianapp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sdxxtop.guardianapp.R;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author :  lwb
 * Date: 2019/7/30
 * Desc:
 */
public class EvaluateSelectView extends LinearLayout {
    @BindView(R.id.iv_manyi)
    ImageView ivManyi;
    @BindView(R.id.ll_manyi)
    LinearLayout llManyi;
    @BindView(R.id.iv_yiban)
    ImageView ivYiban;
    @BindView(R.id.ll_yiban)
    LinearLayout llYiban;
    @BindView(R.id.iv_bumanyi)
    ImageView ivBumanyi;
    @BindView(R.id.ll_bumanyi)
    LinearLayout llBumanyi;

    private int currentSelectItem = 1;

    public EvaluateSelectView(Context context) {
        this(context, null);
    }

    public EvaluateSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EvaluateSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.evaluate_select_view, this, true);
        ButterKnife.bind(this);
        ivManyi.setBackgroundResource(R.drawable.shape_red_bg);
        ivYiban.setBackgroundResource(R.drawable.shape_normal_circle_bg);
        ivBumanyi.setBackgroundResource(R.drawable.shape_normal_circle_bg);
    }

    @OnClick({R.id.ll_manyi, R.id.ll_yiban, R.id.ll_bumanyi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_manyi:  // 满意
                currentSelectItem = 1;
                ivManyi.setBackgroundResource(R.drawable.shape_red_bg);
                ivYiban.setBackgroundResource(R.drawable.shape_normal_circle_bg);
                ivBumanyi.setBackgroundResource(R.drawable.shape_normal_circle_bg);
                break;
            case R.id.ll_yiban:  // 一般
                currentSelectItem = 2;
                ivManyi.setBackgroundResource(R.drawable.shape_normal_circle_bg);
                ivYiban.setBackgroundResource(R.drawable.shape_red_bg);
                ivBumanyi.setBackgroundResource(R.drawable.shape_normal_circle_bg);
                break;
            case R.id.ll_bumanyi:  // 不满意
                currentSelectItem = 3;
                ivManyi.setBackgroundResource(R.drawable.shape_normal_circle_bg);
                ivYiban.setBackgroundResource(R.drawable.shape_normal_circle_bg);
                ivBumanyi.setBackgroundResource(R.drawable.shape_red_bg);
                break;
        }
    }
    public int getCurrentSelectItem(){
        return currentSelectItem;
    }
}
