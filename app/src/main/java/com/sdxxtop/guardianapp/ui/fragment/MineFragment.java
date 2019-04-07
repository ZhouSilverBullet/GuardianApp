package com.sdxxtop.guardianapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.ui.activity.LoginActivity;
import com.sdxxtop.guardianapp.ui.dialog.IosAlertDialog;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TitleView mTitleView;
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
    protected void initEvent() {
        super.initEvent();
        mTitleView.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog();
            }
        });
    }

    private void logoutDialog() {
        new IosAlertDialog(getActivity()).builder()
                .setHeightMsg("确定退出吗?")
                .setPositiveButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logout();
                    }
                })
                .setNegativeButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            statusBar(false);
        }
    }

    public void logout() {
        Intent intent = new Intent(getContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.tatv_message)
    public void onViewClicked() {
    }
}
