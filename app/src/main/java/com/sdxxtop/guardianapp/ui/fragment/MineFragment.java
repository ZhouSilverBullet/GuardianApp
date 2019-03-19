package com.sdxxtop.guardianapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.ui.activity.LoginActivity;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TitleView mTitleView;
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    private boolean isAdmin;

    public static MineFragment newInstance(boolean isAdmin) {

        Bundle args = new Bundle();
        args.putBoolean("isAdmin", isAdmin);
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        super.initView();
        statusBar(false);
        if (getArguments() != null) {
            isAdmin = getArguments().getBoolean("isAdmin");
        }

        if (isAdmin) {
            ivTop.setImageResource(R.drawable.mine_top);
        } else {
            ivTop.setImageResource(R.drawable.mine_top_2);
        }

        topViewPadding(mTitleView);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            statusBar(false);
        }
    }

    @OnClick(R.id.tv_logout)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
    }
}
