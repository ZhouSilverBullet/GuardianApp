package com.xuxin.guardianapp.ui.fragment;

import com.xuxin.guardianapp.R;
import com.xuxin.guardianapp.base.BaseFragment;
import com.xuxin.guardianapp.ui.widget.TitleView;

import butterknife.BindView;

public class LearningFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TitleView mTitleView;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_learning;
    }

    @Override
    protected void initView() {
        super.initView();
        statusBar(true);
        topViewPadding(mTitleView);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            statusBar(true);
        }
    }
}
