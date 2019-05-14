package com.sdxxtop.guardianapp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.TabTextBean;

import java.util.List;

import io.reactivex.annotations.Nullable;


/**
 * @author :  lwb
 * Date: 2019/5/14
 * Desc:
 */
public class CustomEventLayout extends LinearLayout {

    private LinearLayout ll_layout;

    public CustomEventLayout(Context context) {
        this(context, null);
    }

    public CustomEventLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomEventLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_custom_event_layout, this, true);
        ll_layout = findViewById(R.id.ll_layout);
    }

    public void addLayout(List<TabTextBean> data) {
        LayoutParams layoutParams1 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, data.size());
        ll_layout.setLayoutParams(layoutParams1);

        ll_layout.removeAllViews();
        for (int i = 0; i < data.size(); i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            TabTextBean tabTextBean = data.get(i);
            TabTextView tabTextView = new TabTextView(getContext());
            tabTextView.setTag(tabTextBean.getId());
            tabTextView.setLayoutParams(layoutParams);
            tabTextView.setValue(tabTextBean.getTitle(), tabTextBean.getDesc());
            tabTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onTabClick((Integer) tabTextView.getTag());
                }
            });
            if (i == data.size() - 1) {
                tabTextView.tvLine.setVisibility(View.GONE);
            }
            ll_layout.addView(tabTextView);
        }
    }

    private OnTabClickListener mListener;

    public void setOnTabClickListener(OnTabClickListener listener) {
        this.mListener = listener;
    }

    public interface OnTabClickListener {
        void onTabClick(int num);
    }
}
