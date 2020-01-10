package com.sdxxtop.guardianapp.base;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdxxtop.guardianapp.app.App;
import com.sdxxtop.guardianapp.utils.DialogUtil;
import com.sdxxtop.guardianapp.utils.StatusBarUtil;
import com.sdxxtop.guardianapp.utils.SystemUtil;
import com.sdxxtop.guardianapp.utils.UIUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

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

    public void closeLoadingDialog() {
        if (mDialogUtil != null) {
            //如果用hide，会卡界面，fragment里面用dialog就会出现这个问题
            mDialogUtil.closeLoadingDialog(getActivity());
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

    public void showToast(String msg){
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        UIUtils.showToast(msg);
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

    public void setIsFirstLoading(boolean isFirstLoading){

    }
}
