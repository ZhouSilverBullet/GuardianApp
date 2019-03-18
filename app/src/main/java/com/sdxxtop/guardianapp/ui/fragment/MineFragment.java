package com.sdxxtop.guardianapp.ui.fragment;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

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
