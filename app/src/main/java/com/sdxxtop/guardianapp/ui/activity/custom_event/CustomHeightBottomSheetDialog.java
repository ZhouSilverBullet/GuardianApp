package com.sdxxtop.guardianapp.ui.activity.custom_event;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.EventStreamBean;
import com.sdxxtop.guardianapp.ui.activity.custom_event.adapter.CustomEventAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author :  lwb
 * Date: 2019/12/17
 * Desc:
 */
public class CustomHeightBottomSheetDialog extends BottomSheetDialog {

    private int mPeekHeight;
    private int mMaxHeight;
    private boolean mCreated;
    private Window mWindow;
    private BottomSheetBehavior mBottomSheetBehavior;
    private View view;

    private SmartRefreshLayout smartRefresh;
    private RecyclerView recyclerView;
    public CustomEventAdapter adapter;


    public CustomHeightBottomSheetDialog(@NonNull Context context, int Res) {
        super(context);
        init(0, 0);
    }

    public CustomHeightBottomSheetDialog(@NonNull Context context, int theme, int peekHeight, int maxHeight) {
        super(context, theme);
        init(peekHeight, maxHeight);
    }

    public CustomHeightBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener, int peekHeight, int maxHeight) {
        super(context, cancelable, cancelListener);
        init(peekHeight, maxHeight);
    }

    private void init(int peekHeight, int maxHeight) {
        mWindow = getWindow();
        mPeekHeight = peekHeight;
        mMaxHeight = maxHeight;

        view = LayoutInflater.from(getContext()).inflate(R.layout.custom_event_list, null, false);
        findView();
    }

    private void findView() {
        smartRefresh = view.findViewById(R.id.smart_refresh);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CustomEventAdapter(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCreated = true;
        setContentView(view);

        //设置最高高度  展示9条
        mWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(9 * 44));
        mWindow.setGravity(Gravity.BOTTOM);

        setPeekHeight();
        setMaxHeight();
        setBottomSheetCallback();
    }

    public void setRefreshListener(OnRefreshLoadMoreListener listener) {
        if (listener == null || smartRefresh == null) {
            return;
        }
        smartRefresh.setOnRefreshLoadMoreListener(listener);
    }

    public void setData(List<EventStreamBean.SerringsBean> data, boolean isRefresh) {
        if (adapter == null) {
            return;
        }
        if (isRefresh) {
            adapter.replaceData(data);
        } else {
            adapter.addData(data);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void setPeekHeight(int peekHeight) {
        mPeekHeight = peekHeight;

        if (mCreated) {
            setPeekHeight();
        }
    }

    public void setMaxHeight(int height) {
        mMaxHeight = height;

        if (mCreated) {
            setMaxHeight();
        }
    }

    public void setBatterSwipeDismiss(boolean enabled) {
        if (enabled) {

        }
    }

    private void setPeekHeight() {
        if (mPeekHeight <= 0) {
            return;
        }

        if (getBottomSheetBehavior() != null) {
            mBottomSheetBehavior.setPeekHeight(mPeekHeight);
            //已在onCreate中初始化 可不用调用
//            mWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(9*44));
//            mWindow.setGravity(Gravity.BOTTOM);
        }
    }

    private void setMaxHeight() {
        if (mMaxHeight <= 0) {
            return;
        }

        mWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, mMaxHeight);
        mWindow.setGravity(Gravity.BOTTOM);
    }

    private BottomSheetBehavior getBottomSheetBehavior() {
        if (mBottomSheetBehavior != null) {
            return mBottomSheetBehavior;
        }

        View view = mWindow.findViewById(R.id.design_bottom_sheet);
        // setContentView() 没有调用
        if (view == null) {
            return null;
        }
        mBottomSheetBehavior = BottomSheetBehavior.from(view);
        return mBottomSheetBehavior;
    }

    private void setBottomSheetCallback() {
        if (getBottomSheetBehavior() != null) {
            mBottomSheetBehavior.setBottomSheetCallback(mBottomSheetCallback);
        }
    }

    private final BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback
            = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet,
                                   @BottomSheetBehavior.State int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
                BottomSheetBehavior.from(bottomSheet).setState(
                        BottomSheetBehavior.STATE_COLLAPSED);
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    private int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getContext().getResources().getDisplayMetrics());
    }
}
