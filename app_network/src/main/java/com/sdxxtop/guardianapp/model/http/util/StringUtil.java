package com.sdxxtop.guardianapp.model.http.util;

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
