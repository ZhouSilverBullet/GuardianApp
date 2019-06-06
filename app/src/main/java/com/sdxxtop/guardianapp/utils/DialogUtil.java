package com.sdxxtop.guardianapp.utils;

import android.app.Activity;

import com.sdxxtop.guardianapp.ui.dialog.LoadingDialog;

public class DialogUtil {

    private LoadingDialog sLoadingDialog;

    public void showLoadingDialog(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        sLoadingDialog = new LoadingDialog(activity);
        sLoadingDialog.show();
    }

    public void hideLoadingDialog() {
        if (sLoadingDialog != null) {
            sLoadingDialog.hide();
        }
    }

    public void closeLoadingDialog(Activity activity) {
        if (sLoadingDialog != null) {
            sLoadingDialog.dismiss();
        }
    }
}
