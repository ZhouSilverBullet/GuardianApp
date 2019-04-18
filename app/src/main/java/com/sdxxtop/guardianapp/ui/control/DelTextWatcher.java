package com.sdxxtop.guardianapp.ui.control;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * @Author: zhousaito
 * @Date: 2019/4/18 16:29
 * @Version 1.0
 */
public class DelTextWatcher implements TextWatcher {
    private EditText mEditText;
    private View iconView;

    public DelTextWatcher(EditText editText, View iconView) {
        mEditText = editText;
        this.iconView = iconView;
        String trim = mEditText.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            iconView.setVisibility(View.INVISIBLE);
        } else {
            iconView.setVisibility(View.VISIBLE);
        }
        iconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("");
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) {
            iconView.setVisibility(View.INVISIBLE);
        } else {
            iconView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
