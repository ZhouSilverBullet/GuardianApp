package com.sdxxtop.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.sdxxtop.base.R;


public class LoadingDialog extends Dialog {
    public Context context;

    public LoadingDialog(Context context) {
        super(context, R.style.loading_dialog);
        this.context = context;
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(false);
        Window window = this.getWindow();
        window.setWindowAnimations(R.style.LoadingDialogWindowStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_loading);
    }
}
