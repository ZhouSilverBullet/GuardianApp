package com.sdxxtop.network.helper.exception;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-10-10 11:07
 * Version: 1.0
 * Description:
 */
public class ApiException extends Exception {
    public int code;
    public String errorMsg;

    public ApiException() {
    }

    public ApiException(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }
}
