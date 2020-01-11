package com.sdxxtop.guardianapp.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String stringNotNull(String value) {
        String temp = "";
        if (!TextUtils.isEmpty(value)) {
            temp = value;
        }
        return temp;
    }

    private static InputFilter filter_speChat = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
            String speChat = "[`~!@#_$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）— +|{}【】‘；：”“’。，、？]";
            Pattern pattern = Pattern.compile(speChat);
            Matcher matcher = pattern.matcher(charSequence.toString());
            if (matcher.find()) return "";
            else return null;
        }
    };
    private static InputFilter filter_speChat_noletter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
            String speChat = "[`~!@#_$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）— +|{}【】‘；：”“’。，、？qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM]";
            Pattern pattern = Pattern.compile(speChat);
            Matcher matcher = pattern.matcher(charSequence.toString());
            if (matcher.find()) return "";
            else return null;
        }
    };

    /**
     * 禁止EditText特殊字符过滤
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpaChat(EditText editText, int mMaxLength, boolean isNoLetter) {
        InputFilter filter_space = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" "))
                    return "";
                else
                    return null;
            }
        };

        InputFilter filter_length = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                int keep = mMaxLength - (dest.length() - (dend - dstart));
                if (keep <= 0) {
                    UIUtils.showToast("字数超出限制");
                    return "";
                } else if (keep >= end - start) {
                    return null; // keep original
                } else {
                    keep += start;
                    if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                        --keep;
                        if (keep == start) {
                            return "";
                        }
                    }
                    return source.subSequence(start, keep);
                }
            }
        };

        editText.setFilters(new InputFilter[]{filter_space, isNoLetter ? filter_speChat_noletter : filter_speChat, filter_length});
    }

    public static void setEditTextInhibitInputSpaChat(EditText editText, int mMaxLength){
        InputFilter filter_length = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                int keep = mMaxLength - (dest.length() - (dend - dstart));
                if (keep <= 0) {
                    UIUtils.showToast("字数超出限制");
                    return "";
                } else if (keep >= end - start) {
                    return null; // keep original
                } else {
                    keep += start;
                    if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                        --keep;
                        if (keep == start) {
                            return "";
                        }
                    }
                    return source.subSequence(start, keep);
                }
            }
        };

        editText.setFilters(new InputFilter[]{filter_length});
    }
}
