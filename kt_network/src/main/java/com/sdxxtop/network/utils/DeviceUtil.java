package com.sdxxtop.network.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.sdxxtop.network.helper.HttpConstantValue;


public class DeviceUtil {
    @SuppressLint("MissingPermission")
    public static String getDeviceNo(Context context) {
        if (context == null) {
            return "";
        }

        SpUtil.putString(HttpConstantValue.DEVICE_NAME, Build.MODEL);
        String device_no = SpUtil.getString(HttpConstantValue.DEVICE_NO);
        if (!TextUtils.isEmpty(device_no)) {
            return device_no;
        }

        try {
            //获取当前设备的IMEI
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            device_no = telephonyManager.getDeviceId();
            SpUtil.putString(HttpConstantValue.DEVICE_NO, device_no);
        } catch (Exception e) {
            device_no = "";
        }

        return device_no == null ? "" : device_no;
    }
}
