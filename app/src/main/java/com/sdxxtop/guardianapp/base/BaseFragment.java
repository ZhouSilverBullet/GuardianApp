package com.sdxxtop.guardianapp.base;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdxxtop.guardianapp.utils.DialogUtil;
import com.sdxxtop.guardianapp.utils.StatusBarUtil;
import com.sdxxtop.guardianapp.utils.SystemUtil;
import com.sdxxtop.guardianapp.app.App;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseFragment extends SupportFragment {

    protected View mRootView;
    private Unbinder mUnbinder;
    private DialogUtil mDialogUtil;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getFragmentLayout(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        initView();
        initEvent();
        initData();
        return mRootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }

        if (mDialogUtil != null) {
            mDialogUtil = null;
        }
    }

    public void showLoadingDialog() {
        if (mDialogUtil == null) {
            mDialogUtil = new DialogUtil();
        }
        mDialogUtil.showLoadingDialog(getActivity());
    }

    public void hideLoadingDialog() {
        if (mDialogUtil != null) {
            mDialogUtil.hideLoadingDialog();
        }
    }

    protected void initView() {
    }

    protected void initEvent() {
    }

    protected void initData() {
    }

    protected abstract int getFragmentLayout();

    public void topViewPadding(View view) {
        if (isVersionMoreKitkat()) {
            view.setPadding(0, SystemUtil.getStatusHeight(App.getContext()), 0, 0);
        }
    }

    public boolean isVersionMoreKitkat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return true;
        } else {
            return false;
        }
    }

    public void statusBar(boolean isDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Android6.0以上系统
            StatusBarUtil.setDarkStatusIcon(getActivity().getWindow(), isDark);
        }
    }
}
