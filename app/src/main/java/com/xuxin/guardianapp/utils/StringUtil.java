package com.xuxin.guardianapp.utils;

import android.text.TextUtils;

public class StringUtil {
    public static String stringNotNull(String value) {
        String temp = "";
        if (!TextUtils.isEmpty(value)) {
            temp = value;
        }
        return temp;
    }
}
