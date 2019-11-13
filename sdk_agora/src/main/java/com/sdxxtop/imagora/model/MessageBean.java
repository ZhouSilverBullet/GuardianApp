package com.sdxxtop.imagora.model;

import android.text.TextUtils;
import android.widget.TextView;

import com.sdxxtop.sdkagora.AgoraSession;

public class MessageBean {
    private String account;
    private String message;
    private int background;
    private boolean beSelf;

    public MessageBean(String account, String message, boolean beSelf) {
        this.account = getNewAccount(account);
        this.message = message;
        this.beSelf = beSelf;
    }

    /**
     * 获取新名字
     *
     * @param account
     * @return
     */
    private String getNewAccount(String account) {
        if ("12345678".equals(account)) {
            return "指挥中心";
        }
        String value = AgoraSession.getMessage().get(account);
        if (!TextUtils.isEmpty(value)) {
            return value;
        }
        return account;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isBeSelf() {
        return beSelf;
    }

    public void setBeSelf(boolean beSelf) {
        this.beSelf = beSelf;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }


}
