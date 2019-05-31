package com.sdxxtop.guardianapp.utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * @author :  lwb
 * Date: 2019/5/30
 * Desc:
 */
public class MyTextChangeListener implements TextWatcher {

    private OnTextChangedListener mListener;

    public MyTextChangeListener(OnTextChangedListener listener) {
        this.mListener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mListener != null) {
            mListener.textChange(s.toString().trim());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public interface OnTextChangedListener {
        void textChange(String str);
    }
}
