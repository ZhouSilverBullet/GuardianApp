package com.xuxin.guardianapp.ui.fragment;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xuxin.guardianapp.R;
import com.xuxin.guardianapp.base.BaseFragment;
import com.xuxin.guardianapp.ui.widget.TitleView;

import butterknife.BindView;

public class MineFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TitleView mTitleView;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        super.initView();
        statusBar(false);
        topViewPadding(mTitleView);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            statusBar(false);
        }
    }
}
